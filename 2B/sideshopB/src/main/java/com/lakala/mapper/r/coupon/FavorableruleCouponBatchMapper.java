package com.lakala.mapper.r.coupon;

import com.lakala.model.coupon.FavorableruleCouponBatch;

public interface FavorableruleCouponBatchMapper {

    FavorableruleCouponBatch selectByPrimaryKey(Long id);

	FavorableruleCouponBatch selectByBatchId(Long batchId);

}