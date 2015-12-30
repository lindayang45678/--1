package com.lakala.mapper.r.profit;

import com.lakala.model.profit.Torderprovider;


public interface TorderproviderMapper {
    
    // 查询订单详情 根据二级订单
	Torderprovider selectByOrderProviderId(String torderproviderid);
}