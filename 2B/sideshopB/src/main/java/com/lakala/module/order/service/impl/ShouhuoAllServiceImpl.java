package com.lakala.module.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lakala.base.model.Torderitems;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.service.GoodsService;
import com.lakala.module.goods.vo.TgoodsSkuInfoInput;
import com.lakala.module.order.service.QuhuoCommonService;
import com.lakala.module.order.service.ShouhuoAllService;
import com.lakala.module.order.vo.ShouhuoAllInput;
import com.lakala.util.Constants;
import com.lakala.util.ReturnMsg;
/**
 * 全部收货接口实现
 * @author ls
 *
 */
@Service
public class ShouhuoAllServiceImpl implements ShouhuoAllService {
	@Autowired
	private com.lakala.mapper.w.order.ShouhuoAllMapper shouhuoAllMapper_w;
	@Autowired
	private com.lakala.mapper.r.order.ShouhuoAllMapper shouhuoAllMapper_r;
	@Autowired
	private QuhuoCommonService quhuoCommonService;
	@Autowired
	private GoodsService goodsService;
	@Override
	public ObjectOutput<String> updateshall(ShouhuoAllInput sai, HttpServletRequest req) {
		ObjectOutput<String> data = new ObjectOutput<String>();
		if(sai == null || sai.getOrderproviderid() == null || sai.getOrderproviderid().equals("")){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		//--orderprovider表state=104;orderitems表不是104的置为104,logisticitem表同上;tlogisticsinfo表state=104以及签收时间
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderproviderid", sai.getOrderproviderid());
		map.put("state", Constants.TORDER_STATE_YQS);
		//orderprovider表state=104
		shouhuoAllMapper_w.updateproviderstate(map);
		//查询orderid
		String orderid = shouhuoAllMapper_r.selectorderid(map);
		int orderstate = quhuoCommonService.getOrderState(orderid);
		if(orderstate == Constants.TORDER_STATE_YQS){
			//order表state=104
			quhuoCommonService.updateOrderState(orderid, orderstate,-1);
		}
		//查询订单子表(主要是用于更新物流主表tlogisticinfo表state和签收时间)
		map.put("cancelstate", Constants.TORDER_CANCELSTATE_WQX);
		List<Torderitems> itemlist = shouhuoAllMapper_r.selectorderitems(map);
		// 商品入库集合
		List<TgoodsSkuInfoInput> putawayList = new ArrayList<TgoodsSkuInfoInput>(); 
		//for循环更新子订单state=104===(也可以直接更新order是指定id的子订单state)
		if(itemlist != null && itemlist.size() > 0){
			for(Torderitems item : itemlist){
				Map<String,Object> itemmap = new HashMap<String,Object>();
				itemmap.put("torderitemsid", item.getTorderitemsid());
				itemmap.put("itemid", item.getTorderitemsid());
				itemmap.put("actualnum", item.getGoodscount());
				itemmap.put("state", Constants.TORDER_STATE_YQS);
				shouhuoAllMapper_w.updateitemstate(itemmap);
				shouhuoAllMapper_w.updatelogisticitemstate(itemmap);
				// 店主收货商品入库
				// 本次签收数量=  商品总数-实签数量
				itemmap.put("actualnum", item.getGoodscount()-item.getActualnum());
				itemmap.put("deviceNo", sai.getPsam());
				TgoodsSkuInfoInput putawayData =quhuoCommonService.putaway(itemmap);
				if(putawayData!=null){
					putawayList.add(putawayData);
				}
			}
			Set<Integer> set = new HashSet<Integer>();
			for(Torderitems item : itemlist){
				set.add(item.getLogisticsid());
			}
			List<Torderitems> tlist = null;
			Map<String,Object> logidmap = new HashMap<String,Object>();
			logidmap.put("logisticsids", set);
			logidmap.put("state", Constants.TORDER_STATE_YQS);
			if(set.size() > 0){
				tlist = shouhuoAllMapper_r.selectlogids(logidmap);
			}
			if(tlist != null && tlist.size() > 0){
				for(Torderitems item : tlist){
					Map<String,Object> logmap = new HashMap<String,Object>();
					logmap.put("logisticsid", item.getLogisticsid());
					logmap.put("state", Constants.TORDER_STATE_YQS);
					shouhuoAllMapper_w.updatelogisticinfostate(logmap);
				}
			}
		}
		
		// 商品入库
		if(putawayList.size()>0){
			try {
//				goodsService.autoOnsale(putawayList, req);
				//签收成功，自动新增商品，并上架 zhiziwei
				for (TgoodsSkuInfoInput tsi : putawayList) {
					// 自动新增商品
					List<Integer> ids = goodsService.autoAddGoods(tsi, req);
					
					// 自动上架商品
					goodsService.autoOnsale(ids);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		return data;
	}
	
}
