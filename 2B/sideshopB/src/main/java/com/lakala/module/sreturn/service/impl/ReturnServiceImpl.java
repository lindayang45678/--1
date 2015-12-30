package com.lakala.module.sreturn.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lakala.base.model.*;
import com.lakala.exception.LakalaException;
import com.lakala.mapper.r.order.TorderMapper;
import com.lakala.module.approval.service.ApprovalService;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.sreturn.model.Constant;
import com.lakala.module.sreturn.model.ReturnInput;
import com.lakala.module.sreturn.model.ReturnMoble;
import com.lakala.module.sreturn.service.ReturnService;
import com.lakala.util.ReturnMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HOT.LIU on 2015/2/28.
 */
@Service
public class ReturnServiceImpl implements ReturnService {

    @Autowired
    private com.lakala.mapper.r.order.TorderitemsMapper torderitemsMapper_R;

    @Autowired
    private com.lakala.mapper.w.order.TorderitemsMapper torderitemsMapper_W;

    @Autowired
    private com.lakala.mapper.w.order.TorderproviderMapper torderproviderMapper_W;
    
    @Autowired
    private com.lakala.mapper.r.order.TorderMapper torderMapper_R;

    @Autowired
    private com.lakala.mapper.w.order.TorderMapper torderMapper_W;

    @Autowired
    private com.lakala.mapper.w.returnw.ReturnMapper returnMapper_W;

    @Autowired
    private com.lakala.mapper.r.returns.ReturnMapper returnMapper_R;

    @Autowired
    private com.lakala.mapper.w.returnitems.ReturnItemsMapper returnItemsMapper_W;

    @Autowired
    private com.lakala.mapper.r.returnitems.ReturnItemsMapper returnItemsMapper_R;

    @Autowired
    private com.lakala.mapper.w.afterapprovalrecord.AfterapprovalrecordMapper afterapprovalrecordMapper_W;

    @Autowired
    private com.lakala.mapper.r.skunetinfo.SkunetinfoMapper skunetinfoMapper_R;

    @Autowired
    private com.lakala.mapper.r.goods.TgoodskuinfoMapper tgoodskuinfoMapper_R;

    @Autowired
    private com.lakala.mapper.w.skunetinfo.SkunetinfoMapper skunetinfoMapper_W;

    @Autowired
    private com.lakala.mapper.w.goods.TgoodskuinfoMapper tgoodskuinfoMapper_W;

    @Autowired
    private ApprovalService approvalService;

    @Override
    public ObjectOutput list(ReturnInput returnInput) throws LakalaException {
        ObjectOutput data = new ObjectOutput();

        if (StringUtils.isEmpty(returnInput.getPsam())) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数对象为空!");
            return data;
        }

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("psam", returnInput.getPsam());
            map.put("page", (returnInput.getPage() - 1) * returnInput.getPageSize());
            map.put("rows", returnInput.getPageSize());
            map.put("orderid", returnInput.getOrderid());

