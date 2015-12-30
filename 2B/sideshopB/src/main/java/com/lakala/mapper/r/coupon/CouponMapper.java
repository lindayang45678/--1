package com.lakala.mapper.r.coupon;

import java.util.List;
import java.util.Map;

import com.lakala.model.coupon.Coupon;

public interface CouponMapper {
	
	/**查询某个用户下某个状态的优惠券*/
	public List<Coupon> findCoupon(Map<String,Object> params);
	
	public Long getCouponCount(Map<String,Object> params);
}
