package com.lakala.module.order.model;


public class Coupons {
	
	
	private String batchCode; //优惠券批次号
	private String couponCode; //优惠券号
	private Double couponPrice; //优惠券面值
	private String count; //优惠券使用次数
	private Double ttlCouponPrice; //优惠券总面值
	private int isValidate = 0; //是否有效
	private String remark="无效" ; //优惠券无效的原因
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(Double couponPrice) {
		this.couponPrice = couponPrice;
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Double getTtlCouponPrice() {
		return ttlCouponPrice;
	}
	public void setTtlCouponPrice(Double ttlCouponPrice) {
		this.ttlCouponPrice = ttlCouponPrice;
	}
	public int getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(int isValidate) {
		this.isValidate = isValidate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
