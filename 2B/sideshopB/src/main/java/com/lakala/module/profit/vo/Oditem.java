package com.lakala.module.profit.vo;

import java.math.BigDecimal;

public class Oditem {
	
	private String goodId;
	
	private String goodName;

	private BigDecimal goodSalePrice;

	private BigDecimal orderPrice;

	private Integer goodCount;

	private String orderState;

	private String receiveDate;

	private String refundTime;

	private String invaliState;

	private BigDecimal earningsAmount;

	private BigDecimal duyPay;

	private String returnDate;
	
	//商品图片
	private String goodPicture;
	//商品规格
	private String goodSpecification;
	
	private String tgoodinfopoolid;

	//参数直降活动标示：0-未参与，1-参与
	private Integer directflag;
	
	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public BigDecimal getGoodSalePrice() {
		return goodSalePrice;
	}

	public void setGoodSalePrice(BigDecimal goodSalePrice) {
		this.goodSalePrice = goodSalePrice;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getInvaliState() {
		return invaliState;
	}

	public void setInvaliState(String invaliState) {
		this.invaliState = invaliState;
	}

	public BigDecimal getEarningsAmount() {
		return earningsAmount;
	}

	public void setEarningsAmount(BigDecimal earningsAmount) {
		this.earningsAmount = earningsAmount;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public BigDecimal getDuyPay() {
		return duyPay;
	}

	public void setDuyPay(BigDecimal duyPay) {
		this.duyPay = duyPay;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getGoodPicture() {
		return goodPicture;
	}

	public void setGoodPicture(String goodPicture) {
		this.goodPicture = goodPicture;
	}

	public String getGoodSpecification() {
		return goodSpecification;
	}

	public void setGoodSpecification(String goodSpecification) {
		this.goodSpecification = goodSpecification;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getTgoodinfopoolid() {
		return tgoodinfopoolid;
	}

	public void setTgoodinfopoolid(String tgoodinfopoolid) {
		this.tgoodinfopoolid = tgoodinfopoolid;
	}

	public Integer getDirectflag() {
		return directflag;
	}

	public void setDirectflag(Integer directflag) {
		this.directflag = directflag;
	}
}
