package com.lakala.module.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;










import com.lakala.base.model.Afterapprovalrecord;
import com.lakala.base.model.Approval;
import com.lakala.base.model.Return;
import com.lakala.base.model.ReturnItems;
import com.lakala.base.model.Torderitems;
import com.lakala.base.model.Torderprovider;
import com.lakala.module.order.service.AfterSalesService;
import com.lakala.module.order.service.QuhuoCommonService;
import com.lakala.util.Constants;
@Service
public class AfterSalesServiceImpl implements AfterSalesService {
	@Autowired
	private QuhuoCommonService quhuoCommonService;
	@Autowired
	private com.lakala.mapper.w.order.AfterSalesMapper afterSalesMapper_w;
	@Autowired
	private com.lakala.mapper.r.order.AfterSalesMapper afterSalesMapper_r;
	
	@Override
	public void updateHDFK(String torderproviderid){
		//更新item表，订单拒收列表确认收到退货的备注
		Map<String,Object> map = new HashMap<String,Object>();
        //退回回写订单商品状态 120:由退货导致当前订单作废
        map.put("invalidstate", Constants.TORDER_INVALIDSTATE_THZF);
        map.put("returnstate", Constants.TORDER_RETURNSTATE_THDD);
        map.put("state", Constants.TORDER_STATE_YJS);
        map.put("torderproviderid", torderproviderid);
        afterSalesMapper_w.updateReturnRemarkByTorderProviderId(map);
        quhuoCommonService.updateOrderProviderState(torderproviderid, Constants.TORDER_STATE_YJS,-1);
        List<Torderitems> itemlist = afterSalesMapper_r.selectOrderitemsByOrderProviderId(torderproviderid);
        if(itemlist != null && itemlist.size() > 0){
        	quhuoCommonService.updateOrderState(itemlist.get(0).getOrderid(), Constants.TORDER_STATE_YJS,-1);
        	for(Torderitems item : itemlist){
        		this.updateStore(item);
            }
        }
	}
	
