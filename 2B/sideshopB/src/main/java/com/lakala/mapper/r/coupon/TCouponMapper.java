package com.lakala.mapper.r.coupon;

import java.util.List;
import java.util.Map;

import com.lakala.module.order.model.TCoupon;

public interface TCouponMapper {
	

    TCoupon selectByPrimaryKey(Long favorableid);

	TCoupon selectCouponByMap(Map map);

	List<TCoupon> getTcouponListByPsam(String tel);

	TCoupon selectUseCouponByMap(Map<String, String> map);

	TCoupon selectUseCouponByMapWithPsam(Map<String, String> map);
}