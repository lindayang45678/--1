package com.lakala.module.order.model;

import java.io.Serializable;
import java.util.List;

public class CheckCoupProReturn implements Serializable {
	
	private static final long serialVersionUID = 8491280952365576838L;
	
	private String coupon;//优惠券号
	
	private String pasmid;//pasm号
	
	private String goodstotal;//纯商品售价
	
	private String logisfee;//物流费用
	
	private String couponvalue;//现金券价值
	
	private String promfee;//促销优惠金额
	
	private String total;//不包含运费的总金额
	
	private String actualtotal;//订单实际需要刷卡金额
	
	private String couponuse;///** 优惠券是否已经使用  0:未使用 1已使用*/
	
	private List<CheckCoupGoodsReturn> items;
	
	

	public String getCouponuse() {
		return couponuse;
	}

	public void setCouponuse(String couponuse) {
		this.couponuse = couponuse;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<CheckCoupGoodsReturn> getItems() {
		return items;
	}

	public void setItems(List<CheckCoupGoodsReturn> items) {
		this.items = items;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getPasmid() {
		return pasmid;
	}

	public void setPasmid(String pasmid) {
		this.pasmid = pasmid;
	}

	public String getGoodstotal() {
		return goodstotal;
	}

	public void setGoodstotal(String goodstotal) {
		this.goodstotal = goodstotal;
	}

	public String getLogisfee() {
		return logisfee;
	}

	public void setLogisfee(String logisfee) {
		this.logisfee = logisfee;
	}

	public String getCouponvalue() {
		return couponvalue;
	}

	public void setCouponvalue(String couponvalue) {
		this.couponvalue = couponvalue;
	}

	public String getPromfee() {
		return promfee;
	}

	public void setPromfee(String promfee) {
		this.promfee = promfee;
	}

	public String getActualtotal() {
		return actualtotal;
	}

	public void setActualtotal(String actualtotal) {
		this.actualtotal = actualtotal;
	}
	
	
	
	

}