	@Override
	public void updateJsInline(String torderproviderid, String mobile) {
		Torderprovider torderprovider = afterSalesMapper_r.selectByPrimaryKey(torderproviderid);
        Return returns = new Return();
        returns.setOrderid(torderprovider.getOrderid());
        returns.setReturntype(Integer.parseInt(Constants.RETURN_GOODS_TYPE));
        returns.setReturntypename("退货");
        returns.setProviderid(torderprovider.getProviderid());
//        returns.setReturntime(new Date());
        returns.setReturntime(new java.sql.Timestamp(new java.util.Date().getTime()));
        returns.setPostcode(torderprovider.getAddressarea());
        returns.setAmount(torderprovider.getActualamount());
        returns.setTelno(torderprovider.getCustcall());
        returns.setMobileno(torderprovider.getCustelno());
        returns.setUsername(torderprovider.getCusname());
        returns.setOrderamount(torderprovider.getOrderamount());
        returns.setFavorfee(torderprovider.getFavorrulemoney());
        returns.setHandlingcharge(torderprovider.getPayhandingcharge());
        returns.setRetrurnfrom("店主");
        returns.setDeliverytypename("");
        returns.setBeartype("用户承担");
        returns.setOrgid(torderprovider.getOrgid());
        afterSalesMapper_w.insertReturn(returns);
        List<Torderitems> torderitemsList = afterSalesMapper_r.selectOrderitemsByOrderProviderId(torderproviderid);
        //每个供应商下可能存在多个商品订单
        String orderid = torderitemsList.get(0).getOrderid();
        for (Torderitems torderitems : torderitemsList) {
            ReturnItems entity = new ReturnItems();
            entity.setReturnid(returns.getTreturnid());
            entity.setOrderid(torderitems.getOrderid());
            entity.setProviderid(torderitems.getProviderid());
            entity.setProvidername(torderitems.getProvidername());
            entity.setTorderitemid(String.valueOf(torderitems.getTorderitemsid()));
            entity.setGoodsid(torderitems.getGoodsid());
            entity.setGoodsname(torderitems.getGoodsname());
            entity.setPrice(torderitems.getGoodssaleprice());
            entity.setReturnmoneystatus(Constants.RETURN_STATUS_TO_BE_AUDITED_CODE);
            entity.setAccount(torderitems.getGoodsfinalprice());
            entity.setShouldrefund(torderitems.getGoodsfinalprice());
            entity.setNumber(Double.parseDouble(String.valueOf(torderitems.getGoodscount())));
            entity.setAddressfull(torderprovider.getAddressfull());
            entity.setReturngoodstatus(Constants.RETURN_STATUS_THE_COMPLETED_CODE);
            entity.setRefundproject("348");
            entity.setOrgid(torderitems.getOrgid());
            afterSalesMapper_w.insertReturnItems(entity);

            Afterapprovalrecord afterapprovalrecord = new Afterapprovalrecord();
            afterapprovalrecord.setTreturnid(returns.getTreturnid());
            afterapprovalrecord.setTreturnitemsid(entity.getTreturnitemsid());
            afterapprovalrecord.setApprovingusername(mobile);
//            afterapprovalrecord.setApprovingtime(new Date());
            afterapprovalrecord.setApprovingtime(new java.sql.Timestamp(new java.util.Date().getTime()));
            afterapprovalrecord.setApprovingstatus(Constants.RETURN_STATUS_TO_BE_AUDITED_CODE);
            afterapprovalrecord.setOrgid(torderitems.getOrgid());
            afterSalesMapper_w.insertAfterapprovalrecord(afterapprovalrecord);
            
         // 审批表插入供应商申请数据
            Approval approval = new Approval();
            approval.setUserId("1065");
            approval.setObjType("289");//售后退款申请审批
            approval.setDocentry(String.valueOf(entity.getTreturnitemsid()));
            approval.setOwnerRemark("售后退款申请审批！");
            approval.setStatus(163);
            approval.setUrl("return/reviewview/" + entity.getReturnid() + '/' + entity.getTreturnitemsid());
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("userid", approval.getUserId());
            params.put("objtype", approval.getObjType());
            params.put("docentry", approval.getDocentry());
            List<HashMap<String, Object>> list = afterSalesMapper_w.findApprovalDefinitions(params);
            if (list.size() == 0)
                throw new IndexOutOfBoundsException("请添加审批模板并配置申请人、审批人!");

            approval.setWtmCode(list.get(0).get("wtmcode").toString());
            approval.setApprovalUserId(list.get(0).get("approvaluserid").toString());
            approval.setSortId(list.get(0).get("sortid").toString());
            approval.setOrgid(torderitems.getOrgid());
            afterSalesMapper_w.insertApprovalSelective(approval);
            
            this.updateStore(torderitems);
            //插入退货换货单明细
           /* ReturnOrderItems returnOrderItems = new ReturnOrderItems();
            returnOrderItems.setOrderitemsid(torderitems.getTorderitemsid());
            returnOrderItems.setOrderproviderid(torderitems.getOrderproviderid());
            returnOrderItems.setOrderid(torderitems.getOrderid());
            returnOrderItems.setGoodsid(torderitems.getGoodsid());
            returnOrderItems.setGoodsname(torderitems.getGoodsname());
            returnOrderItems.setGoodsskuid(torderitems.getGoodsskuid());
            returnOrderItems.setGoodsskuo2oid(torderitems.getGoodsskuo2oid());
            returnOrderItems.setGoodsinprovidercode(torderitems.getGoodsinprovidercode());
            returnOrderItems.setProviderid(torderitems.getProviderid());
            returnOrderItems.setProvidername(torderitems.getProvidername());
            returnOrderItems.setGoodscount(torderitems.getGoodscount());
            returnOrderItems.setGoodspayoff(torderitems.getGoodspayoff());
            returnOrderItems.setGoodssaleprice(torderitems.getGoodssaleprice());
            returnOrderItems.setGoodsfinalprice(torderitems.getGoodsfinalprice());
            returnOrderItems.setChannelcode(torderitems.getChannelcode());
            returnOrderItems.setOrdertime(torderitems.getOrdertime());
            returnOrderItems.setLastmodifytime(torderitems.getLastmodifytime());
            returnOrderItems.setReturnstate(returns.getReturntype());
            returnOrderItems.setOperator(mobile);
//            returnOrderItems.setOperattime(new Date());
            returnOrderItems.setOperattime(new java.sql.Timestamp(new java.util.Date().getTime()));
            returnOrderItemsMapper_W.insertSelective(returnOrderItems);*/
        }

        //更新item表，订单拒收列表确认收到退货的备注
        Map<String,Object> map = new HashMap<String,Object>();
        //退回回写订单商品状态 120:由退货导致当前订单作废
        map.put("invalidstate", Constants.TORDER_INVALIDSTATE_THZF);
        map.put("returnstate", Constants.TORDER_RETURNSTATE_THDD);
        map.put("state", Constants.TORDER_STATE_YJS);
        map.put("torderproviderid", torderproviderid);
        afterSalesMapper_w.updateReturnRemarkByTorderProviderId(map);
        quhuoCommonService.updateOrderProviderState(torderproviderid, Constants.TORDER_STATE_YJS,-1);
        quhuoCommonService.updateOrderState(orderid, Constants.TORDER_STATE_YJS,-1);
    }
		
	public void updateStore(Torderitems item){
		Map<String,Object> skunetmap = new HashMap<String,Object>();
		if(item.getTempstoregoodsflag().equals(Constants.TEMPSTOREGOODSFLAG_ZCRD)){//暂存到店
			skunetmap.put("skuid", item.getGoodsskuid());
        	skunetmap.put("netno", item.getSiteno());
        	skunetmap.put("goodscount", item.getGoodscount());
        	afterSalesMapper_w.updateBySkuAndNetno(skunetmap);
		}
		if(item.getTempstoregoodsflag().equals(Constants.TEMPSTOREGOODSFLAG_BZC)){//不暂存
			skunetmap.put("goodsskuo2oid", item.getGoodsskuo2oid());
			skunetmap.put("goodscount", item.getGoodscount());
			afterSalesMapper_w.updateStore(skunetmap);
		}
	}
}
