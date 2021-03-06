package com.lakala.module.profit.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 订单收益详情查询数据输入
 * 
 * @author pengyunle
 *
 */
public class OrderEarningsDetailInput extends ObjectInput {

	/** 供应商订单编码 */
	private String orderProviderId;

	/** 收益日期 YYYY-MM-DD */
	private String earningsDate;

	/** 退货订单区分标识：正向正常订单：0； 逆向未退货订单：1，逆向退货订单：2 */
	private Integer returnFlg;

	/** 收益类型 */
	private String earningsType;
	
	/** 支付流水号 */
	private String payId;

	public String getOrderProviderId() {
		return orderProviderId;
	}

	public void setOrderProviderId(String orderProviderId) {
		this.orderProviderId = orderProviderId;
	}

	public String getEarningsDate() {
		return earningsDate;
	}

	public void setEarningsDate(String earningsDate) {
		this.earningsDate = earningsDate;
	}

	public Integer getReturnFlg() {
		return returnFlg;
	}

	public void setReturnFlg(Integer returnFlg) {
		this.returnFlg = returnFlg;
	}

	public String getEarningsType() {
		return earningsType;
	}

	public void setEarningsType(String earningsType) {
		this.earningsType = earningsType;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}
}
