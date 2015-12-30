package com.lakala.module.coupon.vo;

import java.util.List;

import com.lakala.model.coupon.Coupon;

public class CouponOutput {
	
	/** 验证令牌 */
	private String token;
	
	private List<Coupon> couponList;
	
	private long unusedCount;
	
	private long usedCount;
	
	public long getUnusedCount() {
		return unusedCount;
	}

	public void setUnusedCount(long unusedCount) {
		this.unusedCount = unusedCount;
	}

	public long getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(long usedCount) {
		this.usedCount = usedCount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Coupon> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}
	
}
