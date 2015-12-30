package com.lakala.module.profit.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 订单收益查询输入
 * 
 * @author zhaoqiugen
 *
 */
public class OrderEarningsInput extends ObjectInput {

	/** 用户手机号 */
	private String mobile;

	/** 订单id */
	private String orderProviderId;

	/** 订单来源 */
	private String orderSource;

	/** 是否已结算 */
	private String isSettle;

	/** 是否售后 */
	private String isAfterSale;

	/** 收益类型 */
	private String earningsType;

	/** 开始时间 */
	private String startTime;

	/** 结束时间 */
	private String endTime;

	/** 电商网点编号 */
	private String ecNetNo;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderProviderId() {
		return orderProviderId;
	}

	public void setOrderProviderId(String orderProviderId) {
		this.orderProviderId = orderProviderId;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getIsAfterSale() {
		return isAfterSale;
	}

	public void setIsAfterSale(String isAfterSale) {
		this.isAfterSale = isAfterSale;
	}

	public String getEarningsType() {
		return earningsType;
	}

	public void setEarningsType(String earningsType) {
		this.earningsType = earningsType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEcNetNo() {
		return ecNetNo;
	}

	public void setEcNetNo(String ecNetNo) {
		this.ecNetNo = ecNetNo;
	}

	public String getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(String isSettle) {
		this.isSettle = isSettle;
	}
}
