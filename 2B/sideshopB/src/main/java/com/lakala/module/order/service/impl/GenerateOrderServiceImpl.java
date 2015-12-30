package com.lakala.module.order.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import w.com.lakala.order.model.CouList;
import w.com.lakala.order.model.GoodsW;
import w.com.lakala.order.model.Goodsaddorder;
import w.com.lakala.order.model.OrderInfoW;
import w.com.lakala.order.model.OrderReturnaddorder;
import w.com.lakala.order.model.RetStatusaddorder;

import com.alibaba.fastjson.JSON;
import com.lakala.base.model.SDBMediaStatistics;
import com.lakala.base.model.TfavorableruleCouponBatch;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.base.model.Tshopkeeperaddr;
import com.lakala.exception.LakalaException;
import com.lakala.model.BillInfo;
import com.lakala.model.ReturnBillInfo;
import com.lakala.module.comm.Constant;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.vo.TgoodsPublishKdbSkuO2o;
import com.lakala.module.mongo.service.SideShopBMongoService;
import com.lakala.module.order.model.AddOrderInput;
import com.lakala.module.order.model.CartStoreR;
import com.lakala.module.order.model.CheckCoupGoodsReturn;
import com.lakala.module.order.model.CheckCoupProReturn;
import com.lakala.module.order.model.CheckCouponInfo;
import com.lakala.module.order.model.CheckCouponReturn;
import com.lakala.module.order.model.Coupons;
import com.lakala.module.order.model.GoodsR;
import com.lakala.module.order.model.RetStatusR;
import com.lakala.module.order.model.ShopCommAddrInput;
import com.lakala.module.order.model.StoreReturnR;
import com.lakala.module.order.model.TCoupon;
import com.lakala.module.order.service.GenerateOrderService;
import com.lakala.module.user.service.UserService;
import com.lakala.module.user.vo.UserInfoOutput;
import com.lakala.module.user.vo.UserLoginInput;
import com.lakala.module.wholesale.model.ProductDetailForProductList;
import com.lakala.module.wholesale.model.SkuDetails;
import com.lakala.promotion.vo.CouponBean;
import com.lakala.promotion.vo.GoodsVO;
import com.lakala.promotion.vo.PlaceOrderVO;
import com.lakala.util.Base64;
import com.lakala.util.Constants;
import com.lakala.util.ConstantsUtil;
import com.lakala.util.DES;
import com.lakala.util.DateUtil;
import com.lakala.util.MD5Util;
import com.lakala.util.ReturnMsg;
import com.lakala.util.SecurityFactory;
import com.lakala.util.http.HttpSend;
/**
 * 物流服务接口的实现
 * @author ls
 */
@Service
public class GenerateOrderServiceImpl implements GenerateOrderService {
	Logger logger = Logger.getLogger(GenerateOrderServiceImpl.class);
	
	@Autowired
	private com.lakala.mapper.r.sdbmediastatistics.SdbMediaStatisticsMapper sdbMediaStatisticsMapperR;
	@Autowired
	private com.lakala.mapper.w.sdbmediastatistics.SdbMediaStatisticsMapper sdbMediaStatisticsMapper_w;
	
	@Autowired
	private com.lakala.mapper.r.coupon.TCouponMapper tCouponMapperR;
	
	
	@Autowired
	private com.lakala.mapper.r.goodspublishkdbskuo2o.TgoodsPublishKdbSkuO2oMapper tgoodsPublishKdbSkuO2oMapperR;
	
	@Autowired
	private com.lakala.promotion.remote.OrderRemote OrderRemote;
	
	@Autowired
	private com.lakala.mapper.r.coupon.TfavorableruleCouponBatchMapper tfavorableruleCouponBatchMapperR;
	
	@Autowired
	private com.lakala.mapper.r.goods.TgoodskuinfoMapper tgoodskuinfoMapperR;
	
	
	@Autowired
	private com.lakala.service.PayLakalaService payLakalaServiceIntranet;
	
	@Autowired
	private com.lakala.service.PayLakalaService payLakalaServiceExtranet;
	
	@Autowired
	private w.com.lakala.order.service.OrderDubboService orderDubboServiceImplW;
	
	@Autowired
	private com.lakala.mapper.w.order.TorderMapper torderMapperW;
	@Autowired
	private UserService userService;
	@Autowired
	private ConstantsUtil constantsUtil;
	
	@Autowired
    private SideShopBMongoService sideShopBMongoService;
	@Autowired
	private com.lakala.mapper.w.pay.PayRequestInfoMapper payRequestInfoMapper_w;
	
