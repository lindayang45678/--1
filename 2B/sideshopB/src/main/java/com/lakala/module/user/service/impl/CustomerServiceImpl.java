package com.lakala.module.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lakala.exception.LakalaException;
import com.lakala.mapper.r.user.TShopCustomerMapper;
import com.lakala.model.user.TShopCustomer;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.service.CustomerService;
import com.lakala.module.user.vo.CustomerInput;
import com.lakala.module.user.vo.CustomerOutput;
import com.lakala.util.ReturnMsg;

/**
 * 客户信息
 * @author zjj
 * @date 2015年3月2日
 */

@Component
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private TShopCustomerMapper customerMapper_r;

	@Override
	public ObjectOutput customerList(CustomerInput input) throws LakalaException{
		
		ObjectOutput info = new ObjectOutput();
		info.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		info.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		
		if(null == input.getPage()){
			input.setPage(1);
		}
		
		if(null == input.getPageSize()){
			input.setPageSize(10);
		}
		
		int page = input.getPage();
		
		input.setPage((input.getPage() - 1) * input.getPageSize());
		
		List<TShopCustomer> shopList = customerMapper_r.selectCustomerListByShopMobile(input);
		int count = customerMapper_r.countCustomerList(input);
		CustomerOutput output = new CustomerOutput();
		output.setCustomerList(shopList);
		output.setCount(count);
		output.setPage(page);
		output.setPageSize(input.getPageSize());
		
		info.set_ReturnData(output);
		
		return info;
	}

}
