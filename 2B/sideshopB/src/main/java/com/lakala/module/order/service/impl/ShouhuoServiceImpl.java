package com.lakala.module.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.exception.LakalaException;
import com.lakala.model.ShouhuoOutputSql;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.service.GoodsService;
import com.lakala.module.goods.vo.TgoodsSkuInfoInput;
import com.lakala.module.order.service.QuhuoCommonService;
import com.lakala.module.order.service.ShouhuoService;
import com.lakala.module.order.vo.ShouhuoInput;
import com.lakala.module.order.vo.ShouhuoInputParam;
import com.lakala.module.order.vo.ShouhuoOutput;
import com.lakala.module.order.vo.ShouhuoOutput_Provider;
import com.lakala.module.order.vo.ShouhuoOutput_Provider_Goods;
import com.lakala.util.Constants;
import com.lakala.util.NumberUtils;
import com.lakala.util.ReturnMsg;

/**
 * 店主收货服务接口实现类
 * 
 * @author ls
 *
 */
@Service
public class ShouhuoServiceImpl implements ShouhuoService {
	@Autowired
	private com.lakala.mapper.r.order.ShouhuoMapper shouhuoMapper_r;
	@Autowired
	private com.lakala.mapper.w.order.ShouhuoMapper shouhuoMapper_w;
	@Autowired
	private QuhuoCommonService quhuoCommonService;
	@Autowired
	private GoodsService goodsService;

	private BigDecimal ZERO = BigDecimal.valueOf(0);