	@Override
	public ObjectOutput<CheckCouponReturn> checkcoupon(CheckCouponInfo cou) {
		ObjectOutput<CheckCouponReturn> data = new ObjectOutput<CheckCouponReturn>();

		logger.info("checkcoupon start==================== >> " + new Date());
		//9000--psam号或手机号不能为空!
		//9001--某个优惠券不可用
		String borc = cou.getWholesale();
		String retcode = "";
		String retmeg = "";
		String remark = "";
		String bdflag = "0";//优惠券绑定标志
		String syflag = "0";//优惠券使用标志
		String result = "";
		String tel = "";
		PlaceOrderVO pvo = new PlaceOrderVO();
		List<Coupons> errorlist = new ArrayList<Coupons>();
		
		logger.info("checkcoupon tel==================== >> " + tel);
		logger.info("checkcoupon 2c or 2b ================== >> " + borc);
		logger.info("checkcoupon --传来的 -- psam ======================= >> " + cou.getPasmid());
		SDBMediaStatistics sdb = null;
		try{
			 sdb = this.getSdbMediaStatisticsByPsam(cou.getPasmid());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if("2B".equals(borc) || "2b".equals(borc)){
			tel = sdb.getMobile();
		}else{
			tel = cou.getCustelno();
		}
		
		if(cou.getCouponlist() != null && cou.getCouponlist().size() > 0){
			
			List<Map> couponlist = cou.getCouponlist();
			Map<String, String> map = null;
			TCoupon tc = null;
			CouponBean cb = null;
			List<CouponBean> cblist = new ArrayList<CouponBean>();
			if(couponlist != null){
				for(Map cs : couponlist){
					
					tc = new TCoupon();
					map = new HashMap<String, String>();
					map.put("coupon", String.valueOf(cs.get("couponCode")));
					map.put("num", String.valueOf(cs.get("count")));
					map.put("tel", tel);
					map.put("psam", cou.getPasmid());
					
					if("2B".equalsIgnoreCase(borc)){
						tc = this.tCouponMapperR.selectUseCouponByMapWithPsam(map);
						
					}else{
						tc = this.tCouponMapperR.selectUseCouponByMap(map);
						
					}
					
					 if(tc ==  null){
						 Coupons cs1 = new Coupons();
						 cs1.setCouponCode(String.valueOf(cs.get("couponCode")));
							cs1.setRemark("此优惠券不存在！");
							cs1.setIsValidate(0);
							errorlist.add(cs1);
							bdflag = "1";
							syflag = "5";
							break;
					 }
					 logger.info("checkcoupon --tcoupon -- psam ======================= >> " + tc.getDeviceno());
					 if("2B".equalsIgnoreCase(borc)){
						 if(tel.equalsIgnoreCase(tc.getTel()) && null != tc.getDeviceno()){
							
						 }else{
							 Coupons cs1 = new Coupons();
							 cs1.setCouponCode(String.valueOf(cs.get("couponCode")));
							 cs1.setRemark("您的手机号未绑定该优惠券，不能使用！");
								cs1.setIsValidate(0);
								errorlist.add(cs1);
								bdflag = "1";
								syflag = "4";
								break;
						 }
					 }else{
						 if( tel.equalsIgnoreCase(tc.getTel()) && null == tc.getDeviceno()){
							 
						 }else{
							 Coupons cs1 = new Coupons();
							 cs1.setCouponCode(String.valueOf(cs.get("couponCode")));
							 cs1.setRemark("您的手机号未绑定该优惠券，不能使用！");
								cs1.setIsValidate(0);
								errorlist.add(cs1);
								bdflag = "1";
								syflag = "4";
								break;
						 }
						 
					 }
					 
					  cb = new CouponBean();
					  cb.setCouponCode(String.valueOf(cs.get("couponCode")));
					  cb.setCount(Integer.parseInt(String.valueOf(cs.get("count"))));
					  cblist.add(cb);
				}
				if(!bdflag.equals("1")){
					 pvo.couponList = cblist;
				}
				
			}
		}

		//pvo.code = cou.getCoupon();
		pvo.pasmId = cou.getPasmid();
		pvo.payChannel = cou.getPaychannel();
		pvo.mobile = tel;
		List<Map> rList = cou.getItems();

		if (rList == null || rList.size() == 0) {
			CheckCouponReturn ct = new CheckCouponReturn();
			ct.setRetcode("0000");
			ct.setRetmeg("");
			ct.setFlag("0");
			CheckCoupProReturn court = new CheckCoupProReturn();
			court.setCoupon("");
			court.setCouponvalue("0.00");
			court.setLogisfee("0.00");
			court.setPromfee("0.00");
			court.setGoodstotal("0.00");// 纯商品价格
			court.setTotal("0.00");// 不包含运费的总金额
			court.setActualtotal(String.valueOf("0.00"));// 最终刷卡金额
			court.setCouponuse("2");
			List<CheckCoupGoodsReturn> items = new ArrayList<CheckCoupGoodsReturn>();
			ct.setTcouponlist(new ArrayList<TCoupon>());
			ct.setCoupondata(court);
			ct.setCouponlist(new ArrayList<Coupons>());
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnMsg("验证通过");
			data.set_ReturnData(ct);
			return data;
		} else {
			for (Map ca : rList) {
				BigDecimal price = new BigDecimal(-1);
				TgoodsPublishKdbSkuO2o o2oInfo;
				// 查价格
				try {
					
					logger.info("pvo.pasmId ============== >> " +pvo.pasmId);
					logger.info("skuo2oid ============== >> " +ca.get("skuo2oid"));
					logger.info("skuid ============== >> " +ca.get("skuid"));
					logger.info("goodscount ============== >> " +ca.get("goodscount"));
					logger.info("sdb.getTermFbtype() ============== >> " +sdb.getTermFbtype());
					logger.info("sdb.getCityAreaNo() ============== >> " + sdb.getCityAreaNo());
					
					
					com.lakala.base.model.Tgoodskuinfo skuinfo = this.tgoodskuinfoMapperR.selectByPrimaryKey(Integer.parseInt(String.valueOf(ca.get("skuid"))));
					
					ObjectOutput info = null;
					try{
						
						info = this.sideShopBMongoService.detail_mongo(pvo.pasmId, String.valueOf(ca.get("channel")), String.valueOf(skuinfo.getTgoodinfoid()),"all",1);
						logger.info("info._ReturnCode ======== " + info._ReturnCode);
						logger.info("info._ReturnMsg ========= " + info._ReturnMsg);
						
						if(info._ReturnCode.equals("000000")){
							ProductDetailForProductList goods = (ProductDetailForProductList) info.get_ReturnData();
							SkuDetails[] goodsSkuO2OList = goods.getGoodsSkuO2OList();
							for(SkuDetails sku : goodsSkuO2OList){
								if(sku.getTGoodSkuInfoId() == Integer.parseInt(String.valueOf(ca.get("skuid")))){
									if(70 == sku.getType()){
										price = sku.getPromotionPrice(); 
									}else{
										price = new BigDecimal(sku.getSalePrice());
									}
									
								}
									
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
						price = new BigDecimal(-1);
					}
					
					
					logger.info("goodsname ============== >> " +ca.get("goodsname"));
					logger.info("price ============== >> " +price);
					logger.info("channelId ============== >> " +ca.get("channel"));
					
					GoodsVO  gvo  = new  GoodsVO();
					gvo.id = Long.parseLong(String.valueOf(ca.get("skuid")));
					gvo.name = String.valueOf(ca.get("goodsname"));
					gvo.price = price;
					gvo.num = Integer.parseInt(String.valueOf(ca.get("goodscount")));
					gvo.reducePrice = new BigDecimal("0.00");
					gvo.channelId = String.valueOf(ca.get("channel"));
					gvo.skuo2oId = String.valueOf(ca.get("skuo2oid"));
					gvo.pasmId = cou.getPasmid();
					gvo.deliveryService = 2;
					gvo.property = 247;
					pvo.addGoods(gvo);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 调用促销模块
			if(pvo.goodsList!= null && pvo.goodsList.size() > 0){
				logger.info("OrderRemote.executeByChannel2 start==================== >> " + new Date());
				pvo = (PlaceOrderVO) OrderRemote.executeByChannel2("appb", pvo);
				logger.info("OrderRemote.executeByChannel2 end==================== >> " + new Date());
			}
			
			List<TCoupon> tclist = new ArrayList<TCoupon>();
			if("2B".equals(borc) || "2b".equals(borc)){
				tclist = this.getTcouponListByPsam(sdb.getMobile());
			}
			
			
			List<TCoupon> newtclist = new ArrayList<TCoupon>();
			List<TCoupon> newtclist2 = new ArrayList<TCoupon>();
			List<Long> pcidlist = pvo.batchidList;
			logger.info("tclist =========================== >> " + tclist);
			logger.info("pcidlist ========================= >> " + pcidlist);
			if(tclist != null && tclist.size() > 0 && pcidlist != null && pcidlist.size() > 0){
				
				for(TCoupon co : tclist){
					for(Long pcid : pcidlist){
						logger.info("batchid ====================== >> " + pcid);
						if(co.getBatchtickeid().equals(pcid)){
							newtclist.add(co);
						}
					}
				}
				if(newtclist != null && newtclist.size() > 0){
					List<TfavorableruleCouponBatch> balist = this.tfavorableruleCouponBatchMapperR.selectBybatchidList(pcidlist);
					if(balist != null &&  balist.size() > 0){
						for(TCoupon co : newtclist){
							for(TfavorableruleCouponBatch pcid : balist){
								System.out.println("co.getBatchtickeid() ========= " + co.getBatchtickeid() +" ; pcid.getBatchid()  === " + pcid.getBatchid());
								if(co.getBatchtickeid().equals(Long.parseLong(String.valueOf(pcid.getBatchid()))) || co.getBatchtickeid() == Long.parseLong(String.valueOf(pcid.getBatchid()))){
									co.setOrdermount(pcid.getOrderamount().setScale(2, BigDecimal.ROUND_HALF_UP));
									newtclist2.add(co);
								}
							}
						}
					}
				}
				
			}
			
			int i = 0;
			CheckCouponReturn ct = new CheckCouponReturn();
			CheckCoupProReturn court = new CheckCoupProReturn();
			if(bdflag.equals("1")){
				//优惠券与手机号不绑定
				ct.setRetcode("9000");
				ct.setRetmeg("优惠券无效");
			}else{
				if(pvo.useCoupon != null && pvo.useCoupon.equals("0")){
					ct.setRetcode("0000");
					ct.setRetmeg("促销验证成功");
				}else{
					ct.setRetcode("9000");
					ct.setRetmeg("优惠券无效");
				}
			}
			
			//0-满足，。1-不满足
			ct.setDirectDropFlag(pvo.directDropFlag);
			ct.setDirectdropmap( pvo.goodsDDmap);
			logger.info("directDropFlag =========================== >> " + pvo.directDropFlag);
			logger.info("gDDPPmap.size()=>" + pvo.goodsDDmap.size());
			for (Long l : pvo.goodsDDmap.keySet()) {
				logger.info("gDDPPmap=>" + l + "\t" + pvo.goodsDDmap.get(l));
			}
			if(1 == pvo.directDropFlag){
				ct.setRetcode("9000");
				ct.setRetmeg("商品不满足限购规则！");
			}
			
			
			court.setCoupon(pvo.code);
			court.setCouponvalue(String.valueOf(pvo.couponPrice.setScale(2, BigDecimal.ROUND_HALF_UP)));
			court.setLogisfee(String.valueOf(pvo.freight.setScale(2, BigDecimal.ROUND_HALF_UP)));
			
			BigDecimal allfav = pvo.favAmount.add(pvo.couponPrice);
			
			if(allfav.compareTo(pvo.goodsTotal.add(pvo.freight)) >0){
				allfav = pvo.goodsTotal.add(pvo.freight);
			}
			
			if(allfav.compareTo(new BigDecimal(0)) < 0){
				allfav = new BigDecimal(0);
			}
			court.setPromfee(String.valueOf(allfav.setScale(2,   BigDecimal.ROUND_HALF_UP)));
			court.setGoodstotal(String.valueOf(pvo.goodsTotal.setScale(2,   BigDecimal.ROUND_HALF_UP)));// 纯商品价格
			court.setTotal(String.valueOf(pvo.totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP)));//最终刷卡金额
			court.setActualtotal(String.valueOf(pvo.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP)));// 最终刷卡金额
			if(syflag.equals("4")){
				court.setCouponuse("4");
			}else if(syflag.equals("5")){
				court.setCouponuse("5");
			}else{
				court.setCouponuse(pvo.useCoupon);
			}
			logger.info("pvo.useCoupon ================= >> " + pvo.useCoupon);
			List<CheckCoupGoodsReturn> items = new ArrayList<CheckCoupGoodsReturn>();
			CheckCoupGoodsReturn goodsw;
			
			Map<Long,BigDecimal> gDDPPmap = pvo.gDDPPmap;
			
			for (Long l : pvo.gDDPPmap.keySet()) {
				logger.info("gDDPPmap=>" + l + "\t" + pvo.gDDPPmap.get(l));
			}
			
			Map pricemap = new HashMap();
			
			for (GoodsVO gsvo : pvo.goodsList) {
				goodsw = new CheckCoupGoodsReturn();
				goodsw.setSkuid(String.valueOf(gsvo.id));
				goodsw.setSkuo2oid(gsvo.skuo2oId);
				goodsw.setGoodsname(gsvo.name);
				goodsw.setGoodscount(String.valueOf(gsvo.num));
				goodsw.setGoodssaleprice(String.valueOf(gsvo.price));
				goodsw.setGoodsactualprice(String.valueOf(gsvo.actualPrice));
				goodsw.setGoodtotalprice(String.valueOf(gsvo.totalPrice));
				goodsw.setChannel(gsvo.channelId);
				if (gsvo.flag == false) {
					goodsw.setIsgift("0");
				} else {
					goodsw.setIsgift("1");
				}
				if(gDDPPmap != null && !gDDPPmap.isEmpty()){
					if(gDDPPmap.get(String.valueOf(gsvo.id)) != null){
						BigDecimal p1 = new BigDecimal(String.valueOf(gsvo.price));
						if(gDDPPmap.get(String.valueOf(gsvo.id)).compareTo(new BigDecimal("0.00")) > 0){
							if(p1.compareTo(gDDPPmap.get(String.valueOf(gsvo.id))) == 0){
								
							}else{
								pricemap.put(String.valueOf(gsvo.id),String.valueOf(gsvo.price));
							}
						}
						
					}
					
				}
				goodsw.setParentid(String.valueOf(gsvo.parentId));
				items.add(goodsw);
			}
			court.setItems(items);
			if(pricemap != null && !pricemap.isEmpty()){
				ct.setRetcode("9000");
				ct.setRetmeg("价格不符，请重新进入订单确认页购买！");
				ct.setPriceflag(1);
				ct.setPricemap(pricemap);
			}
			
			if(pvo.couponList != null && pvo.couponList.size() > 0){
				List<Coupons> clist = new ArrayList<Coupons>();
				Coupons cps = null;
				for(CouponBean cp : pvo.couponList){
					cps = new Coupons();
					cps.setBatchCode(cp.getBatchCode());
					cps.setCount(String.valueOf(cp.getCount()));
					cps.setCouponCode(cp.getCouponCode());
					cps.setCouponPrice(cp.getCouponPrice());
					cps.setIsValidate(cp.getIsValidate());
					cps.setRemark(cp.getRemark());
					cps.setTtlCouponPrice(cp.getTtlCouponPrice());
					clist.add(cps);
				}
				ct.setCouponlist(clist);
			}else{
				ct.setCouponlist(errorlist);
			}
			ct.setCoupondata(court);
			ct.setTcouponlist(newtclist2);
			ct.setWholesale(cou.getWholesale());
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnMsg("验证通过");
			data.set_ReturnData(ct);
			logger.info("checkcoupon end==================== >> " + new Date());
			return data;
		}

	
	}
	@Override
	public SDBMediaStatistics getSdbMediaStatisticsByPsam(String psam) {
		com.lakala.base.model.SDBMediaStatistics sdb = this.sdbMediaStatisticsMapperR.selectSdbMediaStatisticsByAPPPsam(psam);
		return sdb;
	}
	
	public List<TCoupon> getTcouponListByPsam(String tel) {
		// TODO Auto-generated method stub
		logger.info("getTcouponListByPsam psam =============== >> " + tel);
		List<TCoupon> tlist = this.tCouponMapperR.getTcouponListByPsam(tel);
		if(tlist != null && tlist.size() > 0){
			for(TCoupon tc : tlist){
				//优惠券号，截取“_”后的部分，传给前端
				String ss = tc.getFavorablecode();
				/*String code = "";
				if(ss.length() <=7){
					code = ss;
				}else{
					code = ss.substring(ss.length()-7, ss.length());
				}*/
				tc.setFavorablecode(ss);
				int n = tc.getUsenum() - tc.getUsednum();
				double mo = tc.getCost();
				BigDecimal   b   =   new   BigDecimal(mo);  
				double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
				tc.setCost(f1);
				tc.setUsenum(n);
			}
		}
		
		return tlist;
	}
	@Override
	public ObjectOutput getshopcommaddr(ShopCommAddrInput ei) {
		
		ObjectOutput data = new ObjectOutput();
		if(ei == null || ei.getNetno() == null || ei.getNetno().equals("")){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		 Map<String, Object> params = new HashMap<String, Object>();
	     params.put("netno", ei.getNetno());
		List<Tshopkeeperaddr> tshopkeeperaddrList = sdbMediaStatisticsMapperR.findShopkeeperAddrByNetNo(params);
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnMsg("验证通过");
		data.set_ReturnData(tshopkeeperaddrList);
		return data;
	}
	@Override
	public ObjectOutput addorder(AddOrderInput in) {
		
		logger.info("addorder start==================== >> " + new Date() + " ; membername : " + in.getMembername());
		ObjectOutput data = new ObjectOutput();
		if(in == null ){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		OrderInfoW order = null;
		try{
			order = changeOrderInfoW(in);
		}catch(Exception e){
			e.printStackTrace();
		}
		List<GoodsW> goodsList = order.getItems();
		/*if(order.getDeviceno() == null || order.getDeviceno().trim().equals("")){
			
			OrderReturnaddorder or = new OrderReturnaddorder();
			
			RetStatusaddorder rs = new RetStatusaddorder();
			rs.setRetCode("9000");
			rs.setErrMsg("终端编号（PSAM）不能为空！");
			or.setRetStatus(rs);
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("终端编号（PSAM）不能为空！");
			data.set_ReturnData(or);
			return data;
			
		}*/
		SDBMediaStatistics sdb = this.getSdbMediaStatisticsByPsam(order.getDeviceno() == null ? "APP150528ENXCCP629" : order.getDeviceno());
		List<GoodsR> goodsListR = new ArrayList<GoodsR>();
		GoodsR gr;
		for(GoodsW gw: goodsList){
			System.out.println("gw.getSkuid() =========== >> " + gw.getSkuid());
			System.out.println("gw.getGoodscount() =========== >> " + gw.getGoodscount());
			System.out.println("gw.getChannelcode() =========== >> " + gw.getChannelcode());
			System.out.println("gw.getGoodscount() =========== >> " + gw.getGoodscount());
			gr = new GoodsR();
			gr.setSkuid(gw.getSkuid());
			gr.setGoodscount(gw.getGoodscount());
			gr.setChannelcode(gw.getChannelcode());
			
			
			goodsListR.add(gr);
		}
		System.out.println("order.getSource() ================== >> " + order.getSource());
		StoreReturnR st = this.selectcartstore(Integer.parseInt(order.getSource()),goodsListR);
		if(st == null){
			OrderReturnaddorder or = new OrderReturnaddorder();
			RetStatusaddorder rs = new RetStatusaddorder();
			rs.setRetCode("9000");
			rs.setErrMsg("该商品不存在或库存不足");
			or.setRetStatus(rs);
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("该商品不存在或库存不足！");
			data.set_ReturnData(or);
			logger.info("addorder end==================== >> " + new Date()  + " ; membername : " + in.getMembername());
			return data;
		}
		RetStatusR rt = st.getRetStatus();
		RetStatusR prt;
		String retCode = rt.getRetCode();
		
		if(retCode.trim().equals("0001") || retCode.trim().equals("9000")){
			
			OrderReturnaddorder or = new OrderReturnaddorder();
			
			RetStatusaddorder rs = new RetStatusaddorder();
			rs.setRetCode("9000");
			rs.setErrMsg(st.getRetStatus().getErrMsg());
			rs.setSkuid(rt.getSkuid());
			rs.setGoodsname(rt.getGoodsname());
			or.setRetStatus(rs);
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(st.getRetStatus().getErrMsg());
			data.set_ReturnData(or);
			logger.info("addorder end==================== >> " + new Date()  + " ; membername : " + in.getMembername());
			return data;
		}else{
			prt = new RetStatusR();
			List<GoodsR> rList = st.getItems();
			
			PlaceOrderVO pvo = new PlaceOrderVO();
			
			//pvo.code = order.getCoupons();
			pvo.pasmId = order.getDeviceno() == null ? "APP150528ENXCCP629" : order.getDeviceno();
			pvo.payChannel = Integer.parseInt(order.getPaychanel());
			pvo.mobile = order.getMembername();
			for(GoodsR ca : rList){
				BigDecimal price = new BigDecimal(1000000000);
				//查价格
				
				ObjectOutput info = null;
				
				try {
					info = this.sideShopBMongoService.detail_mongo(pvo.pasmId, String.valueOf(ca.getChannelcode()), ca.getGoodsid(),"all",1);
				} catch (LakalaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!info._ReturnCode.equals("000000")){
					
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
					data.set_ReturnData(null);
					return data;
					
				}
				
				
				ProductDetailForProductList goods = (ProductDetailForProductList) info.get_ReturnData();
				SkuDetails[] goodsSkuO2OList = goods.getGoodsSkuO2OList();
				for(SkuDetails sku : goodsSkuO2OList){
					if(sku.getTGoodSkuInfoId() == Integer.parseInt(String.valueOf(ca.getSkuid()))){
						if(70 == sku.getType()){
							price = sku.getPromotionPrice(); 
						}else{
							price = new BigDecimal(sku.getSalePrice());
						};
						break;
					}
						
				}
				
				if(price.equals(new BigDecimal(1000000000)) || price.compareTo(new BigDecimal(0)) < 0){
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
					data.set_ReturnData(null);
					return data;
				}
				
				logger.info("skuid ============== >> " +ca.getSkuid());
				logger.info("goodsname ============== >> " +ca.getGoodsname());
				logger.info("price ============== >> " +price);
				logger.info("goodscount ============== >> " +ca.getGoodscount());
				logger.info("channelId ============== >> " +ca.getChannelcode());
				logger.info("skuo2oId ============== >> " +ca.getSkuo2oid());
				logger.info("pasmId ============== >> " +order.getDeviceno());
				
				GoodsVO  gvo  = new  GoodsVO();
				gvo.id = Long.parseLong(String.valueOf(ca.getSkuid()));
				gvo.name = ca.getGoodsname();
				gvo.price = price;
				gvo.num = Integer.parseInt(String.valueOf(ca.getGoodscount()));
				gvo.reducePrice = new BigDecimal("0.00");
				gvo.channelId = ca.getChannelcode();
				gvo.skuo2oId = ca.getSkuo2oid();
				gvo.pasmId = order.getDeviceno() == null ? "APP150528ENXCCP629" : order.getDeviceno();
				gvo.deliveryService = 2;
				gvo.property = 247;
				pvo.addGoods(gvo);
				
				/*pvo.addGoods(new GoodsVO(Long.parseLong(ca.getSkuid()), ca.getGoodsname(),
						price, Integer.parseInt(ca.getGoodscount()),
						new BigDecimal("0.00"),ca.getChannelcode(),ca.getSkuo2oid()));*/
			}
			
			if(order.getCoulist() != null && order.getCoulist().size() > 0){
				List<String> coucodelist = new ArrayList<String>();
				
				List<CouList> couponlist = order.getCoulist();
				CouponBean cb = null;
				List<CouponBean> cblist = new ArrayList<CouponBean>();
				if(couponlist != null){
					for(CouList cs : couponlist){
						  cb = new CouponBean();
						  cb.setCouponCode(cs.getCouponCode());
						  cb.setCount(Integer.parseInt(cs.getCount()));
						  cblist.add(cb);
						  pvo.couponList = cblist;
					}
				}
				
			}
		
			//调用促销模块
			logger.info("---------------OrderRemote start -------------------" + System.currentTimeMillis());
			PlaceOrderVO vo = new PlaceOrderVO();
			if(pvo.goodsList!= null && pvo.goodsList.size() > 0){
				 vo = (PlaceOrderVO) OrderRemote.executeByChannel2("appb", pvo);
			}
			logger.info("---------------OrderRemote end -------------------" + System.currentTimeMillis());
			
			int i = 0;
			CheckCouponReturn ct = new CheckCouponReturn();
			List<CouponBean> cblist1 = vo.couponList;
			if(cblist1 != null && cblist1.size() >0){
				for(CouponBean cb : cblist1){
					if(cb.getIsValidate() == 0){
						i = 1;
						break;
					}
				}
			}
			if(i == 1){
				OrderReturnaddorder or = new OrderReturnaddorder();
				
				RetStatusaddorder rs = new RetStatusaddorder();
				rs.setRetCode("9000");
				rs.setErrMsg("优惠券不可用");
				or.setRetStatus(rs);
				data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
				data.set_ReturnMsg("优惠券不可用！");
				data.set_ReturnData(or);
				return data;
			}
			
			if(vo.useCoupon != null && vo.useCoupon.equals("0")){
			
			}else{
				if(vo.couponList!= null && vo.couponList.size() >0){
					OrderReturnaddorder or = new OrderReturnaddorder();
					
					RetStatusaddorder rs = new RetStatusaddorder();
					rs.setRetCode("9000");
					rs.setErrMsg("优惠券不可用");
					or.setRetStatus(rs);
					data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
					data.set_ReturnMsg("优惠券不可用！");
					data.set_ReturnData(or);
					return data;
				}
				
			}
			
			
			
			//0-满足，。1-不满足
			ct.setDirectDropFlag(vo.directDropFlag);
			ct.setDirectdropmap( vo.goodsDDmap);
			
			if(1 == pvo.directDropFlag){
				
				OrderReturnaddorder or = new OrderReturnaddorder();
				
				RetStatusaddorder rs = new RetStatusaddorder();
				rs.setRetCode("9000");
				rs.setErrMsg("系统忙，请稍后再试");
				//rs.setSkuid(Integer.parseInt(ot.getSkuid()));
				//rs.setGoodsname(ot.getGoodsname());
				or.setRetStatus(rs);
				data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
				data.set_ReturnMsg("系统忙，请稍后再试");
				data.set_ReturnData(or);
				return data;
			}
			
			BigDecimal allfav = vo.favAmount.add(vo.couponPrice);
			BigDecimal zzsk = vo.totalAmount;//最终刷卡金额
			if(zzsk.compareTo(new BigDecimal("0")) <=0){
				allfav  = vo.goodsTotal.add(vo.freight);
			}
			order.setServicetype(vo.fsrFlag);
			order.setGoodsTotal(vo.goodsTotal.setScale(2,   BigDecimal.ROUND_HALF_UP));//纯商品金额
		//	logger.info("促销：vo.goodsTotal ================>>" + vo.goodsTotal);
			order.setFavAmount(vo.favAmount.setScale(2,   BigDecimal.ROUND_HALF_UP));//活动优惠金额
		//	logger.info("促销：vo.favAmount ============= >> " + vo.favAmount);
			order.setCouponPrice(vo.couponPrice.setScale(2,   BigDecimal.ROUND_HALF_UP));//优惠券金额
		//	logger.info("促销：vo.couponPrice ============ >> " + vo.couponPrice);
			order.setTotal(vo.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP));//最终刷卡金额
		//	logger.info("促销：vo.totalAmount =========== >> " + vo.totalAmount);
			order.setJson(vo.json);
			order.setActivityType(String.valueOf(vo.activityType));
			order.setActivityName(vo.promotionName);
			order.setIsUseCoupon(vo.useCoupon);
			order.setLogisfee(vo.freight.setScale(2,   BigDecimal.ROUND_HALF_UP));
			BigDecimal allmo = vo.total.add(vo.freight).subtract(vo.couponPrice);
			//判断最终刷卡金额，如果 <= 0。则置为已支付
			if(vo.totalAmount.compareTo(new BigDecimal("0.00")) == -1 || vo.totalAmount.compareTo(new BigDecimal("0.00")) == 0){
			//	logger.info("=============yes=================");
				order.setIspay("150");
			}else{
			//	logger.info("=============no=================");
				order.setIspay("149");
			}
			List<GoodsVO> gvoList = vo.goodsList;
			GoodsW goodsw;
			List<GoodsW> newGoodsWList = new ArrayList<GoodsW>();
				for(GoodsVO gsvo : gvoList){
						goodsw = new GoodsW();
						goodsw.setSkuid(String.valueOf(gsvo.id));
						goodsw.setSkuo2oid(gsvo.skuo2oId);
						goodsw.setActivityId(String.valueOf(gsvo.activityId));
						goodsw.setActivityName(gsvo.promotionName);
						goodsw.setType(String.valueOf(gsvo.type));
						goodsw.setJson(gsvo.json);
						goodsw.setGoodsname(gsvo.name);
						goodsw.setGoodscount(String.valueOf(gsvo.num));
						goodsw.setGoodssaleprice(gsvo.price.setScale(2,   BigDecimal.ROUND_HALF_UP));
						goodsw.setActualPrice(gsvo.actualPrice.setScale(2,   BigDecimal.ROUND_HALF_UP));
						goodsw.setFavAmount(gsvo.favAmount.setScale(2,   BigDecimal.ROUND_HALF_UP));
						goodsw.setChannelcode(gsvo.channelId);
						goodsw.setCouponAmount(gsvo.couponAmount.setScale(2,   BigDecimal.ROUND_HALF_UP));
						goodsw.setLogisfee(gsvo.freightAmount);
					//	logger.info("gsvo.couponAmount ============== >> " + gsvo.couponAmount);
						if(gsvo.flag == false){
							goodsw.setFlag("false");
						}else{
							goodsw.setFlag("true");
						}
						goodsw.setParentId(String.valueOf(gsvo.parentId));
						for(GoodsW gw:order.getItems()){
							if(gw.getSkuo2oid().equals(goodsw.getSkuo2oid())){

								goodsw.setIs3h(gw.getIs3h());
							}
						}
						newGoodsWList.add(goodsw);
				}
			order.setItems(newGoodsWList);
			order.setMembername(in.getMobile());
			w.com.lakala.order.model.OrderReturn ot = this.orderDubboServiceImplW.insertOrder(order);
			String rtflag = ot.getRtflag();
			if(rtflag.equals("0001") || rtflag.equals("9000")){
				
				OrderReturnaddorder or = new OrderReturnaddorder();
				
				RetStatusaddorder rs = new RetStatusaddorder();
				rs.setRetCode("9000");
				rs.setErrMsg(ot.getErrmsg());
				rs.setSkuid(Integer.parseInt(ot.getSkuid()));
				rs.setGoodsname(ot.getGoodsname());
				or.setRetStatus(rs);
				data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
				data.set_ReturnMsg(ot.getErrmsg());
				data.set_ReturnData(or);
				return data;
				
				
			}else{
				OrderReturnaddorder or = new OrderReturnaddorder();
				RetStatusaddorder rs = new RetStatusaddorder();
				rs.setRetCode("0000");
				or.setRetStatus(rs);
				
				or.setTallorderid(String.valueOf(ot.getTallorderid()));
				or.setTorderid(ot.getOrderid());
				or.setSource(order.getSource());
				or.setCoupons(order.getCoupons());
				or.setCustelno(order.getCustelno());
				or.setCusname(order.getCusname());
				or.setPhonename(order.getPhonename());
				or.setPhoneidcard(order.getPhoneidcard());
				or.setDeviceno(order.getDeviceno() == null ? "APP150528ENXCCP629" : order.getDeviceno());
				or.setUserid(order.getUserid()); 
				or.setLogisfee(order.getLogisfee().setScale(2,   BigDecimal.ROUND_HALF_UP));
				or.setBilltype(order.getBilltype());
				or.setBilltitle(order.getBilltitle());
				or.setIspay(order.getIspay());
				or.setPaychanel(order.getPaychanel());
				or.setPaytype(order.getPaytype());
				or.setPaystage(order.getPaystage());
				or.setBankid(order.getBankid());
				or.setProvinceid(order.getProvinceid());
				or.setCityid(order.getCityid());
				or.setAreaid(order.getAreaid());
				or.setAddressfull(order.getAddressfull());
				or.setCode(order.getCode());
				or.setWeekdevice(order.getWeekdevice());
				or.setDevicetype(order.getDevicetype());
				or.setRequiretime(order.getRequiretime());
			
				
				or.setRemark(order.getRemark());
				or.setGoodsTotal(order.getGoodsTotal().setScale(2,   BigDecimal.ROUND_HALF_UP));
				or.setFavAmount(allfav.setScale(2,   BigDecimal.ROUND_HALF_UP));
				or.setTotal(vo.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP));
				//logger.info("返回值：纯商品价格  order.getGoodsTotal()=========== 》》 " + order.getGoodsTotal());
				//logger.info("返回值：总的优惠金额  fav =========== 》》 " + allfav);
				//logger.info("返回值：最终刷卡金额  vo.totalAmount ===========　》》" + vo.totalAmount);
				//logger.info("返回值：支付状态 order.getIspay()  ===========　》》　" + order.getIspay());
				//logger.info("返回值： 运费： vo.freight      =============》》" + vo.freight);
				List<Goodsaddorder> newgoodsList = new ArrayList<Goodsaddorder>();
				
				Goodsaddorder ng = new Goodsaddorder();
				for(GoodsW gosw : newGoodsWList){
					
					ng.setTorderid(ot.getOrderid());
					ng.setSkuid(gosw.getSkuid());
					ng.setSkuo2oid(gosw.getSkuo2oid());
					ng.setGoodsname(gosw.getGoodsname());
					ng.setGoodssaleprice(gosw.getGoodssaleprice().setScale(2,   BigDecimal.ROUND_HALF_UP));
					ng.setGoodscount(gosw.getGoodscount());
					ng.setFlag(gosw.getFlag());
					ng.setParentId(gosw.getParentId());
					ng.setChannelcode(gosw.getChannelcode());
					newgoodsList.add(ng);
				}
				
				or.setItems(newgoodsList);
				
				final Map<String, Object> result = new HashMap<String, Object>();
			   //调用支付
				try {
					result.put("allorderid", ot.getTallorderid());//订单号
					result.put("paychanel", in.getPaychanel());//支付方式
					result.put("ispay", order.getIspay());//支付状态
					if(!order.getIspay().equals(String.valueOf(Constants.TORDER_ISPAY_YZF))){
						returnHandle(or,result,getGoodsnames(order),in);
						/*if(or.getPaychanel().equals("432")||or.getPaychanel().equals("237")){
							data.set_ReturnData(result);
						}else{
							data.set_ReturnData(result.get("payurl"));
						}*/
					}
					data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
					data.set_ReturnData(result);
				} catch (Exception e) {
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnData("调用支付失败");
				}
				logger.info("addorder end==================== >> " + new Date()  + " ; membername : " + in.getMembername());
				return data;
			}
		}
	
	}
	
	
	
	public StoreReturnR selectcartstore(int source, List<GoodsR> goodsList) {

		String goodsname = "";

		String retCode = "0000";

		String errMsg = "购买成功";

		int skuid = 0;

		int o2oid = 0;

		List<GoodsR> skuList = new ArrayList<GoodsR>();

		GoodsR ca;
		// 订单来源 93:手机商城;94:微信商城;95:开店宝;96:PC商城;334:收款宝;97:其他;357:WAP商城
			for (GoodsR gs : goodsList) {
				System.out.println("gs.getGoodscount() ============= >> " + gs.getGoodscount());
				//int skuo2oid = Integer.parseInt(gs.getSkuo2oid());
				BigDecimal num = new BigDecimal(gs.getGoodscount());

				/*TgoodsPublishKdbSkuO2o o2oInfo = tgoodsPublishKdbSkuO2oMapperR
						.selectsaleo2oinfo(skuo2oid);
				if(o2oInfo == null || o2oInfo.getSkuid() == null){
					return null;
				}*/
				Tgoodskuinfo sku = this.tgoodskuinfoMapperR.selectByPrimaryKey(Integer.parseInt(gs.getSkuid()));

				ca = new GoodsR();
				ca.setGoodsname(sku.getGoodname());
				ca.setGoodscount(gs.getGoodscount());
				ca.setSkuid(gs.getSkuid());
			//	ca.setSkuo2oid(gs.getSkuo2oid());
				ca.setGoodsid(String.valueOf(sku.getTgoodinfoid()));
				//ca.setSaleprice(o2oInfo.getSaleprice());
				ca.setChannelcode(gs.getChannelcode());
				skuList.add(ca);
				logger.info("sku.getSkustock() =========== >> " + sku.getSkustock());
				logger.info("gs.getGoodscount() =========== >> " + gs.getGoodscount());
				if(null != sku.getTempstoregoodsflag() && 378 != sku.getTempstoregoodsflag()){
					if (sku.getSkustock().compareTo(num) < 0) {
						goodsname = sku.getGoodname();
						skuid = sku.getTgoodskuinfoid();
				//		o2oid = skuo2oid;
						retCode = "0001";
						errMsg = goodsname+"库存不足";
						CartStoreR cr = new CartStoreR();
						cr.setSkuid(skuid);
						cr.setSkuo2oid(o2oid);
						cr.setGoodsname(goodsname);
						RetStatusR re = new RetStatusR();
						re.setRetCode(retCode);
						re.setErrMsg(errMsg);
						re.setGoodsname(goodsname);
						re.setSkuid(skuid);
						StoreReturnR st = new StoreReturnR();
						st.setRetStatus(re);
						st.setItems(skuList);

						return st;
					}
				}
				
			}

		CartStoreR cr = new CartStoreR();
		cr.setSkuid(skuid);
		//cr.setSkuo2oid(o2oid);
		cr.setGoodsname(goodsname);
		RetStatusR re = new RetStatusR();
		re.setRetCode(retCode);
		re.setErrMsg(errMsg);
		re.setGoodsname(goodsname);
		re.setSkuid(skuid);
		StoreReturnR st = new StoreReturnR();
		st.setRetStatus(re);
		st.setItems(skuList);

		return st;
	}
	
	
	@Override
	public OrderInfoW changeOrderInfoW(AddOrderInput in) {
		
		OrderInfoW order = new OrderInfoW();
		order.setSource(in.getSource());
		order.setCoupons(in.getCoupons());
		order.setCustelno(in.getCustelno());
		order.setCusname(in.getCusname());
		order.setPhonename(in.getPhonename());
		order.setPhoneidcard(in.getPhoneidcard());
		order.setDeviceno(in.getDeviceno());
		order.setUserid(in.getUserid());
		order.setBilltype(in.getBilltype());
		order.setBilltitle(in.getBilltitle());
		order.setIspay(in.getIspay());
		order.setPaychanel(in.getPaychanel());
		order.setPaytype(in.getPaytype());
		order.setPaystage(in.getPaystage());
		order.setBankid(in.getBankid());
		order.setProvinceid(in.getProvinceid());
		order.setCityid(in.getCityid());
		order.setAreaid(in.getAreaid());
		order.setAddressfull(in.getAddressfull());
		order.setCode(in.getCode());
		order.setWeekdevice(in.getWeekdevice());
		order.setDevicetype(in.getDevicetype());
		order.setRequiretime(in.getRequiretime());
		order.setReturnmoney(in.getReturnmoney());
		order.setRemark(in.getRemark());
		order.setPaytoken(in.getPaytoken());
		order.setCustomer(in.getCustomer());
		order.setMembername(in.getMembername());
		
		List<Map> clist = in.getCoulist();
		
		if(clist != null && clist.size() > 0){
			List<CouList> coulist = new ArrayList<CouList>();
			CouList cou ;
			for(Map co : clist){
				cou = new CouList();
				cou.setCouponCode(String.valueOf(co.get("couponCode")==null ? "": co.get("couponCode")));
				cou.setCount(String.valueOf(co.get("count") == null ? "" :co.get("count")));
				coulist.add(cou);
			}
			order.setCoulist(coulist);
		}
		
		List<Map> items = in.getItems();
		if(items != null && items.size() > 0){
			List<GoodsW> goodslist = new ArrayList<GoodsW>();
			GoodsW gw ;
			for(Map good : items){
				gw = new GoodsW();
				gw.setSkuo2oid(String.valueOf(good.get("skuo2oid")==null?"":good.get("skuo2oid")));
				gw.setSkuid(String.valueOf(good.get("skuid")==null?"":good.get("skuid")));
				gw.setGoodscount(String.valueOf(good.get("goodscount")==null?"":good.get("goodscount")));
				gw.setChannel(String.valueOf(good.get("channel")==null?"":good.get("channel")));
				gw.setChannelcode(String.valueOf(good.get("channelcode")==null?"":good.get("channelcode")));
				gw.setIs3h(Integer.parseInt(String.valueOf(good.get("is3h")==null?"0":good.get("is3h"))));
				gw.setCustelno(String.valueOf(good.get("custelno")==null?"":good.get("custelno")));
				gw.setCusname(String.valueOf(good.get("cusname")==null?"":good.get("cusname")));
				gw.setAddressprovince(String.valueOf(good.get("addressprovince")== null?"":good.get("addressprovince")));
				gw.setAddresscity(String.valueOf(good.get("addresscity")==null?"":good.get("addresscity")));
				gw.setAddressarea(String.valueOf(good.get("addressarea")==null?"":good.get("addressarea")));
				gw.setAddressfull(String.valueOf(good.get("addressfull")==null?"":good.get("addressfull")));
				gw.setIsdelivertohome(Integer.parseInt(String.valueOf(good.get("isdelivertohome")==null?"0":good.get("isdelivertohome"))));
				gw.setCustomer(String.valueOf(good.get("customer")==null?"":good.get("customer")));
				gw.setDeviceno(String.valueOf(good.get("deviceno")==null?"":good.get("deviceno")));
				
				goodslist.add(gw);
			}
			order.setItems(goodslist);
			
		}
		return order;
	}
	
	
	public String getGoodsnames(OrderInfoW resultOrderInfoW){
		List<GoodsW> glist=resultOrderInfoW.getItems();
		String goodsnames=null;
		if(glist!=null && glist.size()>0){
			for(int i=0;i<glist.size();i++){
				if(i==0){
					goodsnames=glist.get(i).getGoodsname();
				}else{
					goodsnames+=","+glist.get(i).getGoodsname();
				}
			}
		}
		return goodsnames;
	}
	
	/**
	 * 
	 * @param resultOrderInfoW
	 * @param result
	 * @param goodsnames
	 */
	private void returnHandle(OrderReturnaddorder resultOrderInfoW,
			final Map<String, Object> result,String goodsnames,AddOrderInput in) {
		String  total = resultOrderInfoW.getTotal().toString();
		if(!resultOrderInfoW.getRetStatus().getRetCode().equals("0000")){
			result.put("massage", resultOrderInfoW.getRetStatus().getErrMsg());
		}else if("0.00".equals(total)){
			result.put("status", "1");
			result.put("pay0", "1");
			result.put("orderId", resultOrderInfoW.getTallorderid());
		}else{
			if(Constants.B2APP_B.equals(resultOrderInfoW.getSource())){
				switch (Integer.parseInt(resultOrderInfoW.getPaychanel())) {
					//拉卡拉快捷
					case Constants.TORDER_PAYCHANNEL_LKL:
	 					toDubboPay(resultOrderInfoW, result, goodsnames, "WAP",in);
						break;
					//拉卡拉刷卡
					case Constants.TORDER_PAYCHANNEL_LKL_CARD:
	 					toDubboPay(resultOrderInfoW, result, goodsnames, "LKL_CARD",in);
						break;
					//支付宝
					case Constants.TORDER_PAYCHANNEL_ZFB:
						toDubboPay(resultOrderInfoW, result, goodsnames, "APP_ALI",in);
						break;
					//微信
					case Constants.TORDER_PAYCHANNEL_WX:
						toDubboPay(resultOrderInfoW, result, goodsnames, "APP_WX_B",in);
						break;
				}
			}
			if(Constants.B2WEIXIN.equals(resultOrderInfoW.getSource()) 
					|| Constants.B2SKB.equals(resultOrderInfoW.getSource()) 
					|| Constants.B2CJSJYH.equals(resultOrderInfoW.getSource())){
				switch (Integer.parseInt(resultOrderInfoW.getPaychanel())) {
					//拉卡拉快捷
					case Constants.TORDER_PAYCHANNEL_LKL:
	 					toDubboPay(resultOrderInfoW, result, goodsnames, "WAP",in);
						break;
					/*//支付宝
					case Constants.TORDER_PAYCHANNEL_ZFB:
						toDubboPay(resultOrderInfoW, result, goodsnames, "ALI",in);
						break;
					//收款宝
					case Constants.TORDER_PAYCHANNEL_SKB:
						toDubboPaySKB(resultOrderInfoW, result, goodsnames, "KDB");
						break;*/
					//微信
					case Constants.TORDER_PAYCHANNEL_WX:
						try {
							String shouquanUrl = getCode(total, resultOrderInfoW,goodsnames);
							result.put("payurl", shouquanUrl);
							logger.info("\r\n"
									+"------------------------------------------------------------------------\r\n"
									+"获取授权地址：" + shouquanUrl +"\r\n"
									+"------------------------------------------------------------------------\r\n");
							
							if(shouquanUrl!=null){
								//支付方式链接地址信息更新到订单表中以备未支付、支付失败的订单下次支付
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("tAllOrderId", resultOrderInfoW.getTallorderid());
								map.put("paytoken", shouquanUrl);				
								updateTokenOfAllorderid(map);
								map.put("source", resultOrderInfoW.getSource());
								map.put("paytype", Constants.WX);
								insertpayrequestinfo(map);
							}
						} catch (Exception e) {
							result.put("massage", "支付调用失败！");
							e.printStackTrace();
						}
						break;
				}
			}
		}
	}
	
	private void toDubboPay(OrderReturnaddorder resultOrderInfoW,
			Map<String, Object> result, String goodsnames, String payType,AddOrderInput in) {
		ReturnBillInfo returnBillInfo = null;
		try {
			if("WAP".equals(payType)||"LKL_CARD".equals(payType)){//拉卡拉走内网
				returnBillInfo = this.payLakala(resultOrderInfoW.getTallorderid(), resultOrderInfoW.getTotal().toString(), payType, goodsnames,in.getMobile(),resultOrderInfoW.getSource());
			}else{//支付宝和微信走外网
				returnBillInfo = this.payLakalaOut(resultOrderInfoW.getTallorderid(), resultOrderInfoW.getTotal().toString(), payType, goodsnames);
			}
			if(returnBillInfo!=null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tAllOrderId", returnBillInfo.getResorderid());
				String payurl = "";
				if("APP_ALI".equals(payType)){//支付宝
					result.put("payurl", returnBillInfo.getParams().get("payInfo").toString());
					map.put("paytoken", returnBillInfo.getParams().get("payInfo").toString());
				}else if("APP_WX_B".equals(payType)){
					Map<String,Object> resultmap = new HashMap<String,Object>();
					resultmap.put("appid",returnBillInfo.getParams().get("appid").toString());
					resultmap.put("noncestr",returnBillInfo.getParams().get("noncestr").toString());
					resultmap.put("package",returnBillInfo.getParams().get("package").toString());
					resultmap.put("partnerid",returnBillInfo.getParams().get("partnerid").toString());
					resultmap.put("prepayid",returnBillInfo.getParams().get("prepayid").toString());
					resultmap.put("sign",returnBillInfo.getParams().get("sign").toString());
					resultmap.put("timestamp",returnBillInfo.getParams().get("timestamp").toString());
					payurl = JSON.toJSONString(resultmap);
					result.put("payurl",resultmap);
					map.put("paytoken", payurl);
				}else if("WAP".equals(payType)){//拉卡拉快捷、拉卡拉钱包
					result.put("mobile",in.getMobile());
					result.put("password",in.getPassword());
					result.put("token",in.getToken());
					result.put("payurl",returnBillInfo.getPayurl());
					map.put("paytoken", returnBillInfo.getPayurl());
				}else{//拉卡拉刷卡支付(收款宝暂时用不到)
					Map<String,Object> resultmap = new HashMap<String,Object>();
					resultmap.put("authtoken",returnBillInfo.getParams().get("AuthToken").toString());
					resultmap.put("transname",returnBillInfo.getParams().get("TransName").toString());
					UserLoginInput user = new UserLoginInput();
					user.setMobile(in.getMobile());
					UserInfoOutput userInfoOut = userService.getPassword(user).get_ReturnData();
					if(userInfoOut != null){
						resultmap.put("password",userInfoOut.getPwd());
					}
					payurl = JSON.toJSONString(resultmap);
					result.put("payurl",payurl);
					map.put("paytoken", payurl);
				}
				updateTokenOfAllorderid(map);
				if(payType.equals("WAP") || payType.equals("LKL_CARD")){
					map.put("paybillno", returnBillInfo.getResbillno());
					updateTallorderid(map);
					//拉卡拉支付信息存到tpayrequestinfo表
					map.put("source", resultOrderInfoW.getSource());
					map.put("paytype", payType);
					insertpayrequestinfo(map);
				}else{
					//插入tpayrequestinfo表构建数据
					map.put("source", resultOrderInfoW.getSource());
					map.put("paytype", payType);
					insertpayrequestinfo(map);
				}
			}else{
				result.put("massage","支付调用失败！");
			}
		} catch (Exception e) {
			result.put("massage","支付调用失败！");
		}
	}
	public void insertpayrequestinfo(Map<String,Object> map){
		payRequestInfoMapper_w.insertpayrequestinfo(map);
	}
	/**
	 * 收款宝支付
	 * @param resultOrderInfoW
	 * @param result
	 * @param goodsnames
	 * @param payType
	 */
	private void toDubboPaySKB(OrderReturnaddorder resultOrderInfoW,
			Map<String, Object> result, String goodsnames, String payType) {
		ReturnBillInfo returnBillInfo = null;
		try {
			BillInfo billInfo = new BillInfo();
			billInfo.setOrderID(resultOrderInfoW.getTallorderid());
			billInfo.setInstallment("");
			billInfo.setAmount(resultOrderInfoW.getTotal());
			billInfo.setPaytype(payType);
			billInfo.setCallbackurl(Constants.CALL_BACK_URL+"/"+resultOrderInfoW.getTallorderid());
//			billInfo.setNotifyURL(this.notifyurl);
			billInfo.setGoodsname(goodsnames);
			
			returnBillInfo= this.payLakalaServiceIntranet.payLakala(billInfo);	
			billInfo.setBillno(returnBillInfo.getResbillno());
			logger.info("returnBillInfo.getResbillno()==========="+returnBillInfo.getResbillno());

			ReturnBillInfo rb=this.paySKB(billInfo);
			logger.info("rb.getPayurl()==========="+rb.getPayurl());
			if(rb.getPayurl()!=null){
				//支付方式链接地址信息更新到订单表中以备未支付、支付失败的订单下次支付
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tAllOrderId", resultOrderInfoW.getTallorderid());
				map.put("paytoken", rb.getPayurl());
				map.put("paybillno", returnBillInfo.getResbillno());
				this.updateTokenOfAllorderid(map);//更新支付收银台地址
				this.updatePaybillnoOfTallorderid(map);//更新账单号
			}
			result.put("status", "1");
			result.put("payurl", rb.getPayurl());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("massage", "支付调用失败！");
		}
	}
	
	/**
	 * 生成微信支付code
	 * @param total
	 * @param resultOrderInfoW
	 * @param goodsnames
	 * @return
	 */
	private String getCode(String total, OrderReturnaddorder resultOrderInfoW,String goodsnames) {
		try {
			String url=constantsUtil.getWxcallbackurl();
			Map<String, String> params = new HashMap<String,String>();
			params.put("goodsnames", goodsnames);
			params.put("orderid", resultOrderInfoW.getTallorderid());
			params.put("total", total);
			params.put("redirect_succ_uri", "2B_M_URL_SUCCESS");//收款宝成功跳转页面
			params.put("redirect_fail_uri", "2B_M_URL_FAIL");//收款宝失败跳转页面
			logger.info("goodsnames==========="+goodsnames);
			logger.info("orderid==========="+resultOrderInfoW.getTallorderid());
			logger.info("total==========="+total);
			String code = HttpSend.post(url, params);			
			logger.info("getCode获取微信支付code==========="+code);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public int updateTokenOfAllorderid(Map<String, Object> map) {
		return torderMapperW.updateTokenOfAllorderid(map);
	}
	
	
	@Override
	public int updatePaybillnoOfTallorderid(Map<String, Object> map) {
		return torderMapperW.updatePaybillnoOfTallorderid(map);
	}
	@Override
	public int updateTallorderid(Map<String, Object> map) {
		return torderMapperW.updateTallorderid(map);
	}
	//内网支付----拉卡拉支付方式
	@Override
	public ReturnBillInfo payLakala(String orderid, String total, String payType, String goodsnames,String loginname,String source){
		BillInfo billInfo = new BillInfo();
		billInfo.setLoginname(loginname);
		billInfo.setSource(Integer.parseInt(source));
		billInfo.setOrderID(orderid);
		billInfo.setAmount(new BigDecimal(total));
		billInfo.setPaytype(payType);
		billInfo.setCallbackurl(constantsUtil.getCallbackurl()+source);
		billInfo.setGoodsname(goodsnames);
		return payLakalaServiceIntranet.payLakala(billInfo);
	}
	//外网支付----支付宝、微信等支付方式
	@Override
	public ReturnBillInfo payLakalaOut(String orderid, String total, String payType, String goodsnames){
		BillInfo billInfo = new BillInfo();
		billInfo.setOrderID(orderid);
		billInfo.setAmount(new BigDecimal(total));
		billInfo.setPaytype(payType);
		//billInfo.setCallbackurl("lklside://");
		billInfo.setGoodsname(goodsnames);
		
		return this.payLakalaServiceExtranet.payLakala(billInfo);
	}
	/**
	 *SKB支付
	 */

  public  ReturnBillInfo paySKB(BillInfo billInfo){
	  
	  //获取账单号
	  ReturnBillInfo returnBillInfo= new ReturnBillInfo();
	   //随机数
		Random random=new Random();
		String rand=String.valueOf(random.nextInt(999999));
		//
		Map<String, Object> skbParmsInfo= getSkbParmsInfo(billInfo,rand);
		//生成收款宝加密参数----第一次加密
		  JSONObject jsonObject = JSONObject.fromObject(skbParmsInfo);
		  logger.info("jsonObject=====第一次加密==================="+jsonObject);
        DES crypt = new DES(Constant.PAY_SKB_KEY);
        String params=null;
        try {
        	 params=crypt.encrypt(jsonObject.toString());
        	logger.info("Encode===========:" + crypt.encrypt(jsonObject.toString()));
		} catch (Exception e) {
			logger.error("进行DES 加密错误 ！");
		}
	  //生成唤醒收银台参数---第二次加密
		//签名

		String sign=null;
		try {
			sign = SecurityFactory.getOperator(SecurityFactory.TYPE_RSA).sign(jsonObject.toString(), Constant.SKB_PRIVATE_KEY);
			logger.info("sign====RSA=======:" + sign);

		} catch (Exception e) {
			logger.error("进行RSA 加密 生成 sign 错误 ！");
		}
    	
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("channelCode", Constant.PAY_SKB_CHANNELNO);
		paramMap.put("params", params);
		paramMap.put("crypType", "1");
		paramMap.put("sign",sign);
		logger.info("paramMap=======:" + JSONObject.fromObject(paramMap).toString());
		String p=Base64.encode(JSONObject.fromObject(paramMap).toString().getBytes());
		String skburl=Constant.PAY_SKB_URL+p;
		returnBillInfo.setPayurl(skburl);
		return returnBillInfo;
  }
	
  public  Map<String, Object> getSkbParmsInfo(BillInfo billInfo,String rand){
	  String sign=getSign(billInfo,rand);
	  logger.info("sign====="+sign);
	  Map<String, Object> paramsMap = new HashMap<String, Object>();
	  paramsMap.put("callbackUrl", billInfo.getCallbackurl());//回调地址
	  paramsMap.put("billType", "1");//账单类型 1--第三方(账单号支付)，0--快捷（订单号支付）
	  paramsMap.put("billNo", billInfo.getBillno());//账单号
	  paramsMap.put("amount", billInfo.getAmount());//交易金额
	  paramsMap.put("timeStamp", DateUtil.getTodayFormat2());//时间戳
	  
	  paramsMap.put("ver", Constant.PAY_SKB_VER);//版本号
	  paramsMap.put("merId", Constant.PAY_SKB_MERID);//商户编号
	  paramsMap.put("minCode", Constant.PAY_SKB_MINCODE);//编号
	  paramsMap.put("crypType", "1");//加密方式  1 
	  
	  paramsMap.put("productName", billInfo.getGoodsname());//商品名称
	  paramsMap.put("productDesc", "");//商品描述
	  paramsMap.put("remark", "");//订单备注
	  paramsMap.put("expriredtime", "");//失效时间			  
	  paramsMap.put("randnum", rand);//失效时间
	  paramsMap.put("sign", sign);//签名
	  return paramsMap;
  }
  
  /**
   * 生成签名
   * 进行MD5：ver|merId|商户密码|orderId|amount|randnum|支付URL|macType|minCode|
   */
  public String getSign(BillInfo billInfo,String rand){
	  StringBuffer sb = new StringBuffer();
      BigDecimal b1 = billInfo.getAmount();
      BigDecimal b2 = new BigDecimal(100);
      BigDecimal b3=b1.multiply(b2);
	  sb.append(Constant.PAY_SKB_VER).append("|")
	  	.append(Constant.PAY_SKB_MERID).append("|")
	  	.append(Constant.PAY_SKB_PASSWORD).append("|")
	  	.append(billInfo.getBillno()).append("|")//账单号
	  	.append(new DecimalFormat("0").format(b3)).append("|")//金额：100.01 元
	  	.append(rand).append("|")//随机数
	  	.append(billInfo.getCallbackurl()).append("|")//同步回调地址
	  	.append(Constant.PAY_SKB_MACTYPE).append("|")//1、数字签名，2、MD5
	  	.append(Constant.PAY_SKB_MINCODE).append("|");//编号			  
	String mac=null;
	try {
		mac = MD5Util.getMD5(new String(sb.toString().getBytes(), "GBK"));
	} catch (UnsupportedEncodingException e) {
		logger.error("进行MD5 生成 Sign 错误 ！");
		e.printStackTrace();
	}
	  
	  return mac;
  }
  /**
   * 增加收货地址
   */
@Override
public ObjectOutput insertAddress(Tshopkeeperaddr ska) {
	ObjectOutput data = new ObjectOutput();
	if(ska == null || ska.getNetno() == null || ska.getNetno().equals("")){
		data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
		data.set_ReturnMsg("对象为空!");
		return data;
	}
	//构造参数
	//通过netno查询省市区id和name
	List<SDBMediaStatistics> sdblist = sdbMediaStatisticsMapperR.selectByNetno(ska.getNetno());
	if(sdblist != null && sdblist.size() > 0){
		SDBMediaStatistics sdb = sdblist.get(0);
		ska.setShipprovinceid(Integer.parseInt(sdb.getProvNo()));
		ska.setShipprovince(sdb.getProv());
		ska.setShipcityid(Integer.parseInt(sdb.getCityNo()));
		ska.setShipcity(sdb.getCity());
		ska.setShipareaid(Integer.parseInt(sdb.getCityAreaNo()));
		ska.setShiparea(sdb.getCityArea());
	}
	//插入收货地址
	try {
		sdbMediaStatisticsMapper_w.insertAddress(ska);
	} catch (Exception e) {
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg("插入失败!");
		return data;
	}
	data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
	return data;
}

/**
 * 更新收货地址
 */
@Override
public ObjectOutput updateAddress(Tshopkeeperaddr ska) {
	ObjectOutput data = new ObjectOutput();
	if(ska == null){
		data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
		data.set_ReturnMsg("对象为空!");
		return data;
	}
	//插入收货地址
	try {
		sdbMediaStatisticsMapper_w.updateAddress(ska);
	} catch (Exception e) {
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg("更新失败!");
		return data;
	}
	data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
	return data;
}

/**
 * 删除收货地址
 */
@Override
public ObjectOutput deleteAddress(Tshopkeeperaddr ska) {
	ObjectOutput data = new ObjectOutput();
	if(ska == null){
		data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
		data.set_ReturnMsg("对象为空!");
		return data;
	}
	//插入收货地址
	try {
		sdbMediaStatisticsMapper_w.deleteAddress(ska);
	} catch (Exception e) {
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg("删除失败!");
		return data;
	}
	data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
	return data;
}

private static ClassPathXmlApplicationContext context;

public static void main(String[] args) {
	//只负责加载配置文件，启动服务 
	context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml","applicationContext-dubbo.xml"});
    context.start();
    GenerateOrderService go = (GenerateOrderService) context.getBean("generateOrderServiceImpl");
//    ReturnBillInfo orb = go.payLakala("15041616492119732368", "2", "LKL_CARD", "苹果6plus","dd");
//    System.out.println(orb.getParams());
}
}
