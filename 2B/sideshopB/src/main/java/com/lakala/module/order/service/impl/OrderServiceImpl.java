package com.lakala.module.order.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import w.com.lakala.order.model.Tlogisticsinfo;
import w.com.lakala.order.model.Tlogisticsinfoitem;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.base.model.TgoodskuinfoWithBLOBs;
import com.lakala.base.model.Torderitems;
import com.lakala.base.model.Torderprovider;
import com.lakala.exception.LakalaException;
import com.lakala.model.Torder;
import com.lakala.model.order.OrderInfo;
import com.lakala.model.order.OrderProviderQueryInfo;
import com.lakala.model.order.OrderProviderV2Info;
import com.lakala.model.order.OrderProviderV2QueryInfo;
import com.lakala.model.order.OrderV2Info;
import com.lakala.model.user.TShopService;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.vo.in.StoreChangeInPut;
import com.lakala.module.order.model.Constant;
import com.lakala.module.order.service.Mongo4GoodsService;
import com.lakala.module.order.service.OrderService;
import com.lakala.module.order.vo.OrderCntInput;
import com.lakala.module.order.vo.OrderCntOutput;
import com.lakala.module.order.vo.OrderDetailInput;
import com.lakala.module.order.vo.OrderDetailOutput;
import com.lakala.module.order.vo.OrderInfoInput;
import com.lakala.module.order.vo.OrderInfoOutput;
import com.lakala.module.order.vo.OrderMultQueryInput;
import com.lakala.module.order.vo.OrderMultQueryOutput;
import com.lakala.module.order.vo.OrderV2InfoInput;
import com.lakala.module.order.vo.OrderV2InfoOutput;
import com.lakala.module.order.vo.ToPayInput;
import com.lakala.module.sreturn.model.ReturnMoble;
import com.lakala.module.sreturn.service.ReturnService;
import com.lakala.util.BussConst;
import com.lakala.util.HttpUtil;
import com.lakala.util.Msg2App2CUtil;
import com.lakala.util.ReturnMsg;
import com.lakala.util.SendMessageUtil;


@Service("OrderServiceBean")
public class OrderServiceImpl implements OrderService {
	
	Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
    private  com.lakala.mapper.r.order.TorderMapper torderMapper_R;
	
	@Autowired
    private  com.lakala.mapper.w.order.TorderMapper torderMapper_W;
	
	@Autowired
	private com.lakala.mapper.r.returns.ReturnMapper returnMapper_R;
	
	@Autowired
	private com.lakala.mapper.r.order.TorderitemsMapper torderitemsMapper_R;
	
	@Autowired
	private com.lakala.mapper.w.order.TorderitemsMapper torderitemsMapper_W;
	
	@Autowired
	private com.lakala.mapper.r.order.TorderproviderMapper torderproviderMapper_R;
	
	@Autowired
	private com.lakala.mapper.w.order.TorderproviderMapper torderproviderMapper_W;
	
	@Autowired
	private com.lakala.mapper.w.logisticsinfo.TlogisticsinfoMapper tlogisticsinfoMapper_W;
	
	@Autowired
	private com.lakala.mapper.w.logisticsinfo.TlogisticsinfoitemMapper tlogisticsinfoitemMapper_W;
	
	@Autowired
	private com.lakala.mapper.r.coupon.TordercouponsMapper tordercouponsMapper_R;
	
	@Autowired
	private com.lakala.mapper.w.coupon.TordercouponsMapper tordercouponsMapper_W;
	
	@Autowired
	private ReturnService returnService;
	
	@Autowired
	private com.lakala.util.MongoInterfaceUrl cfg;
	
	
	@Autowired
    private com.lakala.mapper.r.goods.TgoodskuinfoMapper tgoodskuinfoMapper_R;
	
	@Autowired
    private Mongo4GoodsService mongo4GoodsService;
	
	@Autowired
    private com.lakala.mapper.r.sdbmediastatistics.TShopServiceMapper tshopservicemapper_R;
	
	
	private static Map<String,Object> pf_needpay_map = new HashMap<String,Object>();  //待付款总数-用于批发订单
	private static Map<String,Object> pf_nodeliver_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
	private static Map<String,Object> pf_delivered_map = new HashMap<String,Object>();  //待收货总数-用于批发订单
	private static Map<String,Object> pf_partreceive_map = new HashMap<String,Object>();  //部分收货总数-用于批发订单
	private static Map<String,Object> pf_received_map = new HashMap<String,Object>();  //已收货总数-用于批发订单
	private static Map<String,Object> ls_lsdqr_map = new HashMap<String,Object>();   //待确认总数-用于零售订单
	private static Map<String,Object> ls_lsdzt_map = new HashMap<String,Object>();   //待自提总数-用于零售订单
	private static Map<String,Object> ls_lsdsh_map = new HashMap<String,Object>();   //待送货总数-用于零售订单
	private static Map<String,Object> ls_nodeliver_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
	private static Map<String,Object> ls_delivered_map = new HashMap<String,Object>(); //已发货总数 -用于零售订单
	private static Map<String,Object> ls_signed_map = new HashMap<String,Object>(); //已签收总数 -用于零售订单
	private static Map<String,Object> ls_lsyqx_map = new HashMap<String,Object>();   //已取消总数-用于零售订单
	
	@Override
	public Torder loadOrderByKey(String orderId) throws LakalaException{
		//判断orderId合法性
		if(orderId == null || orderId.equalsIgnoreCase("")) return null;
		Torder order = null;
		try{
			order = torderMapper_R.selectByPrimaryKey(orderId);
		}catch(Exception e){
			throw new LakalaException(e.getMessage());
		}
		return order;
	}

	@Override
	public List<Torder> listOrderByKey(String key) throws LakalaException{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public ObjectOutput orderMultiquery(OrderMultQueryInput orderinput)
			throws LakalaException {

		ObjectOutput data = new ObjectOutput();
		if (orderinput == null || StringUtils.isEmpty(orderinput.getEcnetno())) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}

		List<OrderProviderQueryInfo> orderproviderlist = new ArrayList<OrderProviderQueryInfo>(); 
		Integer total = 0;
		Map<String, Object> map = new HashMap();
		OrderMultQueryOutput info = new OrderMultQueryOutput();

		String[] stateattr = {};
		String[] sourceattr = {};
		String[] ispayattr = {};
		String[] paychannelattr = {};
		String state = orderinput.getState();
		String source = orderinput.getSource();
		String ispay = orderinput.getIspay();
		String paychannel = orderinput.getPaychannel();
		String torderid = orderinput.getTorderid();
		String starttime = orderinput.getStarttime();
		String endtime = orderinput.getEndtime();
		String custelno = orderinput.getCustelno();
		String mobile = orderinput.getMobile();
		String is3h = orderinput.getIs3h();
		String ispfchannelcode = orderinput.getIspfchannelcode();
		String nopfchannelcode = orderinput.getNopfchannelcode();
		String siteno = orderinput.getSiteno();
		String ecnetno = orderinput.getEcnetno();  //电商网点编号
		// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
		String queryordertotal = orderinput.getQueryordertotal();
		String isdelivertohome = orderinput.getIsdelivertohome();

		Integer page = orderinput.getPage() == null ? 1 : orderinput.getPage();
		Integer rows = orderinput.getPageSize() == null ? 10 : orderinput
				.getPageSize();
		Integer start = (page - 1) * rows;
		map.put("start", start);
		map.put("rows", rows);

		// 待发货显示已支付的，或者货到付款的
		map.put("ispayfornodeliver", "true");
		map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);

		if (!StringUtils.isEmpty(state)) {
			state = orderinput.getState();
			if (state.startsWith(",")) {
				stateattr = state.substring(1).split(",");
			} else {
				stateattr = state.split(",");
			}
			map.put("stateids", stateattr);
		}

		if (!StringUtils.isEmpty(source)) {
			if (source.startsWith(",")) {
				sourceattr = source.substring(1).split(",");
			} else {
				sourceattr = source.split(",");
			}
			map.put("sourceids", sourceattr);
		}

		if (!StringUtils.isEmpty(ispay)) {
			if (ispay.startsWith(",")) {
				ispayattr = ispay.substring(1).split(",");
			} else {
				ispayattr = ispay.split(",");
			}
			map.put("ispays", ispayattr);
		}
		if (!StringUtils.isEmpty(paychannel)) {
			if (paychannel.startsWith(",")) {
				paychannelattr = paychannel.substring(1).split(",");
			} else {
				paychannelattr = paychannel.split(",");
			}
			map.put("paychannels", paychannelattr);
		}