	@Override
	public ObjectOutput<ShouhuoOutput> getShouhuoInfo(ShouhuoInput si) {
		ObjectOutput<ShouhuoOutput> data = new ObjectOutput<ShouhuoOutput>();
		if (si == null || si.getLogno() == null || si.getLogno().equals("")) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 通过运单号和手机号查询收货信息(原始信息)
		List<ShouhuoOutputSql> soslisto = shouhuoMapper_r.getShouhuoInfo(si);
		if (soslisto == null || soslisto.size() == 0) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			data.set_ReturnMsg(Constants.MSGNULL);
			return data;
		}
		List<ShouhuoOutputSql> soslist = new ArrayList<ShouhuoOutputSql>();
		for (ShouhuoOutputSql ss : soslisto) {
			if (!ss.getLstate().equals(Constants.TORDER_STATE_YQS)) {
				soslist.add(ss);
			}
		}
		// 处理数据
		if (soslist != null && soslist.size() > 0) {
			return handlenosignlist(soslist);
		}
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg(Constants.MSGSIGNED);
		return data;
	}
	@Override
	public ObjectOutput<ShouhuoOutput> getConfirmInfo(ShouhuoInput si,List<Integer> logidlist) {
		ObjectOutput<ShouhuoOutput> data = new ObjectOutput<ShouhuoOutput>();
		if (si == null || si.getLogno() == null || si.getLogno().equals("")) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 通过运单号和手机号查询收货信息(原始信息)
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("si", si);map.put("logids", logidlist);
		List<ShouhuoOutputSql> soslist = shouhuoMapper_r.getConfirmInfo(map);
		if (soslist == null || soslist.size() == 0) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			data.set_ReturnMsg(Constants.MSGNULL);
			return data;
		}
		// 处理数据
		if (soslist != null && soslist.size() > 0) {
			return handlenosignlist(soslist);
		}
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg(Constants.MSGSIGNED);
		return data;
	}
	public ObjectOutput<ShouhuoOutput> handlenosignlist(List<ShouhuoOutputSql> soslist){
		ObjectOutput<ShouhuoOutput> data = new ObjectOutput<ShouhuoOutput>();
		Set<String> set = new HashSet<>();
		for (ShouhuoOutputSql sos : soslist) {
			set.add(sos.getTorderproviderid());
		}
		// 构建输出数据
		ShouhuoOutput so = new ShouhuoOutput();
		so.setLogno(soslist.get(0).getLogno());
		so.setCusname(soslist.get(0).getCusname());
		so.setCustelno(soslist.get(0).getCustelno());
		so.setDeliverycom(soslist.get(0).getDeliverycom());
		so.setChannelcode(soslist.get(0).getChannelcode());
		// 构建输出数据child=List<ShouhuoOutput_Provider> shouhuoList
		List<ShouhuoOutput_Provider> shouhuoList = new ArrayList<ShouhuoOutput_Provider>();
		for (String torderproviderid : set) {
			for (ShouhuoOutputSql sos : soslist) {
				if (sos.getTorderproviderid().equals(torderproviderid)) {
					ShouhuoOutput_Provider sop = new ShouhuoOutput_Provider();
					sop.setTorderproviderid(torderproviderid);
					sop.setProvidername(sos.getProvidername());
					sop.setProviderstate(sos.getProviderstate());
					sop.setOrdertime(sos.getOrdertime());
					// 构建输出数据child的child=List<ShouhuoOutput_Provider_Goods>
					// sopglist
					List<ShouhuoOutput_Provider_Goods> sopglist = new ArrayList<ShouhuoOutput_Provider_Goods>();
					for (ShouhuoOutputSql sosgoods : soslist) {
						if (sosgoods.getTorderproviderid().equals(torderproviderid)) {
							// 取得签收商品发布价
							ShouhuoOutput_Provider_Goods sopg = getBreakRules(sosgoods);
							sopg.setOrderitemid(sosgoods.getOrderitemid());
							sopg.setGoodsid(sosgoods.getGoodsid());
							sopg.setGoodsname(sosgoods.getGoodsname());
							sopg.setGoodsnum(sosgoods.getGoodsnum());
							sopg.setNorms(sosgoods.getNorms());
							sopg.setActualnum(sosgoods.getActualnum());
							sopg.setGoodbigpic(sosgoods.getGoodbigpic());
							sopg.setTgoodinfopoolid(sosgoods.getTgoodinfopoolid());
							sopglist.add(sopg);
						}
					}
					sop.setSopglist(sopglist);
					shouhuoList.add(sop);
					break;
				}
			}
		}
		so.setShouhuoList(shouhuoList);
		data.set_ReturnData(so);
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		return data;
	}
	/**
	 * 店主订单签收商品上架价格取得
	 * 
	 * @param or
	 * @return
	 */
	private ShouhuoOutput_Provider_Goods getBreakRules(ShouhuoOutputSql param) {
		ShouhuoOutput_Provider_Goods resultBean = new ShouhuoOutput_Provider_Goods();
		BigDecimal salePrice = ZERO;
		ShouhuoInput si = new ShouhuoInput();
		si.setOrderItemsId(param.getOrderitemid());
		ShouhuoOutputSql breakRule = shouhuoMapper_r.getBreakRule(si);
		if (breakRule == null) {
			// 该商品不可发布
			resultBean.setPublishFlg(0);
			return resultBean;
		}
		List<Tgoodskuinfo> hasGoodList= null;
		try {
			hasGoodList = goodsService.isExistLSGoods(breakRule.getDeviceNo(), String.valueOf(breakRule.getTgoodinfopoolid()));
		} catch (LakalaException e) {
		}
		if (hasGoodList!=null && hasGoodList.size()>0) {
			for (Tgoodskuinfo gsi : hasGoodList) {
				if (param.getNorms().equals(gsi.getSkufrontdisnamestr())) {
					// 该商品已发布
					resultBean.setPublishFlg(2);
					// 设定该商品现在的价格
					resultBean.setSalePrice(NumberUtils.decimalTwoPoint(gsi.getSaleprice()));
					return resultBean;
				}
			}
		}
		if (breakRule == null || breakRule.getCarton() == null || breakRule.getCarton().compareTo(ZERO) == 0) {
			// 该商品不可发布
			resultBean.setPublishFlg(0);
		} else {
			// 统一售价
			if (breakRule.getSalePrice() != null && breakRule.getSalePrice().compareTo(ZERO) != 0) {
				salePrice = breakRule.getSalePrice();
				resultBean.setUnifyFlg(1);
			}
			// 拆分售价
			else {
				salePrice = breakRule.getGoodsSalePrice().divide(breakRule.getCarton(), 2, BigDecimal.ROUND_HALF_UP);
				resultBean.setUnifyFlg(0);
				salePrice = salePrice.add(salePrice.multiply(NumberUtils.IsNullToZero(breakRule.getGrossProfitRatio())).divide(
						BigDecimal.valueOf(100)));
			}
			resultBean.setSalePrice(NumberUtils.decimalTwoPoint(salePrice.setScale(1, BigDecimal.ROUND_HALF_UP)));
			// 该商品可发布
			resultBean.setPublishFlg(1);

		}
		return resultBean;
	}
	/**
	 * 店主确认收货
	 */
	@Override
	public ObjectOutput<List<Integer>> updateShouhuo(ShouhuoInput si, HttpServletRequest req) {
		ObjectOutput<List<Integer>> data = new ObjectOutput<List<Integer>>();
		if (si == null || si.getLogno() == null || si.getLogno().equals("")) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		List<ShouhuoOutputSql> soslisto = shouhuoMapper_r.getShouhuoInfo(si);
		if (soslisto == null || soslisto.size() == 0) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			data.set_ReturnMsg(Constants.MSGNULL);
			return data;
		}
		List<ShouhuoOutputSql> soslist = new ArrayList<ShouhuoOutputSql>();
		for (ShouhuoOutputSql ss : soslisto) {
			if (!ss.getLstate().equals(Constants.TORDER_STATE_YQS)) {
				soslist.add(ss);
			}
		}
		Set<Integer> set = new HashSet<Integer>();
		if (soslist != null && soslist.size() > 0) {
			Map<String, Object> logmap = new HashMap<String, Object>();
			// 商品入库集合
			List<TgoodsSkuInfoInput> putawayList = new ArrayList<TgoodsSkuInfoInput>();
			// 更新订单子表/更新物流子表
			for (ShouhuoOutputSql sos : soslist) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("shipsignstate", Constants.SHIPSIGNSTATE_YQH);
				map.put("torderitemsid", sos.getOrderitemid());
				// 判断签收还是部分签收
				try {
					ShouhuoInputParam[] siparray = JSON.parse(si.getData(), ShouhuoInputParam[].class);
					for (int i = 0; i < siparray.length; i++) {
						if (sos.getOrderitemid().equals(siparray[i].getOrderitemid())) {
							int actualnum = Integer.parseInt(siparray[i].getActualnum());
							int goodsnum = Integer.parseInt(sos.getGoodsnum());
							if (actualnum < goodsnum) {
								map.put("state", Constants.TORDER_STATE_BFQS);
								if(logmap.get(sos.getLogid().toString()) == null){
									logmap.put(sos.getLogid().toString(), Constants.TORDER_STATE_BFQS);
								}
							} else {
								map.put("state", Constants.TORDER_STATE_YQS);
							}
							map.put("publicPice", siparray[i].getPublicPice());
							map.put("deviceNo", si.getPsam()); // zhiziwei
							// 店主收货商品入库 
							// by ls
							//当店主收货为0件时，不执行
							int comparenum = actualnum - Integer.parseInt(sos.getActualnum() == null?"0":sos.getActualnum());
							if(comparenum > 0){
								map.put("actualnum", comparenum);
								TgoodsSkuInfoInput putawayData = quhuoCommonService.putaway(map);
								if (putawayData != null) {
									putawayList.add(putawayData);
								}
							}
							map.put("actualnum", actualnum);
							// 更新订单子表
							shouhuoMapper_w.updatedzcount(map);
							// 更新物流子表
							shouhuoMapper_w.updatedzstateinlogitem(map);
							break;
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			// 更新订单供应商级表
			Set<String> providerset = new HashSet<String>();
			for (ShouhuoOutputSql sos : soslist) {
				String torderproviderid = sos.getTorderproviderid();
				if (torderproviderid != null && !torderproviderid.equals("")) {
					providerset.add(torderproviderid);
				}
			}
			for (String torderproviderid : providerset) {
				int providerstate = quhuoCommonService.getProviderState(torderproviderid);
				// 后面的-1只是起到flag的作用
				quhuoCommonService.updateOrderProviderState(torderproviderid, providerstate, -1);
			}
			// 更新订单级表
			Set<String> orderset = new HashSet<String>();
			for (ShouhuoOutputSql sos : soslist) {
				String orderid = sos.getOrderid();
				if (orderid != null && !orderid.equals("")) {
					orderset.add(orderid);
				}
			}
			for (String orderid : orderset) {
				int orderstate = quhuoCommonService.getOrderState(orderid);
				// 后面的-1只是起到flag的作用
				quhuoCommonService.updateOrderState(orderid, orderstate, -1);
			}
			// 更新物流主表
			for (ShouhuoOutputSql sos : soslist) {
				set.add(sos.getLogid());
			}
			if (set != null && set.size() > 0) {
				Map<String, Object> loginfomap = new HashMap<String, Object>();
				for (Integer logid : set) {
					loginfomap.put("state", logmap.get(logid.toString()) == null?Constants.TORDER_STATE_YQS:Constants.TORDER_STATE_BFQS);
					loginfomap.put("logid", logid);
					shouhuoMapper_w.updatestateinloginfo(loginfomap);
				}
			} else {
				data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
				data.set_ReturnMsg("数据异常");
				return data;
			}
			// 商品入库
			if (putawayList.size() > 0) {
				try {
//					goodsService.autoOnsale(putawayList, req);
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
			List<Integer> logidlist = new ArrayList<Integer>();
			if(set.size() > 0){
				for(Integer logid : set){
					logidlist.add(logid);
				}
			}
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnData(logidlist);
			return data;
		}
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg(Constants.MSGSIGNED);
		return data;
	}

}
