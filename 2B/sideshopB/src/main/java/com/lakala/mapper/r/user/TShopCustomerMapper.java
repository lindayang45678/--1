package com.lakala.mapper.r.user;

import java.util.List;

import com.lakala.model.user.TShopCustomer;
import com.lakala.module.user.vo.CustomerInput;

public interface TShopCustomerMapper {
    
    List<TShopCustomer> selectCustomerListByShopMobile(CustomerInput input);
    
    int countCustomerList(CustomerInput input);
    
}