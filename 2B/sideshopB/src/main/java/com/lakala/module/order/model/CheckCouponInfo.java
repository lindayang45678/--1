package com.lakala.module.order.model;

import java.util.List;
import java.util.Map;

import com.lakala.module.comm.ObjectInput;

public class CheckCouponInfo extends ObjectInput{
	
	private String pasmid;
	
	private String coupon;
	
	private String source;
	
	private String custelno;
	
	private String userid;
	
	private String wholesale;
	
	private List<Map> couponlist;
	
	private List<Map> items;
	
	private int paychannel = 1;
	
	

	
	public String getWholesale() {
		return wholesale;
	}

	public void setWholesale(String wholesale) {
		this.wholesale = wholesale;
	}

	

	public List<Map> getCouponlist() {
		return couponlist;
	}

	public void setCouponlist(List<Map> couponlist) {
		this.couponlist = couponlist;
	}

	public List<Map> getItems() {
		return items;
	}

	public void setItems(List<Map> items) {
		this.items = items;
	}

	public String getPasmid() {
		return pasmid;
	}

	public void setPasmid(String pasmid) {
		this.pasmid = pasmid;
	}

	

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCustelno() {
		return custelno;
	}

	public void setCustelno(String custelno) {
		this.custelno = custelno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getPaychannel() {
		return paychannel;
	}

	public void setPaychannel(int paychannel) {
		this.paychannel = paychannel;
	}

	

}