            List<ReturnMoble> list = returnMapper_R.findAllByMobile(map);
            data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
            data.set_ReturnData(list);
            return data;
        } catch (Exception e) {
            throw new LakalaException(e);
        }
    }

    @Override
    public ObjectOutput approval(String treturnid, String treturnitemsid, String mobile, boolean isagree, String remarks) {
        ObjectOutput data = new ObjectOutput();

        if (StringUtils.isEmpty(treturnid) || StringUtils.isEmpty(treturnitemsid)) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数对象为空!");
            return data;
        }

        Return record = returnMapper_R.selectByPrimaryKey(Integer.parseInt(treturnid));

        ReturnItems returnItems = returnItemsMapper_R.selectByPrimaryKey(Integer.parseInt(treturnitemsid));
        Torderitems torderitems = torderitemsMapper_R.selectByPrimaryKey(returnItems.getTorderitemid());
        Map<String, Object> skunetmap = new HashMap<String, Object>();
        skunetmap.put("providerid", torderitems.getProviderid());
        skunetmap.put("skuid", torderitems.getGoodsskuid());
        skunetmap.put("netno", torderitems.getSiteno());

        Skunetinfo skunetinfo = skunetinfoMapper_R.findSkunetinfoByMap(skunetmap);
        Tgoodskuinfo tgoodskuinfo = tgoodskuinfoMapper_R.selectByPrimaryKey(torderitems.getGoodsskuid());

        //插入自己所有审批记录
        Afterapprovalrecord afterapprovalrecord = new Afterapprovalrecord();
        afterapprovalrecord.setTreturnid(Integer.parseInt(treturnid));
        afterapprovalrecord.setTreturnitemsid(Integer.parseInt(treturnitemsid));
        afterapprovalrecord.setApprovingusername(mobile);
        afterapprovalrecord.setApprovingtime(new java.sql.Timestamp(new java.util.Date().getTime()));
        afterapprovalrecord.setApprovingstatus(Constant.RETURN_STATUS_PENDING_AUDIT_CODE);
        afterapprovalrecordMapper_W.insertSelective(afterapprovalrecord);

        Torderitems updateorderitems = new Torderitems();
        Tgoodskuinfo skuinfo = new Tgoodskuinfo();

        if (isagree) {

            //更新售后申请主表收货日期
            record.setReceipttime(new java.sql.Timestamp(new java.util.Date().getTime()));

            returnMapper_W.updateByPrimaryKeySelective(record);

            //更新售后申请字表所有状态信息
            returnItems.setReturnmoneystatus(Constant.RETURN_STATUS_TO_BE_AUDITED_CODE);
            returnItems.setReturngoodstatus(Constant.RETURN_STATUS_THE_COMPLETED_CODE);
            returnItems.setRefundproject(Constant.PAYMENT_FOR_GOODS_CODE);
            returnItems.setRemark("同意");

            if (returnItems.getShouldrefund() == null) {
                BigDecimal shouldrefund = returnItems.getAccount();
                returnItems.setShouldrefund(shouldrefund.setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            returnItemsMapper_W.updateByPrimaryKeySelective(returnItems);

            //更改订单明细表中的状态为换货成功
            updateorderitems.setTorderitemsid(torderitems.getTorderitemsid());

            if (Constant.RETURN_GOODS_TYPE.equals(String.valueOf(record.getReturntype()))) {
                // 审批表插入供应商申请数据
                Approval approval = new Approval();
                approval.setUserId("1064");
                approval.setObjType(Constant.CUSTOMER_SERVICE_REFUND_APPLICATION_APPROVAL);
                approval.setDocentry(String.valueOf(returnItems.getTreturnitemsid()));
                approval.setOwnerRemark("售后退款申请审批！");
                // 审批状态：O 待审批
                approval.setStatus(Constant.WAIT);
                approval.setUrl("return/reviewview/" + record.getTreturnid() + '/' + returnItems.getTreturnitemsid());

                approvalService.insertSelective(approval);

                //更新订单明细表
                updateorderitems.setInvalidstate(Integer.parseInt(Constant.RETURN_STATUS_HAS_BEEN_RETURNED));

                //如果为取消订单不对还原库存
                if (!Constant.RETURN_STATUS_CANCEL_ORDER_CODE.equals(String.valueOf(record.getReturntype()))) {
                    if (Constant.ORDER_GOODS_GIFT_STATE_GOODS.equals(String.valueOf(torderitems.getGiftstate()))) {//如果为商品
                        if (Constant.RETURN_STATUS_TEMP_STORE_GOODS_FLAG_CODE.equals(torderitems.getTempstoregoodsflag())) {
                            skunetinfo.setStock(skunetinfo.getStock() + returnItems.getNumber().intValue());

                            if (skunetinfo.getSoldstock() >= returnItems.getNumber().intValue()) {
                                skunetinfo.setSoldstock(skunetinfo.getSoldstock() - returnItems.getNumber().intValue());
                            }
                            skunetinfoMapper_W.updateByPrimaryKeySelective(skunetinfo);
                        } else {
                            skuinfo.setTgoodskuinfoid(torderitems.getGoodsskuid());
                            skuinfo.setSkustock(tgoodskuinfo.getSkustock().add(new BigDecimal(returnItems.getNumber())));
                            tgoodskuinfoMapper_W.updateByPrimaryKeySelective(skuinfo);
                        }
                    } else if (Constant.ORDER_GOODS_GIFT_STATE_GIFT.equals(String.valueOf(torderitems.getGiftstate()))) {//如果为赠品
                        skuinfo.setTgoodskuinfoid(torderitems.getGoodsskuid());
                        skuinfo.setSkustock(tgoodskuinfo.getSkustock().add(new BigDecimal(returnItems.getNumber())));
                        tgoodskuinfoMapper_W.updateByPrimaryKeySelective(skuinfo);
                    }
                }

            } else if (Constant.EXCHANG_GOODS_TYPE.equals(String.valueOf(record.getReturntype()))) {
                updateorderitems.setInvalidstate(Integer.parseInt(Constant.RETURN_STATUS_HAVE_REPLACEMENT));
                updateorderitems.setState(Integer.parseInt(Constant.ORDER_STATUS_NON_DELIVERY));
            }
            torderitemsMapper_W.updateByPrimaryKeySelective(updateorderitems);
        } else {

            //更新售后申请字表所有状态信息
            if (Constant.RETURN_STATUS_THE_COMPLETED_CODE.equals(returnItems.getReturngoodstatus())) {
                if (Constant.RETURN_STATUS_TO_BE_AUDITED_CODE.equals(returnItems.getReturnmoneystatus())) {
                    returnItems.setReturnmoneystatus(Constant.RETURN_STATUS_REFUND_EXAMINATION_NOT_THROUGH_CODE);
                    returnItems.setRefundproject(Constant.PAYMENT_FOR_GOODS_CODE);
                    if (returnItems.getShouldrefund() == null) {
                        BigDecimal shouldrefund = returnItems.getAccount();
                        returnItems.setShouldrefund(shouldrefund.setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                }
            } else {
                returnItems.setReturngoodstatus(Constant.RETURN_STATUS_EXAMINATION_NOT_THROUGH_CODE);
            }

            returnItems.setRemark(remarks);

            returnItemsMapper_W.updateByPrimaryKeySelective(returnItems);

            Torderitems updatetorderitems = new Torderitems();
            updatetorderitems.setTorderitemsid(torderitems.getTorderitemsid());
            //还原子订单退货换货状态
            updatetorderitems.setReturnstate(Integer.parseInt(Constant.ORDER_RETURN_STATUS));
            updatetorderitems.setInvalidstate(Integer.parseInt(Constant.ORDER_INVALID_STATE));
            //为取消订单
            if (Constant.RETURN_STATUS_CANCEL_ORDER_CODE.equals(returnItems.getReturngoodstatus())) {
                //还原取消订单到待发货
                Torder updatetorder = new Torder();
                Torderprovider updatetorderprovider = new Torderprovider();
                updatetorder.setTorderid(torderitems.getOrderid());
                updatetorder.setCancelstate(Integer.parseInt(Constant.ORDER_CANCEL_STATE));
                updatetorderprovider.setTorderproviderid(torderitems.getOrderproviderid());
                updatetorderprovider.setCancelstate(Integer.parseInt(Constant.ORDER_CANCEL_STATE));
                updatetorderitems.setCancelstate(Integer.parseInt(Constant.ORDER_CANCEL_STATE));
                torderMapper_W.updateTorder(updatetorder);
                torderproviderMapper_W.updateTorderProvider(updatetorderprovider);
                torderitemsMapper_W.updateByPrimaryKeySelective(updatetorderitems);
            }
            //不管是什么操作，只要是审核不通过，都需要对商品或赠品进行库存和销售量的数据进行还原
            if (Constant.PAYMENT_FOR_GOODS_CODE.equals(returnItems.getRefundproject())) {
                if (Constant.ORDER_GOODS_GIFT_STATE_GOODS.equals(String.valueOf(torderitems.getGiftstate()))) {//如果为商品
                    //如果为暂存商品
                    if (Constant.RETURN_STATUS_TEMP_STORE_GOODS_FLAG_CODE.equals(torderitems.getTempstoregoodsflag())) {
                        if (skunetinfo.getStock() >= returnItems.getNumber().intValue()) {
                            skunetinfo.setStock(skunetinfo.getStock() - returnItems.getNumber().intValue());
                        }

                        skunetinfo.setSoldstock(skunetinfo.getSoldstock() + returnItems.getNumber().intValue());
                        skunetinfoMapper_W.updateByPrimaryKeySelective(skunetinfo);
                    } else {
                        skuinfo.setTgoodskuinfoid(torderitems.getGoodsskuid());
                        if (tgoodskuinfo.getSkustock().compareTo(new BigDecimal(returnItems.getNumber())) > -1) {
                            skuinfo.setSkustock(tgoodskuinfo.getSkustock().subtract(new BigDecimal(returnItems.getNumber())));
                        }
                        tgoodskuinfoMapper_W.updateByPrimaryKeySelective(skuinfo);
                    }
                } else if (Constant.ORDER_GOODS_GIFT_STATE_GIFT.equals(String.valueOf(torderitems.getGiftstate()))) {//如果为赠品
                    skuinfo.setTgoodskuinfoid(torderitems.getGoodsskuid());
                    skuinfo.setSkustock(tgoodskuinfo.getSkustock().subtract(new BigDecimal(returnItems.getNumber())));
                    tgoodskuinfoMapper_W.updateByPrimaryKeySelective(skuinfo);
                }
                //还原订单状态
                torderitemsMapper_W.updateByPrimaryKeySelective(updatetorderitems);
            }
        }

        data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
        data.set_ReturnMsg("操作成功！");
        return data;
    }
    
    
    
    /**
     * 生成退款的torderitemslist并生成returnItemList进行退款,并根据实际情况对运费的退款进行分析处理
     * @param torderprovider
     * @param nowcancelitemids
     * @param isalldo
     * @param returns
     * @param user
     * @return
     * @author: yhg 
     * @throws LakalaException 
     * @time: 2015年6月3日 上午12:37:33
     * @todo: TODO
     */
    public void updateReturnListByOrderItemListWhenTk(Torderprovider torderprovider,List<Torderitems> torderitemsList,boolean isalldo,Return returns) throws LakalaException{

    	    if(torderitemsList != null){
            	if(!isalldo){
            		String nowcancelitemids = "";
        	    	for (Torderitems oi : torderitemsList) {
        	    		nowcancelitemids = nowcancelitemids + "," + oi.getTorderitemsid();
    				}
        	    	
                	Map map = new HashMap();
                	map.put("nowcancelitemids", nowcancelitemids.substring(1));
                	map.put("tallorderid", torderitemsList.get(0).getTallorderid());
                	//虽然不是整单取消，但也是取消该笔订单即取消了整个tallorderid订单，所以运费该算在其中某一个torderitems商品子订单上，这里默认取第一个
                	if(0 == torderitemsMapper_R.selectIsLastOrderitems(map)){
                		for (int i = 0; i < torderitemsList.size(); i++) {
							if(i==0){
								updateReturnDataByOrderItemWhenTk(returns,torderitemsList.get(i),3);
							}else{
								updateReturnDataByOrderItemWhenTk(returns,torderitemsList.get(i),2);
							}
						}
                	}else{
                		//除取消的该订单还存在其他未取消的商品订单，或者该整单下有商品子订单是已发货的
                        for (Torderitems torderitems : torderitemsList) {
                        	//售后退款信息
                        	updateReturnDataByOrderItemWhenTk(returns,torderitems,2);
                        }
                	}
                	
                }else{
                	//整单取消
                    for (Torderitems torderitems : torderitemsList) {
                    	//售后退款信息
                    	updateReturnDataByOrderItemWhenTk(returns,torderitems,1);
                    }
                }
            }
    }
    
    /**
     * 本方法用于根据商品订单更新售后退款信息(也适用于批发进货、暂存入仓的售后信息)-见如下描述中退款规则
     * @param returns 售后主表实体
     * @param torderitems 商品订单实体
     * @param user 用户实体
     *   未发货订单取消退款规则
     *     1、整单取消:退款金额=售价*数量-优惠券分摊的金额+整单运费;  
     *     2、部分取消:a)若除需取消订单外，存在未取消或已发货订单，则运费不返还。退款金额=售价*数量-优惠券分摊的金额
     *             b)若除需取消订单外,其他订单都已取消，切都是未发货的订单。则返还整单运费。   退款金额=售价*数量-优惠券分摊的金额+整单运费
     *   已发货订单取消退货规则 
     *     1、整单取消:退款金额=售价*数量-优惠券分摊的金额
     *     2、部分取消:退款金额=售价*数量-优惠券分摊的金额         
     *       
     * @param logiopttype = 1：标识整单减运费,并根据每个商品订单分摊金额做运费加减;  
     *        logiopttype = 2：运费不参与退款运算,适用于整单中存在已参与过发货的订单或还存在未取消的订单;   
     *        logiopttype = 3：整单运费分摊在某个商品订单上，适用于整单未发货的订单非整单取消,且是最后一个取消的订单，运费需全部返还;
     * @return
     */
    public void updateReturnDataByOrderItemWhenTk(Return returns,Torderitems torderitems,int logiopttype)  throws LakalaException {
    	
    	    if(null!=torderitems&&com.lakala.base.model.OrderConstant.TORDER_ISPAY_YZF==torderitems.getIspay()){
    	    	//售后退款信息
    	    	ReturnItems entity = new ReturnItems();
    	        entity.setReturnid(returns.getTreturnid());
    	        entity.setOrderid(torderitems.getOrderid());
    	        entity.setProviderid(torderitems.getProviderid());
    	        entity.setProvidername(torderitems.getProvidername());
    	        entity.setTorderitemid(String.valueOf(torderitems.getTorderitemsid()));
    	        entity.setGoodsid(torderitems.getGoodsid());
    	        entity.setGoodsname(torderitems.getGoodsname());
    	        entity.setPrice(torderitems.getGoodssaleprice());
    	        entity.setReturnmoneystatus(Constant.RETURN_STATUS_TO_BE_AUDITED_CODE);
    	        
    	        //实际应退金额
    	        BigDecimal needreturnAmount = new BigDecimal(0);
    	        BigDecimal goodsfavorulemoney_temp = (torderitems.getGoodsfavorulemoney()==null?new BigDecimal(0):torderitems.getGoodsfavorulemoney());
    	        BigDecimal logisticsfee_temp = (torderitems.getLogisticsfee()==null?new BigDecimal(0):torderitems.getLogisticsfee());
    	        
    	      //获取实际应退金额
    	        if(1 == logiopttype){
    	         	needreturnAmount = (torderitems.getGoodssaleprice().multiply(new BigDecimal(torderitems.getGoodscount()))).subtract(goodsfavorulemoney_temp).add(logisticsfee_temp);
    	         }else if(2 == logiopttype){
    	         	needreturnAmount = (torderitems.getGoodssaleprice().multiply(new BigDecimal(torderitems.getGoodscount()))).subtract(goodsfavorulemoney_temp);
    	         }else if(3 == logiopttype){
    	         	 //查整体运费
    	    		 String alllogisfeestr = "0";
    	   			 alllogisfeestr = torderMapper_R.selectAllOrderLogisfeeById(torderitems.getTallorderid());
    	   			 if(StringUtils.isEmpty(alllogisfeestr)){
    	   				alllogisfeestr = "0";
    	   			 }
    	   			 BigDecimal alllogisfee = new BigDecimal(alllogisfeestr);
    	    		 needreturnAmount = (torderitems.getGoodssaleprice().multiply(new BigDecimal(torderitems.getGoodscount()))).subtract(goodsfavorulemoney_temp).add(alllogisfee);
    	    	}
    	        
    	        entity.setAccount(needreturnAmount);
    	        entity.setShouldrefund(needreturnAmount);
    	        entity.setRefundproject(Constant.PAYMENT_FOR_GOODS_CODE);
    	        entity.setNumber(Double.parseDouble("0"));//待发货退款,数量不退
    	        entity.setReturngoodstatus(Constant.RETURN_STATUS_CANCEL_ORDER_CODE);
    	        entity.setAddressfull(torderitems.getAddressfull());
    	        entity.setRemark("取消订单");
    	        entity.setOrgid(torderitems.getOrgid());
    	        returnItemsMapper_W.insertSelective(entity);
    	        
    	        // 审批表插入审批数据
    	        Approval approval = new Approval();
    	        approval.setUserId(com.lakala.util.Constants.RETRURN_APPROVAL_2BUSERID);
    	        approval.setObjType(Constant.CUSTOMER_SERVICE_REFUND_APPLICATION_APPROVAL);
    	        approval.setDocentry(String.valueOf(entity.getTreturnitemsid()));
    	        approval.setOwnerRemark("订单取消退款申请审核！");
    	        // 审批状态：O 待审批
    	        approval.setStatus(Constant.WAIT);
    	        approval.setUrl("return/reviewview/" + returns.getTreturnid() + '/' + entity.getTreturnitemsid());
    	        approval.setOrgid(torderitems.getOrgid());
    	        this.approvalService.insertSelective(approval);
    	    }
    }
    
    /**
     * 根据订单生成售后退款信息
     * @param torderprovider
     * @param torderitemsList
     * @param isalldo 是否整单操作
     * @throws Exception
     * @author: yhg 
     * @time: 2015年4月25日 下午2:37:07
     * @todo: TODO
     */
    public void updateReturnTKDataByOrder(Torderprovider torderprovider,List<Torderitems> torderitemsList,boolean isalldo) throws LakalaException{
    	
    		if(torderprovider!=null&&com.lakala.base.model.OrderConstant.TORDER_ISPAY_YZF==torderprovider.getIspay()){
            	Return returns = new Return();
                returns.setOrderid(torderprovider.getOrderid());
                returns.setReturntype(Integer.parseInt(Constant.RETURN_CANCEL_ORDER));
                returns.setReturntypename("取消订单");
                returns.setProviderid(torderprovider.getProviderid());
//                returns.setReturntime(new Date());
                returns.setReturntime(new java.sql.Timestamp(new java.util.Date().getTime()));
                returns.setPostcode(torderprovider.getAddressarea());
                returns.setAmount(torderprovider.getActualamount());
                returns.setTelno(torderprovider.getCustcall());
                returns.setMobileno(torderprovider.getCustelno());
                returns.setUsername(torderprovider.getCusname());
                returns.setOrderamount(torderprovider.getOrderamount());
                returns.setFavorfee(torderprovider.getFavorrulemoney());
                returns.setHandlingcharge(torderprovider.getPayhandingcharge());
                returns.setRetrurnfrom(Constant.RETRURN_FROM_APP2B);
                returns.setRemark("取消订单");
                returns.setOrgid(torderprovider.getOrgid());
                returnMapper_W.insertSelective(returns);
                //每个供应商下可能存在多个商品订单
                updateReturnListByOrderItemListWhenTk(torderprovider,torderitemsList,isalldo,returns);
            }
    	
    }


  
}
