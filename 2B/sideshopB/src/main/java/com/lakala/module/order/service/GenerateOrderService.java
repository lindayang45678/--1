package com.lakala.module.order.service;

import java.util.List;
import java.util.Map;

import w.com.lakala.order.model.OrderInfoW;

import com.lakala.base.model.SDBMediaStatistics;
import com.lakala.base.model.Tshopkeeperaddr;
import com.lakala.model.ReturnBillInfo;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.model.AddOrderInput;
import com.lakala.module.order.model.CheckCouponInfo;
import com.lakala.module.order.model.CheckCouponReturn;
import com.lakala.module.order.model.ShopCommAddrInput;
import com.lakala.module.order.model.TCoupon;


/**
 * 生成订单
 * @author zuoyb
 *
 */
public interface GenerateOrderService {

	ObjectOutput<CheckCouponReturn> checkcoupon(CheckCouponInfo ei);
	
	/*
	 * 
	 * 获取终端信息，根据psam
	 */
	public SDBMediaStatistics getSdbMediaStatisticsByPsam(String psam);
	
	/*
	 * 
	 * 根据psam号查询可用的优惠券，2B
	 * 
	 */
	public List<TCoupon> getTcouponListByPsam(String psam);

	ObjectOutput getshopcommaddr(ShopCommAddrInput ei);

	ObjectOutput addorder(AddOrderInput ei);
	
	public OrderInfoW changeOrderInfoW(AddOrderInput in);
	
	/**
	 * 支付方式链接地址信息更新到订单表中以备未支付、支付失败的订单下次支付
	 * @param 
	 * @return
	 */
	public int updateTokenOfAllorderid(Map<String, Object> map);
	
	/**
	 * 拉卡拉账单号支付需要更新账单号到tallorder表paybillno
	 * @param 
	 * @return
	 */
	public int updatePaybillnoOfTallorderid(Map<String, Object> map);
	
	ObjectOutput insertAddress(Tshopkeeperaddr ska);
	ObjectOutput updateAddress(Tshopkeeperaddr ska);
	ObjectOutput deleteAddress(Tshopkeeperaddr ska);

	ReturnBillInfo payLakalaOut(String orderid, String total, String payType,
			String goodsnames);

	ReturnBillInfo payLakala(String orderid, String total, String payType,
			String goodsnames,String loginname,String source);

	int updateTallorderid(Map<String, Object> map);
}
