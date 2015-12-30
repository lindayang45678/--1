package com.lakala.module.coupon.service;

import java.util.List;

import com.lakala.model.coupon.Coupon;
import com.lakala.module.coupon.vo.CouponInput;

public interface CouponService {
	
	public List<Coupon> findCoupon(CouponInput couponInput);
	
	public Long getCouponCount(CouponInput couponInput);
}
