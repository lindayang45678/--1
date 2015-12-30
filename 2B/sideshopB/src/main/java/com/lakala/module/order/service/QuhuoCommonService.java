package com.lakala.module.order.service;

import java.util.Map;

import com.lakala.module.goods.vo.TgoodsSkuInfoInput;

public interface QuhuoCommonService {

	int getProviderState(String torderproviderid);

	void updateOrderState(String orderId, int orderstate, int paychannel);

	int getOrderState(String orderId);

	void updateOrderProviderState(String torderproviderid, int providerstate,
			int paychannel);

	int getAllOrderPayState(Integer allorderid);

	void updateAllOrderPayState(Integer allorderid);
	/**
	 * 商品入库数据取得
	 * @param map
	 * @return
	 */
	TgoodsSkuInfoInput putaway(Map<String, Object> map);
}