		if (!StringUtils.isEmpty(torderid)) {
			map.put("torderid", torderid);
		}

		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime);
		}

		if (!StringUtils.isEmpty(orderinput.getEndtime())) {
			map.put("endtime", endtime);
		}

		if (!StringUtils.isEmpty(custelno)) {
			map.put("custelno", custelno);
		}

		if (!StringUtils.isEmpty(mobile)) {
			map.put("mobile", mobile);
		}

		if (!StringUtils.isEmpty(is3h)) {
			map.put("is3h", is3h);
		}

		if (!StringUtils.isEmpty(ispfchannelcode)) {
			map.put("ispfchannelcode", "true");
		}

		if (!StringUtils.isEmpty(nopfchannelcode)) {
			map.put("nopfchannelcode", "true");
		}
		
		if (!StringUtils.isEmpty(siteno)) {
			map.put("siteno", siteno);
		}
		
		if (!StringUtils.isEmpty(ecnetno)) {
			map.put("ecnetno", ecnetno);
		}
		
		if (!StringUtils.isEmpty(isdelivertohome)) {
			map.put("isdelivertohome", isdelivertohome);
		}

		try {
			orderproviderlist = torderMapper_R
					.queryCommonOrderProviderByMap(map);
			info.setOrderproviderlist(orderproviderlist);

			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(map);
				info.setTotal(total);
			}
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnData(info);
			data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e.getMessage());
		}

		return data;
	}
	
	

	@Override
	public List<OrderInfo> queryCommonOrderByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return torderMapper_R.queryCommonOrderByMap(map);
	}

	@Override
	public Integer countCommonOrderByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return torderMapper_R.countCommonOrderByMap(map);
	}

	@Override
	public ObjectOutput orderView(OrderDetailInput orderdetailinput)
			throws LakalaException {
		// TODO Auto-generated method stub
		ObjectOutput data = new ObjectOutput();
		if (orderdetailinput == null
				|| StringUtils.isEmpty(orderdetailinput.getEcnetno())
				|| (StringUtils.isEmpty(orderdetailinput.getTallorderid()) 
			       && StringUtils.isEmpty(orderdetailinput.getTorderproviderid()))
			    ||(!StringUtils.isEmpty(orderdetailinput.getTallorderid()) 
					       && !StringUtils.isEmpty(orderdetailinput.getTorderproviderid()))) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}

		OrderDetailOutput info = new OrderDetailOutput();
		try{
			if(!StringUtils.isEmpty(orderdetailinput.getTallorderid()) ){
				
				OrderInfo orderinfo = torderMapper_R.viewOrderByMap(orderdetailinput);	
				info.setOrderinfo(orderinfo);
			}else if(!StringUtils.isEmpty(orderdetailinput.getTorderproviderid())){
				
				OrderProviderV2QueryInfo orderproviderinfo = torderMapper_R.viewCommonOrderProviderByMap(orderdetailinput);	
				info.setOrderproviderqueryinfo(orderproviderinfo);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new LakalaException(e.getMessage());
		}
		
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(info);
		data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		return data;
	}
    
	
	@Override
	public ObjectOutput orderEntryCnt(OrderCntInput ordercntinput)
			throws LakalaException {
		// TODO Auto-generated method stub
		
		ObjectOutput data = new ObjectOutput();
		if (ordercntinput == null
				|| StringUtils.isEmpty(ordercntinput.getOrdercate())
				|| StringUtils.isEmpty(ordercntinput.getEcnetno())
				|| (!"pfjh".equals(ordercntinput.getOrdercate())
						&& !"lsdd".equals(ordercntinput.getOrdercate()) && !"xdzsh"
							.equals(ordercntinput.getOrdercate())&& !"all"
							.equals(ordercntinput.getOrdercate()))) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}

		OrderCntOutput info = new OrderCntOutput();
		try{
			info = getOrderEntryCnt(ordercntinput);
		}catch(Exception e){
			throw new LakalaException(e.getMessage());
		}
		
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(info);
		data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		return data;
	}
	
	
	/**
	 * 根据输入返回OrderCntOutput实体
	 * @param ordercntinput
	 * @return  OrderCntOutput实体
	 * @author: yhg 
	 * @time: 2015年3月12日 下午6:04:55
	 * @todo: TODO
	 */
	public OrderCntOutput getOrderEntryCnt(OrderCntInput ordercntinput){
		
		OrderCntOutput info = new OrderCntOutput();
		String ecnetno = ordercntinput.getEcnetno();
		
		
         if("pfjh".equals(ordercntinput.getOrdercate())||"all".equals(ordercntinput.getOrdercate())){
			
        	Integer pf2needpaynum = 0;    //待付款总数-用于批发订单
        	Integer pf2nodelivernum = 0;  //待发货总数-用于批发订单
        	Integer pf2deliverednum = 0;  //已发货总数-用于批发订单
        	Integer pf2partreceivenum = 0; //部分收货总数-用于批发订单
        	Integer pf2receivednum = 0;  //已收货总数-用于批发订单
        	
        	//Map<String,Object> pf_needpay_map = new HashMap<String,Object>();  //待付款总数-用于批发订单
        	pf_needpay_map.put("mobile", ordercntinput.getMobile());
        	pf_needpay_map.put("ispfchannelcode", "true");
			pf_needpay_map.put("state", Constant.TORDER_STATE_WFH);
			pf_needpay_map.put("ispay", Constant.TORDER_ISPAY_DZF);
			pf_needpay_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pf_needpay_map.put("isrestore", Constant.TORDER_ISRESTORE_WTB);
        	pf2needpaynum = torderMapper_R.countCommonOrderByMap(pf_needpay_map);
			
			//Map<String,Object> pf_nodeliver_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
			pf_nodeliver_map.put("ecnetno", ecnetno);
			pf_nodeliver_map.put("ispfchannelcode", "true");
			pf_nodeliver_map.put("state", Constant.TORDER_STATE_WFH);
			pf_nodeliver_map.put("ispayfornodeliver", "true");
			pf_nodeliver_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pf2nodelivernum = torderMapper_R.countCommonOrderProviderByMap(pf_nodeliver_map);

			//Map<String,Object> pf_delivered_map = new HashMap<String,Object>();  //已发货总数-用于批发订单
			pf_delivered_map.put("ecnetno", ecnetno);
			pf_delivered_map.put("ispfchannelcode", "true");
			//原已发货列表变更为待收货列表。待收货包括显示已发货的和部分签收的数据
			pf_delivered_map.put("stateids", new String[]{String.valueOf(Constant.TORDER_STATE_YFH),String.valueOf(Constant.TORDER_STATE_BFQS)});
			pf_delivered_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pf2deliverednum = torderMapper_R.countCommonOrderProviderByMap(pf_delivered_map);

			//Map<String,Object> pf_partreceive_map = new HashMap<String,Object>();  //部分收货总数-用于批发订单
/*			pf_partreceive_map.put("ecnetno", ecnetno);
			pf_partreceive_map.put("ispfchannelcode", "true");
			pf_partreceive_map.put("state", Constant.TORDER_STATE_BFQS);
			pf_partreceive_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pf2partreceivenum = torderMapper_R.countCommonOrderProviderByMap(pf_partreceive_map);*/

			//Map<String,Object> pf_received_map = new HashMap<String,Object>();  //已收货总数-用于批发订单
			pf_received_map.put("ecnetno", ecnetno);
			pf_received_map.put("ispfchannelcode", "true");
			pf_received_map.put("state", Constant.TORDER_STATE_YQS);
			pf_received_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pf2receivednum = torderMapper_R.countCommonOrderProviderByMap(pf_received_map);
			
			info.setPf2needpaynum(pf2needpaynum==null?0:pf2needpaynum);  //待付款总数-用于批发订单
			info.setPf2nodelivernum(pf2nodelivernum==null?0:pf2nodelivernum);  //待发货总数-用于批发订单
			info.setPf2deliverednum(pf2deliverednum==null?0:pf2deliverednum);  //已发货总数-用于批发订单
			info.setPf2partreceivenum(pf2partreceivenum==null?0:pf2partreceivenum);  //部分收货总数-用于批发订单
			info.setPf2receivednum(pf2receivednum==null?0:pf2receivednum);  //已收货总数-用于批发订单
			
		}
         
        if("lsdd".equals(ordercntinput.getOrdercate())||"all".equals(ordercntinput.getOrdercate())){
			
			Integer ls2waitconfirmnum = 0;  //待确认总数-用于零售订单
			Integer ls2waittakenum = 0;  //待自提总数-用于零售订单
			Integer ls2waitsendnum = 0;  //待送货总数-用于零售订单
			Integer ls2nodelivernum = 0;  //待发货总数-用于零售订单
			Integer ls2deliverednum = 0;  //已发货总数 -用于零售订单
			Integer ls2signednum = 0;  //已签收数 -用于零售订单
			Integer ls2aftersalenum = 0;  //售后总数 -用于零售订单
			Integer ls2calcelednum = 0;  //已取消总数-用于零售订单
			
			//Map<String,Object> ls_lsdqr_map = new HashMap<String,Object>();   //待确认总数-用于零售订单
			ls_lsdqr_map.put("ecnetno", ecnetno);
			ls_lsdqr_map.put("nopfchannelcode", "true");
			ls_lsdqr_map.put("state", Constant.TORDER_STATE_WFH);
			ls_lsdqr_map.put("ispayfornodeliver", "true");
			ls_lsdqr_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_lsdqr_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsdqr_map.put("source", Constant.TORDER_SOURCE_APPC);
			ls2waitconfirmnum = torderMapper_R.countCommonOrderProviderByMap(ls_lsdqr_map);
			
			//Map<String,Object> ls_lsdzt_map = new HashMap<String,Object>();   //待自提总数-用于零售订单
			ls_lsdzt_map.put("ecnetno", ecnetno);
			ls_lsdzt_map.put("nopfchannelcode", "true");
			ls_lsdzt_map.put("state", Constant.TORDER_STATE_YFH);
			ls_lsdzt_map.put("ispayfornodeliver", "true");
			ls_lsdzt_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_lsdzt_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsdzt_map.put("isdelivertohome", Constant.TORDER_ISDELIVERTOHOME_KDDD); //待自提
			ls_lsdzt_map.put("source", Constant.TORDER_SOURCE_APPC);
			ls2waittakenum = torderMapper_R.countCommonOrderProviderByMap(ls_lsdzt_map);
			
			
			//Map<String,Object> ls_lsdsh_map = new HashMap<String,Object>();   //待送货总数-用于零售订单
			ls_lsdsh_map.put("ecnetno", ecnetno);
			ls_lsdsh_map.put("nopfchannelcode", "true");
			ls_lsdsh_map.put("state", Constant.TORDER_STATE_YFH);
			ls_lsdsh_map.put("ispayfornodeliver", "true");
			ls_lsdsh_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_lsdsh_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsdsh_map.put("isdelivertohome", Constant.TORDER_ISDELIVERTOHOME_KDDJ); //送货上门
			ls_lsdsh_map.put("source", Constant.TORDER_SOURCE_APPC);
			ls2waitsendnum = torderMapper_R.countCommonOrderProviderByMap(ls_lsdsh_map);
			
			//Map<String,Object> ls_nodeliver_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
			ls_nodeliver_map.put("ecnetno", ecnetno);
			ls_nodeliver_map.put("nopfchannelcode", "true");
			ls_nodeliver_map.put("state", Constant.TORDER_STATE_WFH);
			ls_nodeliver_map.put("ispayfornodeliver", "true");
			ls_nodeliver_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_nodeliver_map.put("platorself", Constant.TORDER_PLATORSELF_PT); //自营商品的发货走确认、签收走店主收货。所以这里显示的待发货只是平台商品订单  add by yhg 20150428
			ls2nodelivernum = torderMapper_R.countCommonOrderProviderByMap(ls_nodeliver_map);

			//Map<String,Object> ls_delivered_map = new HashMap<String,Object>(); //已发货总数 -用于零售订单
			ls_delivered_map.put("ecnetno", ecnetno);
			ls_delivered_map.put("nopfchannelcode", "true");
			ls_delivered_map.put("state", Constant.TORDER_STATE_YFH);
			ls_delivered_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_delivered_map.put("platorself", Constant.TORDER_PLATORSELF_PT); //自营商品的发货走确认、签收走店主收货。所以这里显示的待发货只是平台商品订单  add by yhg 20150428
			ls2deliverednum = torderMapper_R.countCommonOrderProviderByMap(ls_delivered_map);

			//Map<String,Object> ls_signed_map = new HashMap<String,Object>(); //已签收总数 -用于零售订单
			ls_signed_map.put("ecnetno", ecnetno);
			ls_signed_map.put("nopfchannelcode", "true");
			ls_signed_map.put("state", Constant.TORDER_STATE_YQS);
			ls_signed_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls2signednum = torderMapper_R.countCommonOrderProviderByMap(ls_signed_map);

/*			Map<String,Object> ls_aftersale_map = new HashMap<String,Object>(); //售后总数 -用于零售订单
			ls_aftersale_map.put("psam", ordercntinput.getDeviceno()); //零售售后传终端编号pasm
			ls2aftersalenum = returnMapper_R.countAllByMobile(ls_aftersale_map);
			*/
			
			//Map<String,Object> ls_lsyqx_map = new HashMap<String,Object>();   //已取消总数-用于零售订单
			ls_lsyqx_map.put("ecnetno", ecnetno);
/*			ls_lsyqx_map.put("cancelstate", Constant.TORDER_CANCELSTATE_YQX);
			ls_lsyqx_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsyqx_map.put("canceltype", Constant.TORDER_CANCELTYPE_DZQX); //店主取消
*/			
			ls_lsyqx_map.put("lsyqx", true);
			ls2calcelednum = torderMapper_R.countCommonOrderProviderByMap(ls_lsyqx_map);
			
			info.setLs2waitconfirmnum(ls2waitconfirmnum==null?0:ls2waitconfirmnum); //待确认总数-用于零售订单
			info.setLs2waittakenum(ls2waittakenum==null?0:ls2waittakenum); //待自提总数-用于零售订单
			info.setLs2waitsendnum(ls2waitsendnum==null?0:ls2waitsendnum); //待送货总数-用于零售订单
			info.setLs2calcelednum(ls2calcelednum==null?0:ls2calcelednum); //已取消总数-用于零售订单
			info.setLs2nodelivernum(ls2nodelivernum==null?0:ls2nodelivernum);  //待发货总数-用于零售订单
			info.setLs2deliverednum(ls2deliverednum==null?0:ls2deliverednum);  //已发货总数 -用于零售订单
			info.setLs2signednum(ls2signednum==null?0:ls2signednum);  //已发货总数 -用于零售订单
			info.setLs2aftersalenum(ls2aftersalenum==null?0:ls2aftersalenum);  //售后总数 -用于零售订单
			
		}
			
    
         return info;
	}
	
	
	
	
	
	/**
	 * 根据输入返回OrderInfoOutput实体
	 * @param OrderInfoInput
	 * @return  OrderInfoOutput实体
	 * @author: yhg 
	 * @time: 2015年3月12日 下午6:04:55
	 * @todo: TODO
	 */
	public OrderV2InfoOutput getOrderEntryListV2(OrderV2InfoInput orderv2infoinput){
		
		OrderV2InfoOutput info = new OrderV2InfoOutput();
		List<OrderV2Info> orderlist = new ArrayList<OrderV2Info>();
		List<OrderProviderV2Info> orderproviderlist = new ArrayList<OrderProviderV2Info>();
		List<ReturnMoble> returnlist = new ArrayList<ReturnMoble>();
		Integer page = orderv2infoinput.getPage()==null?1:orderv2infoinput.getPage();
		Integer rows = orderv2infoinput.getPageSize()==null?10:orderv2infoinput.getPageSize();
        Integer start = (page - 1) * rows;
        String ecnetno = orderv2infoinput.getEcnetno();
        Integer total = null;
        String queryordertotal = orderv2infoinput.getQueryordertotal();
		
		/**--订单查询类别
		 *
		 *     代码     :备注
		 *  all    :全部订单;   
		 *  pfall  :全部订单-用于批发订单;
		 *  lsall  :全部订单-用于零售订单;
		 *  pfdfk  :待付款-用于批发订单;   
			pfdfh  :待发货-用于批发订单;
			pfyfh  :已发货-用于批发订单;
			pfbfsh :部分收货-用于批发订单;
			pfysh  :已收货-用于批发订单;
			lsdqr  :待确认-用于零售订单;
			lsdzt  :待自提-用于零售订单;
			lsdsh  :待送货-用于零售订单;
			lsdfh  :待发货-用于零售订单;
			lsyfh  :已发货 -用于零售订单;
			lsyqs  :已签收 -用于零售订单;
			lsyqx  :已取消-用于零售订单
			lssh   :售后 -用于零售订单 V2改版暂未用;
			sxxdzsh:需店主送货订单-用于需店主送货  V2改版不用; 
		--**/
        if("all".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> all_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
			all_map.put("start", start);
			all_map.put("rows", rows);
			all_map.put("appallmobile", orderv2infoinput.getMobile());
			all_map.put("appallecnetno", ecnetno);
			all_map.put("showall", true);  //全部显示如下数据:1、在线支付且已支付的；2、在线支付未支付的且30分钟内的；2、货到付款的
			//all_map.put("cancelstate", OrderConstant.TORDER_CANCELSTATE_WQX);
			all_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderlist = torderMapper_R.queryCommonOrderByMapV2(all_map);
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderByMap(all_map);
			}

			info.setOrderlist(orderlist);
		}else if("pfall".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> pfall_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
			pfall_map.put("start", start);
			pfall_map.put("rows", rows);
			pfall_map.put("appallmobile", orderv2infoinput.getMobile());
			pfall_map.put("appallecnetno", ecnetno);
			pfall_map.put("ispfchannelcode", "true");
			pfall_map.put("showall", true);  //全部显示如下数据:1、在线支付且已支付的；2、在线支付未支付的且30分钟内的；2、货到付款的
			pfall_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pfall_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			
			orderlist = torderMapper_R.queryCommonOrderByMapV2(pfall_map);
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderByMap(pfall_map);
			}

			info.setOrderlist(orderlist);
		}else if("lsall".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> lsall_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
			lsall_map.put("start", start);
			lsall_map.put("rows", rows);
			lsall_map.put("ecnetno", ecnetno);
			lsall_map.put("nopfchannelcode", "true");
			//lsall_map.put("ispayfornodeliver", "true"); //根据龙飞最新需求,零售订单全部列表也显示零售的未支付订单
			lsall_map.put("showall", true);  //全部显示如下数据:1、在线支付且已支付的；2、在线支付未支付的且30分钟内的；2、货到付款的
			//lsall_map.put("cancelstate", OrderConstant.TORDER_CANCELSTATE_WQX);
			lsall_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(lsall_map);
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(lsall_map);
			}
			
			info.setOrderproviderlist(orderproviderlist);
		}else if("pfdfk".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> pf_needpay_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
			pf_needpay_map.put("start", start);
			pf_needpay_map.put("rows", rows);
			pf_needpay_map.put("mobile", orderv2infoinput.getMobile());
			pf_needpay_map.put("ispfchannelcode", "true");
			pf_needpay_map.put("state", Constant.TORDER_STATE_WFH);
			pf_needpay_map.put("ispay", Constant.TORDER_ISPAY_DZF);
			pf_needpay_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			pf_needpay_map.put("isrestore", Constant.TORDER_ISRESTORE_WTB);
			pf_needpay_map.put("ecnetno", ecnetno);
			//pf_needpay_map.put("source", Constant.TORDER_SOURCE_APPB);
			
			pf_needpay_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderlist = torderMapper_R.queryCommonOrderByMapV2(pf_needpay_map);
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderByMap(pf_needpay_map);
				info.setTotal(total);
			}
			
			info.setOrderlist(orderlist);
		}else if("pfdfh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> pf_nodeliver_map = new HashMap<String,Object>();  //待发货总数-用于批发订单
			pf_nodeliver_map.put("start", start);
			pf_nodeliver_map.put("rows", rows);
			pf_nodeliver_map.put("ecnetno", ecnetno);
			pf_nodeliver_map.put("ispfchannelcode", "true");
			pf_nodeliver_map.put("state", Constant.TORDER_STATE_WFH);
			pf_nodeliver_map.put("ispayfornodeliver", "true");
			pf_nodeliver_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			
			pf_nodeliver_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(pf_nodeliver_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(pf_nodeliver_map);
			}
			
		}else if("pfyfh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> pf_delivered_map = new HashMap<String,Object>();  //待收货总数-用于批发订单
			pf_delivered_map.put("start", start);
			pf_delivered_map.put("rows", rows);
			pf_delivered_map.put("ecnetno", ecnetno);
			pf_delivered_map.put("ispfchannelcode", "true");
			//原已发货列表变更为待收货列表。待收货包括显示已发货的和部分签收的数据
			pf_delivered_map.put("stateids", new String[]{String.valueOf(Constant.TORDER_STATE_YFH),String.valueOf(Constant.TORDER_STATE_BFQS)});
			pf_delivered_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			
			pf_delivered_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(pf_delivered_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(pf_delivered_map);
			}
			
		}else if("pfbfsh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> pf_partreceive_map = new HashMap<String,Object>();  //部分收货总数-用于批发订单
			pf_partreceive_map.put("start", start);
			pf_partreceive_map.put("rows", rows);
			pf_partreceive_map.put("ecnetno", ecnetno);
			pf_partreceive_map.put("ispfchannelcode", "true");
			pf_partreceive_map.put("state", Constant.TORDER_STATE_BFQS);
			pf_partreceive_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			
			pf_partreceive_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(pf_partreceive_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(pf_partreceive_map);
			}
			
		}else if("pfysh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> pf_received_map = new HashMap<String,Object>();  //已收货总数-用于批发订单
			pf_received_map.put("start", start);
			pf_received_map.put("rows", rows);
			pf_received_map.put("ecnetno", ecnetno);
			pf_received_map.put("ispfchannelcode", "true");
			pf_received_map.put("state", Constant.TORDER_STATE_YQS);
			pf_received_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			
			pf_received_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(pf_received_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(pf_received_map);
			}
			
		}else if("lsdqr".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_lsdqr_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
			ls_lsdqr_map.put("start", start);
			ls_lsdqr_map.put("rows", rows);
			ls_lsdqr_map.put("ecnetno", ecnetno);
			ls_lsdqr_map.put("nopfchannelcode", "true");
			ls_lsdqr_map.put("state", Constant.TORDER_STATE_WFH);
			ls_lsdqr_map.put("ispayfornodeliver", "true");
			ls_lsdqr_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_lsdqr_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsdqr_map.put("source", Constant.TORDER_SOURCE_APPC);
			ls_lsdqr_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_lsdqr_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_lsdqr_map);
			}
			
			
		}else if("lsdzt".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_lsdzt_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
			ls_lsdzt_map.put("start", start);
			ls_lsdzt_map.put("rows", rows);
			ls_lsdzt_map.put("ecnetno", ecnetno);
			ls_lsdzt_map.put("nopfchannelcode", "true");
			ls_lsdzt_map.put("state", Constant.TORDER_STATE_YFH);
			ls_lsdzt_map.put("ispayfornodeliver", "true");
			ls_lsdzt_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_lsdzt_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsdzt_map.put("isdelivertohome", Constant.TORDER_ISDELIVERTOHOME_KDDD); //到店自提
			ls_lsdzt_map.put("source", Constant.TORDER_SOURCE_APPC);
			
			ls_lsdzt_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_lsdzt_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_lsdzt_map);
			}
			
		}else if("lsdsh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_lsdsh_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
			ls_lsdsh_map.put("start", start);
			ls_lsdsh_map.put("rows", rows);
			ls_lsdsh_map.put("ecnetno", ecnetno);
			ls_lsdsh_map.put("nopfchannelcode", "true");
			ls_lsdsh_map.put("state", Constant.TORDER_STATE_YFH);
			ls_lsdsh_map.put("ispayfornodeliver", "true");
			ls_lsdsh_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_lsdsh_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsdsh_map.put("isdelivertohome", Constant.TORDER_ISDELIVERTOHOME_KDDJ); //送货上门
			ls_lsdsh_map.put("source", Constant.TORDER_SOURCE_APPC);
			
			ls_lsdsh_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_lsdsh_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_lsdsh_map);
			}
			
		}else if("lsdfh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_nodeliver_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
			ls_nodeliver_map.put("start", start);
			ls_nodeliver_map.put("rows", rows);
			ls_nodeliver_map.put("ecnetno", ecnetno);
			ls_nodeliver_map.put("nopfchannelcode", "true");
			ls_nodeliver_map.put("state", Constant.TORDER_STATE_WFH);
			ls_nodeliver_map.put("ispayfornodeliver", "true");
			ls_nodeliver_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_nodeliver_map.put("platorself", Constant.TORDER_PLATORSELF_PT); //自营商品的发货走确认、签收走店主收货。所以这里显示的待发货只是平台商品订单  add by yhg 20150428
			
			ls_nodeliver_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_nodeliver_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_nodeliver_map);
			}
			
			
		}else if("lsyfh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_delivered_map = new HashMap<String,Object>(); //已发货总数 -用于零售订单
			ls_delivered_map.put("start", start);
			ls_delivered_map.put("rows", rows);
			ls_delivered_map.put("ecnetno", ecnetno);
			ls_delivered_map.put("nopfchannelcode", "true");
			ls_delivered_map.put("state", Constant.TORDER_STATE_YFH);
			ls_delivered_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			ls_delivered_map.put("platorself", Constant.TORDER_PLATORSELF_PT); //自营商品的发货走确认、签收走店主收货。所以这里显示的待发货只是平台商品订单  add by yhg 20150428
			
			ls_delivered_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_delivered_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_delivered_map);
			}
			
			
		}else if("lsyqs".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_signed_map = new HashMap<String,Object>(); //已签收总数 -用于零售订单
			ls_signed_map.put("start", start);
			ls_signed_map.put("rows", rows);
			ls_signed_map.put("ecnetno", ecnetno);
			ls_signed_map.put("nopfchannelcode", "true");
			ls_signed_map.put("state", Constant.TORDER_STATE_YQS);
			ls_signed_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			
			ls_signed_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_signed_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_signed_map);
			}
			
		}else if("lsyqx".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_lsyqx_map = new HashMap<String,Object>();   //待发货总数-用于零售订单
			ls_lsyqx_map.put("start", start);
			ls_lsyqx_map.put("rows", rows);
			ls_lsyqx_map.put("ecnetno", ecnetno);
/*			ls_lsyqx_map.put("cancelstate", Constant.TORDER_CANCELSTATE_YQX);
			ls_lsyqx_map.put("platorself", Constant.TORDER_PLATORSELF_ZY);
			ls_lsyqx_map.put("canceltype", Constant.TORDER_CANCELTYPE_DZQX); //店主取消
*/			
			ls_lsyqx_map.put("lsyqx", true);
			
			ls_lsyqx_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(ls_lsyqx_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(ls_lsyqx_map);
			}
			
		}else if("lssh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> ls_aftersale_map = new HashMap<String,Object>(); //售后总数 -用于零售订单
			ls_aftersale_map.put("page", start);
			ls_aftersale_map.put("rows", rows);
			ls_aftersale_map.put("psam", orderv2infoinput.getDeviceno()); //零售售后传终端编号pasm
			returnlist = returnMapper_R.findAllByMobile(ls_aftersale_map);
			info.setReturnlist(returnlist);
			
		}else if("sxxdzsh".equals(orderv2infoinput.getQuerytype())){
			Map<String,Object> sx_needbsend_map = new HashMap<String,Object>(); //需店主送货订单总数-用于需店主送货
			sx_needbsend_map.put("start", start);
			sx_needbsend_map.put("rows", rows);
			sx_needbsend_map.put("ecnetno", ecnetno);
			sx_needbsend_map.put("is3hs", new String[]{String.valueOf(Constant.TORDER_IS3H_1H),String.valueOf(Constant.TORDER_IS3H_3H5H)});
			sx_needbsend_map.put("isdelivertohome", Constant.TORDER_ISDELIVERTOHOME_KDDJ);
			sx_needbsend_map.put("state", Constant.TORDER_STATE_YFH);
            //待发货显示已支付的，或者货到付款的
			sx_needbsend_map.put("ispayfornodeliver", "true");
			sx_needbsend_map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			
			sx_needbsend_map.put("searchvalue", orderv2infoinput.getSearchvalue());  //订单模糊搜索字段，可匹配收货人手机号、供应商订单号、商品名称
			
			orderproviderlist = torderMapper_R.queryCommonOrderProviderByMapV2(sx_needbsend_map);
			
			// yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)
			if (!StringUtils.isEmpty(queryordertotal)
					&& "yes".equals(queryordertotal)) {
				total = torderMapper_R.countCommonOrderProviderByMap(sx_needbsend_map);
			}
			
		}
		

		info.setTotal(total);
		info.setOrderproviderlist(orderproviderlist);
        return info;
	}
	

	@Override
	public ObjectOutput orderEntryList(OrderInfoInput orderinfoinput)
			throws LakalaException {
		// TODO Auto-generated method stub
		ObjectOutput data = new ObjectOutput();
		
		/**--订单查询类别
		 *
		 *     代码     :备注
		 *  pfdfk  :待付款-用于批发订单;
			pfdfh  :待发货-用于批发订单;
			pfyfh  :已发货-用于批发订单;
			pfbfsh :部分收货-用于批发订单;
			pfysh  :已收货-用于批发订单;
			lsdfh  :待发货-用于零售订单;
			lsyfh  :已发货 -用于零售订单;
			lsyqs  :已签收 -用于零售订单;
			lssh   :售后 -用于零售订单;
			sxxdzsh:需店主送货订单-用于需店主送货; 
		--**/
		
		if (orderinfoinput == null
				|| StringUtils.isEmpty(orderinfoinput.getQuerytype())
				|| StringUtils.isEmpty(orderinfoinput.getEcnetno())
				|| !Constant.querytypeset.contains(orderinfoinput
						.getQuerytype())
						) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}

		OrderInfoOutput info = new OrderInfoOutput();
		try{
			//info = getOrderEntryList(orderinfoinput);
		}catch(Exception e){
			throw new LakalaException(e.getMessage());
		}
		
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(info);
		data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		return data;
	}
	
	
	
	@Override
	public ObjectOutput orderEntryListV2(OrderV2InfoInput orderv2infoinput)
			throws LakalaException {
		// TODO Auto-generated method stub
		ObjectOutput data = new ObjectOutput();
		
		/**--订单查询类别
		 *
		 *     代码     :备注
		 *  pfdfk  :待付款-用于批发订单;
			pfdfh  :待发货-用于批发订单;
			pfyfh  :已发货-用于批发订单;
			pfbfsh :部分收货-用于批发订单;
			pfysh  :已收货-用于批发订单;
			lsdfh  :待发货-用于零售订单;
			lsyfh  :已发货 -用于零售订单;
			lsyqs  :已签收 -用于零售订单;
			lssh   :售后 -用于零售订单;
			sxxdzsh:需店主送货订单-用于需店主送货; 
		--**/
		
		if (orderv2infoinput == null
				|| StringUtils.isEmpty(orderv2infoinput.getQuerytype())
				|| StringUtils.isEmpty(orderv2infoinput.getEcnetno())
				|| !Constant.querytypeset.contains(orderv2infoinput
						.getQuerytype())
				|| (StringUtils.isEmpty(orderv2infoinput.getMobile()) && ("all"
						.equals(orderv2infoinput.getQuerytype())
						|| "pfall".equals(orderv2infoinput.getQuerytype())
						|| "pfdfk".equals(orderv2infoinput.getQuerytype())))) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}

		OrderV2InfoOutput info = new OrderV2InfoOutput();
		try{
			info = getOrderEntryListV2(orderv2infoinput);
		}catch(Exception e){
			throw new LakalaException(e.getMessage());
		}
		
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(info);
		data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		return data;
	}
	
	
	
	/**
	 * 订单取消
	 * @param orderdetailinput
	 * @return
	 * @author: yhg 
	 * @time: 2015年4月27日 上午9:37:02
	 * @todo: TODO
	 */
	@Override
	public ObjectOutput updatecancelorder(OrderDetailInput orderdetailinput)
			throws LakalaException, IOException {
		// TODO Auto-generated method stub
		ObjectOutput data = new ObjectOutput();
		if (orderdetailinput == null
				|| StringUtils.isEmpty(orderdetailinput.getTorderproviderid())) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}
		
		boolean optsuccess = false;  //是否操作成功,默认为false

		try{
			
			/***
			 * 步骤说明  ---------------------------------------
			 * 1、校验是否整单取消
			 * 2、后台查询需取消的订单
			 * 3、发起售后
			 * 4、释放库存
			 * 5、释放优惠券
			 * 6、更改订单状态
			 * 7、给店主和顾客短信提示
			 * 8、调接口给APP2C推送消息
			 * ---------------------------------------------
			 */
			
			Torderprovider torderprovider = null;
			torderprovider = torderproviderMapper_R.selectByPrimaryKey(orderdetailinput.getTorderproviderid());
			if(torderprovider!=null&&Constant.TORDER_CANCELSTATE_WQX==torderprovider.getCancelstate()){
				Map isalldomap = new HashMap();
				isalldomap.put("torderproviderid", orderdetailinput.getTorderproviderid());
				isalldomap.put("cancelstate",Constant.TORDER_CANCELSTATE_WQX);
				isalldomap.put("tallorderid",torderprovider.getTallorderid());
				isalldomap.put("tallorderidflag","tallorderid");
				isalldomap.put("flag", "torderprovider");
				int ret = 0;
				
				//1、校验是否整单取消
				isalldomap.put("state", "99"); //用于整单校验,APP2B只有待发货的自营商品商品才需要取消，校验整单商品是否都是待发货的
				ret = torderitemsMapper_R.checkOrderIsDoAll(isalldomap);
				if(ret != -99){
					
					boolean isalldo = false;
					isalldo = (ret==0?true:false);
					//2、后台查询需取消的订单
					List<Torderitems> torderitemsList = new ArrayList<Torderitems>();
					Map map = new HashMap();
					map.put("torderproviderid", orderdetailinput.getTorderproviderid());
					map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
					torderitemsList = torderitemsMapper_R.selectOrderItemsList(map);//查询需取消的订单
					
					if(torderitemsList.size()>0){
						//3、发起售后
						returnService.updateReturnTKDataByOrder(torderprovider, torderitemsList,isalldo);
						
						String cancelitemsids = "";
						//4、释放库存
						for (Torderitems oi : torderitemsList) {
							updateRestoreByItem(oi);
							//去取消的商品订单主键串,例如32232,433221
							cancelitemsids = cancelitemsids + "," + oi.getTorderitemsid();
						}
						
						cancelitemsids = cancelitemsids.substring(1);
						
						//5、释放优惠券：整单取消，若存在优惠券，则需释放优惠券 add by yhg 20150514
						updateReleaseOrderCoupon(torderprovider.getTallorderid(), cancelitemsids,isalldo);
						
						//6、更改订单状态
						updateOrderStateWhenCancel(torderprovider,cancelitemsids,isalldo);
						
						//7、给店主和顾客短信提示
						insertLogiAndSmsWhenConfirm(torderitemsList,2,false);
						
						//8、调接口给APP2C推送消息
						Msg2App2CUtil.syncmsg2app2c(Msg2App2CUtil.OPTTYPE_URL_RETURNORDER_CUSTOMER,"orderProviderID",orderdetailinput.getTorderproviderid());
						
						optsuccess = true;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new LakalaException(e.getMessage());
		}
		
		if(optsuccess){
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
			return data;
		}else{
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_ORDERCANCE_000002);
			return data;
		}
		
	}
	
	
	/**
     * 订单整单取消，或者最后一个取消时，释放优惠券
     * @param order
     * @param nowcancelitemids
     * @param isalldo  是否整单取消
     * @return
     * @author: yhg 
     * @time: 2015年5月18日 下午5:01:31
     * @todo: TODO
     */
	public int updateReleaseOrderCoupon(Integer tallorderid, String nowcancelitemids,boolean isalldo)  throws LakalaException {

		int temp = 1;
		try {
			
				 int couponcnt = 0;
                 couponcnt = tordercouponsMapper_R.countOrderCouponsByTallorderid(tallorderid);
                 if(couponcnt>0&&isalldo){
                 	tordercouponsMapper_W.updateTorderCouponsFree(tallorderid);
                 }else if(couponcnt>0&&!isalldo){
                	 //若不是整单取消，判断是不是最后一批或一个需取消的订单，如果是，也返还券
                 	int islastcnt = 0;
                 	Map lastmap = new HashMap();
                 	lastmap.put("torderitemsids", nowcancelitemids);
                 	lastmap.put("tallorderid", tallorderid);
                 	islastcnt = torderitemsMapper_R.selectIsLastOrderitemsWhenFavCheck(lastmap);
                 	if(islastcnt == 0){
                 		tordercouponsMapper_W.updateTorderCouponsFree(tallorderid);
                 	}
                 }
		} catch (Exception e) {
			temp = -1;
			throw new LakalaException("订单取消释放库存异常！");
		}

		return temp;
	}
	
	
	
	
	/**
     * 订单取消释放库存异常！
     * @param order
     * @param oi
     * @return
     * @author: yhg 
	 * @throws Exception 
	 * @time: 2015年2月2日 下午2:33:03
     * @todo: TODO
     */
	public int updateRestoreByItem(Torderitems oi) throws Exception {

		int temp = 1;
		if (oi != null) {

				// 暂存到仓的商品订单取消不释放库存
			    if (!(Constant.TEMPSTOREGOODSFLAG_ZCRC == oi.getTempstoregoodsflag()
						&& (String.valueOf(
								Constant.TORDER_CHANNELCODE_PFJH).equals(
								oi.getChannelcode()) || String.valueOf(
										Constant.TORDER_CHANNELCODE_APP2B).equals(
								oi.getChannelcode()) || String.valueOf(
										Constant.TORDER_CHANNELCODE_APP2B_LAST).equals(
								oi.getChannelcode())) && Constant.TORDER_ISPAY_YZF == oi
							.getIspay())) {
					// 订单取消，释放库存
					Map storeMap = new HashMap();
					storeMap.put("giftstate", oi.getGiftstate());
					storeMap.put("goodsskuo2oid", oi.getGoodsskuo2oid());
					storeMap.put("goodsskuid", oi.getGoodsskuid());
					storeMap.put("goodscount", oi.getGoodscount());
					storeMap.put("torderitemsid", oi.getTorderitemsid());
					storeMap.put("tempstoregoodsflag",
							oi.getTempstoregoodsflag());
					storeMap.put("tallorderid", oi.getTallorderid());

					torderitemsMapper_W
							.updateBatchRestoreByCancelOrder(storeMap);
					
					//操作mongo库存
					Tgoodskuinfo tgoodskuinfo = tgoodskuinfoMapper_R.selectByPrimaryKey(oi.getGoodsskuid());
					TgoodskuinfoWithBLOBs goodskuinfo = new TgoodskuinfoWithBLOBs();
                    goodskuinfo.setTgoodskuinfoid(oi.getGoodsskuid());
                    goodskuinfo.setTgoodinfoid(oi.getGoodsid());
                    goodskuinfo.setAddstock(new BigDecimal(oi.getGoodscount()==null?0:oi.getGoodscount()));
                   
                    StoreChangeInPut input = new StoreChangeInPut();
                    if(null!=tgoodskuinfo){
                    	input.setGoodsId(tgoodskuinfo.getTgoodinfoid());
        				input.setSkuId(tgoodskuinfo.getTgoodskuinfoid().longValue());
        				input.setNum(oi.getGoodscount()==null?0:oi.getGoodscount());
        				input.setFlag(1);//加库存
        				
        				input.setUserName(String.valueOf(oi.getTorderitemsid()) + ",APPB取消APPC端自营订单");
        				input.setIp("");
        				
        				ObjectMapper om = new ObjectMapper();
        				String instr = null;
        				try {
        					instr = om.writeValueAsString(input);
        				} catch (JsonProcessingException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        				logger.info("调用mongo开始： =========url :  " +  cfg.getMongoTestBaseUrl()+ BussConst.MOD_STOCK + " ; 入参 : = " +instr );
        				String res = HttpUtil.httpPostConnetion(cfg.getMongoTestBaseUrl()
        						+ BussConst.MOD_STOCK, instr);
                    }
                  }
			}
		
		return temp;
	}
	
	
	/**
	 * 订单取消时更新订单状态
	 * @param torderprovider
	 * @param cancelitemsids 操作的商品订单主键串。传过来的值必须前后无逗号的。因为本方法内针对该字符不作其他处理。例如232,2342
	 * @param isalldo  是否整单取消
	 * @return
	 * @throws LakalaException
	 * @author: yhg 
	 * @time: 2015年5月15日 下午3:22:59
	 * @todo: TODO
	 */
	public int updateOrderStateWhenCancel(Torderprovider torderprovider,String cancelitemsids,boolean isalldo) throws LakalaException {
		
		int temp = 1;
		
			//更新torderitems表canselstate=136
			torderitemsMapper_W.updateTorderItemsCancelByTorderItemsIdGrops(cancelitemsids);
			if(isalldo){
				//整单取消，直接更改订单状态,不必向上回溯检查
				
				//更新torderprovider表cancelstate =136
				torderproviderMapper_W.updateTorderProviderCancelByTorderProviderId(torderprovider.getTorderproviderid());
				//更新全部torder 表订单状态
				torderMapper_W.updateTorderCancelByTordeId(torderprovider.getOrderid()); 
			}else{
				int opret = 0;
				Map opcancelmap = new HashMap();
				opcancelmap.put("torderproviderid", torderprovider.getTorderproviderid());
				opcancelmap.put("cancelitemsids", cancelitemsids);
				opret = torderitemsMapper_R.checkOrderIsAllCancel(opcancelmap);
				if(opret == 0){
					//更新torderprovider表cancelstate =136
					torderproviderMapper_W.updateTorderProviderCancelByTorderProviderId(torderprovider.getTorderproviderid());
					
					int orderret = 0;
					Map cancelmap = new HashMap();
					cancelmap.put("torderid", torderprovider.getOrderid());
					cancelmap.put("cancelitemsids", cancelitemsids);
					orderret = torderitemsMapper_R.checkOrderIsAllCancel(cancelmap);
					if(orderret == 0){
						//更新全部torder 表订单状态
						torderMapper_W.updateTorderCancelByTordeId(torderprovider.getOrderid()); 
					}
				}
			}
		
		return temp;
	}
	
	
	@Override
	public ObjectOutput updateconfirmorder(OrderDetailInput orderdetailinput)
			throws LakalaException, IOException {
		// TODO Auto-generated method stub
		ObjectOutput data = new ObjectOutput();
		
		if (orderdetailinput == null
				|| StringUtils.isEmpty(orderdetailinput.getTorderproviderid())) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return data;
		}
		
		Torderprovider op = null;
		op = torderproviderMapper_R.selectByPrimaryKey(orderdetailinput.getTorderproviderid());
		if(op!=null&&Constant.TORDER_CANCELSTATE_WQX==op.getCancelstate()&&Constant.TORDER_STATE_WFH==op.getState()){
			
			//确认时更新torderitems表
			Map itemmap = new HashMap();
			List<TShopService> tshopslist = new ArrayList<TShopService>(); 
			itemmap.put("state", Constant.TORDER_STATE_WFH);
			itemmap.put("torderproviderid", orderdetailinput.getTorderproviderid());
			itemmap.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
			itemmap.put("iddescsoft", "true");
			itemmap.put("onlydisone", "true");
			tshopslist = tshopservicemapper_R.queryShopServiceByOrdereMap(itemmap);
			String deliverymanname = "";  //送货人姓名
			String deliverymanphone = "";  //送货人手机号
			if(!tshopslist.isEmpty()){
				deliverymanname = tshopslist.get(0).getName();
				deliverymanphone = tshopslist.get(0).getPhone();
			}
			
			//确认时更新torderprovider表
			Map opmap = new HashMap();
			opmap.put("state", Constant.TORDER_STATE_YFH);
			opmap.put("torderproviderid", orderdetailinput.getTorderproviderid());
			opmap.put("deliverymanname", deliverymanname);
			opmap.put("deliverymanphone", deliverymanphone);
			torderproviderMapper_W.confirmOrderByTorderProviderId(opmap);
			String torderid = op.getOrderid();
			int ret  = 0 ;
			Map checkmap = new HashMap();
			checkmap.put("torderid", torderid);
			checkmap.put("torderproviderid", orderdetailinput.getTorderproviderid());
			ret = torderitemsMapper_R.checkOrderIsAllComfirm(checkmap);  //查询torder下的订单是否都已确认,已全部确认-Torder表改为已发货;未全部确认-Torder表改为部分发货
			Map confirmmap = new HashMap();
			confirmmap.put("torderid", torderid);
			confirmmap.put("deliverymanname", deliverymanname);
			confirmmap.put("deliverymanphone", deliverymanphone);
			if(ret == 0){
				confirmmap.put("state", Constant.TORDER_STATE_YFH);
			}else{
				confirmmap.put("state", Constant.TORDER_STATE_BFFH);
			}
			torderMapper_W.confirmOrderByTorderId(confirmmap);
			
			
			
			//订单确认时给APP2C推送消息
			try {
				Msg2App2CUtil.syncmsg2app2c(Msg2App2CUtil.OPTTYPE_URL_DELIVERED,"orderProviderID",orderdetailinput.getTorderproviderid());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
			return data;
		}else{
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg(ReturnMsg.MSG_ORDERCANCE_000002);
			return data;
		}
		
		
	}
	
	
	/**
	 * 自营订单确认时短息提醒
	 * @param torderproviderid
	 * @throws IOException
	 * @author: yhg 
	 * @time: 2015年7月23日 下午6:18:54
	 * @todo: TODO
	 */
	@Override
	public void msgwhenconfim(String torderproviderid) throws IOException{
		List<Torderitems> oilist = new ArrayList<Torderitems>();
		Map oimap = new HashMap();
		oimap.put("torderproviderid", torderproviderid);
		oimap.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
		oilist = torderitemsMapper_R.selectOrderItemsList(oimap);
		
		//订单确认时发送短信和物流信息
		insertLogiAndSmsWhenConfirm(oilist,1,true);
	}

	
	
	
	/**
	 * 根据商品订单IDtorderitemsid从tshopslist取出相应的收货人信息集合，并拼成json串
	 * @param torderitemsid
	 * @param tshopslist
	 * @return
	 * @author: yhg 
	 * @time: 2015年7月20日 下午6:13:01
	 * @todo: TODO
	 */
	public String genshopservicelist(Integer torderitemsid,List<TShopService> tshopslist){
		List<Map> singlelist = new ArrayList<Map>();
		 for(TShopService ts : tshopslist){
			 
			 //每个tordereitems只取一次，根据永宾需求。在收货人有多个的情况下，每个商品订单只取一个在C端展示
			 if(ts.getTorderitemsid().equals(torderitemsid)&&singlelist.isEmpty()){
				 Map map = new HashMap();
				 map.put("name", ts.getName()==null?"null":ts.getName());
				 map.put("phone", ts.getPhone()==null?"null":ts.getPhone());
				 singlelist.add(map);
			 }
		 }
		 
		 return String.valueOf(JSON.toJSON(singlelist));
	}
	
	
	
	
	
    /**
     * 
     * 自营商品 下单发货，并且发送物流信息
     * @param oilist  商品订单list
     * @param smstype 短信类型  1-确认短信； 2-取消短信
     * @param iswl    是否生成物流发货数据  true-生成；false-不生成
     * @author: yhg 
     * @throws IOException 
     * @time: 2015年4月28日 下午3:51:41
     * @todo: TODO
     */
	public void insertLogiAndSmsWhenConfirm(List<Torderitems> oilist,int smstype,Boolean iswl) throws IOException{
		
		if(oilist != null){
			Map<Integer,String> smsgoodMap = new HashMap();  //自提码和商品对应的Map，用于短信提醒
			Map<Integer,BigDecimal> smspriceMap = new HashMap();  //自提码和货款对应的Map，用于短信提醒
			Map<Integer,String> smsorderproviderMap = new HashMap();  //自提码和供应商级订单号对应的Map，用于短信提醒
			Map<Integer,String> smsaddrMap = new HashMap();  //自提码和发货地址对应的Map，用于短信提醒
			Map<Integer,Integer> isdeliveMap = new HashMap();  //快递方式(送货上门/到店自提)对应的Map，用于短信提醒
			Set<Integer> smsset = new HashSet<Integer>();   //自提码set<自提码>
			Torder torder = null;
			
			
			for (Torderitems torderitems : oilist) {
				
				Integer itmesincecode = torderitems.getSincecode();
				if(!StringUtils.isEmpty(itmesincecode)){
					smsset.add(itmesincecode);
					
					if (!isdeliveMap.containsKey(itmesincecode)
							||StringUtils.isEmpty(isdeliveMap
									.get(itmesincecode))) {
						// 若不存在该自提码，则直接增加
						isdeliveMap.put(itmesincecode,
								torderitems.getIsdelivertohome());
					} 
					
					if (!smsorderproviderMap.containsKey(itmesincecode)
							||StringUtils.isEmpty(smsorderproviderMap
									.get(itmesincecode))) {
						// 若不存在该自提码，则直接增加
						smsorderproviderMap.put(itmesincecode,
								torderitems.getOrderproviderid());
					} 
					
					if (!smsaddrMap.containsKey(itmesincecode)
							||StringUtils.isEmpty(smsaddrMap
									.get(itmesincecode))) {
						// 若不存在该自提码，则直接增加
						smsaddrMap.put(itmesincecode,
								torderitems.getAddressfull());
					} 
					
					
					// 将3小时订单,并且货到付款的订单，商品名称存入smsgoodMap,存入格式为
					// map.put(自提码,'商品1、商品2')
					if (smsgoodMap.containsKey(itmesincecode)
							&& !StringUtils.isEmpty(smsgoodMap
									.get(itmesincecode))) {
						String newgoodsname = "";
						newgoodsname = smsgoodMap.get(itmesincecode)
								.toString().concat(",")
								.concat(torderitems.getGoodsname());
						smsgoodMap.put(itmesincecode, newgoodsname);
					} else {
						// 若不存在该自提码或商品名称为空值，则直接增加
						smsgoodMap.put(itmesincecode,
								torderitems.getGoodsname());
					}

					// 将3小时订单,并且货到付款的订单，价格总和存入smspriceMap,存入格式为
					// map.put(自提码,'商品1最终售价+商品2最终售价')
					if (smspriceMap.containsKey(itmesincecode)
							&& !StringUtils.isEmpty(smspriceMap
									.get(itmesincecode))) {
						smspriceMap.put(
								itmesincecode,
								torderitems.getGoodsfinalprice().add(
										smspriceMap.get(itmesincecode)));
					} else {
						// 若不存在该自提码，则直接增加
						smspriceMap.put(itmesincecode,
								torderitems.getGoodsfinalprice());
					}
					
				}
				
			}
			
			
			
			if(!smsset.isEmpty()){
				if(iswl){
					for (Integer qhm : smsset) {
						// 查询不同的取货码。用于插入物流表，3H商品取货码
						insertLogisticsBySinceCode(qhm);
						 logger.info("----------------------------APP2B 订单确认发货和物流生成成功----------------------------------------");
					
					}
				}
				
				if(smstype == 1){
					
					Map tordermap = new HashMap();
					tordermap.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
					tordermap.put("torderid", oilist.get(0).getOrderid());
					torder = torderMapper_R.selectOrderListByMap(tordermap).get(0);
					
					// 3h订单下单及发货，并短信提醒
			        SendMessageUtil.sendMessageBy3hOrderDeliver(torder,
					smsset, smsgoodMap, smspriceMap,
					smsorderproviderMap, smsaddrMap,
					torder.getPaychanel());
			       logger.info("----------------------------APP2B 订单确认短信发送成功----------------------------------------");
			       
				}else if(smstype == 2){
					String custelno = oilist.get(0).getCustelno();
					Integer paychanel = oilist.get(0).getPaychanel();
					String providername =  oilist.get(0).getProvidername();
					SendMessageUtil.sendMsgWhen2BCancel(paychanel,custelno,providername,smsset,smsgoodMap,smspriceMap,isdeliveMap);
				}
				
				
			}
		}
	}
	
	
	/**
	 * 根据自提码生成物流数据
	 * @param code
	 * @author: yhg 
	 * @time: 2015年4月28日 下午2:12:33
	 * @todo: TODO
	 */
	@Override
	public void insertLogisticsBySinceCode(Integer code) {
        
		Map map = new HashMap();
		map.put("sincecode", code);
		map.put("cancelstate", Constant.TORDER_CANCELSTATE_WQX);
		List<Torderitems> itemsList = torderitemsMapper_R.selectOrderItemsList(map);

		if (itemsList != null) {
			String orderproviderid = itemsList.get(0).getOrderproviderid();
			Torderprovider op = torderproviderMapper_R.selectByPrimaryKey(orderproviderid);
			
			if (op != null) {
				Tlogisticsinfo logis = new Tlogisticsinfo();
				logis.setLogisticscode(String.valueOf(code));// 物流单号
				logis.setProviderid(op.getProviderid());// 供应商ID
				logis.setState(102);// 发货状态
				logis.setProvidername(op.getProvidername());// 供应商名称
				logis.setCustelno(op.getCustelno());// 收货人电话
				logis.setCusname(op.getCusname());// 收货人姓名
				logis.setIsweekenddeliver(op.getIsweekenddeliver());// 是否周末派送
				logis.setIsdelivertohome(op.getIsdelivertohome());// 订单配送方式
																	// 86:快递到店;87:快递到家
				logis.setAddressprovincename(op.getAddressprovincename());// 省
				logis.setAddressprovince(op.getAddressprovince());// 发货地址省编码
				logis.setAddresscityname(op.getAddresscityname());// 市
				logis.setAddresscity(op.getAddresscity());// 发货地址市编码
				logis.setAddressareaname(op.getAddressareaname());// 区
				logis.setAddressarea(op.getAddressarea());// 区代码
				logis.setAddressfull(op.getAddressfull());// 详细地址
				logis.setCode(op.getCode());// 邮编
				//根据自提码直接生成物流信息的都是拉卡拉自配送的，物流公司直接取拉卡拉自配送ID 128  add by yhg 20150326
				logis.setTlogisticcorpid(Constant.LOGIST_CORP_LAKALAZPS);  //物流公司主键

				tlogisticsinfoMapper_W.insertSelective(logis);

				int logisticsid = logis.getLogisticsid();// 返回的物流表主键
				Tlogisticsinfoitem loi;
				List<Tlogisticsinfoitem> itlist = new ArrayList<Tlogisticsinfoitem>();
				for (Torderitems it : itemsList) {
					loi = new Tlogisticsinfoitem();

					loi.setLogisticsid(logisticsid);// 物流单主键
					loi.setTorderid(it.getOrderid());// 订单主表号
					loi.setTorderproviderid(it.getOrderproviderid());
					loi.setTorderitemsid(it.getTorderitemsid());
					loi.setGoodsnum(Double.parseDouble(it.getGoodscount()
							.toString()));// 发货数量
					loi.setGoodsid(it.getGoodsid());// 商品的ID信息
					loi.setGoodsskuid(it.getGoodsskuid());
					loi.setGoodsname(it.getGoodsname());
					loi.setGoodsinprovidercode(it.getGoodsinprovidercode());// 商品外部编号
					loi.setProviderid(it.getProviderid());
					loi.setProvidername(it.getProvidername());
					loi.setGoodsisownorsupport(it.getGoodsisownorsupport());// 244订单商品性质
																			// (246:自营;247:平台)
					loi.setGoodsfavorulemoney(it.getGoodsfavorulemoney());// 该商品活动优惠额
					loi.setGoodsdeductionpoints(it.getGoodsdeductionpoints());// 商品扣点（结算价来算销售价）
					loi.setGoodspayoff(it.getGoodspayoff());// 商品结算价
					loi.setGoodssaleprice(it.getGoodssaleprice());// 销售价
					loi.setGoodsfinalprice(it.getGoodsfinalprice());// 最终售价
					loi.setGoodsprofits(it.getGoodsprofits());// 毛利润
					loi.setGoodsretainedprofits(it.getGoodsretainedprofits());// 净利润
					loi.setStoreprofits(it.getStoreprofits());// 销售点返利（最终售价要高于结算价,否则按单返利）
					loi.setDeductionbasepoints(it.getDeductionbasepoints());// 毛利润的分润基数
					loi.setCompanydiscount(it.getCompanydiscount());// 公司分润比例（分润扣点）
					loi.setCompanyprofit(it.getCompanyprofit());// 公司分润金额
					loi.setStorediscount(it.getStorediscount());// 销售点分润比例（分润扣点）
					loi.setStoreprofit(it.getStoreprofit());// 销售点分润金额
					loi.setGoodslogisticstate(it.getGoodslogisticstate());// 商品运输状态:0-未导出，1-已导出，2-已发货
					loi.setChannelcode(it.getChannelcode());// 来自频道
					loi.setVirtualclassificationid(it
							.getVirtualclassificationid());// 末级虚分类ID
					loi.setGoodsclassificationid(it.getGoodsclassificationid());// 商品实际分类
					loi.setState(it.getState());// 单个商品的完成状态
												// 99:未发货;100:发货中;102:已发货;104:已签收;216:已拒收;217:物流异常
					loi.setOrdertime(it.getOrdertime());// 下单时间
					loi.setFav(it.getFav());// 营销活动{[id:XXX,type:1]|[id:XXX,type:3]}
											// 其中，type：1.促销、3.优惠券、4.活动
					loi.setLockstate(it.getLockstate());// 订单锁定状态
														// 133:未锁定;134:已锁定
					loi.setLastmodifytime(it.getLastmodifytime());// 最后修改时间
					loi.setSettlestate(it.getSettlestate());// 结算状态，78-未结算
															// 79-已结算
					itlist.add(loi);
                    
					Map logismap = new HashMap();
					logismap.put("logisid", logisticsid);
					logismap.put("itemsid", it.getTorderitemsid());
					torderitemsMapper_W.updateTorderitemsWithLogisID(logismap);
				}
				tlogisticsinfoitemMapper_W.insertBatch(itlist);

			}

		}

	}
	
	/**
	 * 二次支付时修改订单表支付方式
	 */
	public void updatePayChanelFor2Pay(ToPayInput tpi){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("tAllOrderId", tpi.getTallorderid());
		map.put("paychanel", tpi.getPaychanel());
		//tallorder
		this.torderMapper_W.updateTallorderid(map);
		//torder
		this.torderMapper_W.updateTokenOfAllorderid(map);
		//torderitems
		this.torderitemsMapper_W.updateTorderitemsPayChanel(map);
		//torderprovider
		this.torderproviderMapper_W.updateTorderproviderPayChanel(map);
	}

	
}
