package com.lakala.module.order.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CheckCouponReturn  implements Serializable {

	private static final long serialVersionUID = 8491280952365576838L;

	private String retcode;

	private String retmeg;

	private String flag;// 优惠券标志，0-不可用，1-可用，2-已使用（使用次数为0）

	private CheckCoupProReturn coupondata;
	
	private String wholesale;

	private List<Coupons> couponlist;//用户输入的优惠券列表
	
	private List<TCoupon> tcouponlist;//当前可用所有优惠券
	
	/** 是否满足支付方式 0：满足 1：不满足 */
	private int paySucFlag = 0;
	/** 是否商品直降 0：否 1：是*/
	private int directDropFlag = 0;
	
	private Map directdropmap;
	
	private int priceflag = 0;
	
	private Map pricemap;
	
	
	
	
	
	

	public int getPriceflag() {
		return priceflag;
	}

	public void setPriceflag(int priceflag) {
		this.priceflag = priceflag;
	}

	public Map getPricemap() {
		return pricemap;
	}

	public void setPricemap(Map pricemap) {
		this.pricemap = pricemap;
	}

	public int getPaySucFlag() {
		return paySucFlag;
	}

	public void setPaySucFlag(int paySucFlag) {
		this.paySucFlag = paySucFlag;
	}

	public int getDirectDropFlag() {
		return directDropFlag;
	}

	public void setDirectDropFlag(int directDropFlag) {
		this.directDropFlag = directDropFlag;
	}

	
	
	public Map getDirectdropmap() {
		return directdropmap;
	}

	public void setDirectdropmap(Map directdropmap) {
		this.directdropmap = directdropmap;
	}


	public String getWholesale() {
		return wholesale;
	}

	public void setWholesale(String wholesale) {
		this.wholesale = wholesale;
	}
	

	public List<TCoupon> getTcouponlist() {
		return tcouponlist;
	}

	public void setTcouponlist(List<TCoupon> tcouponlist) {
		this.tcouponlist = tcouponlist;
	}

	public List<Coupons> getCouponlist() {
		return couponlist;
	}

	public void setCouponlist(List<Coupons> couponlist) {
		this.couponlist = couponlist;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public CheckCoupProReturn getCoupondata() {
		return coupondata;
	}

	public void setCoupondata(CheckCoupProReturn coupondata) {
		this.coupondata = coupondata;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmeg() {
		return retmeg;
	}

	public void setRetmeg(String retmeg) {
		this.retmeg = retmeg;
	}

}
