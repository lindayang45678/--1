package com.lakala.mapper.r.coupon;

import com.lakala.model.coupon.FavorableRuleKDB;

public interface FavorableRuleKDBMapper {
    FavorableRuleKDB selectByPrimaryKey(Integer id);
    
    FavorableRuleKDB selectByFavId(long favId);
}
