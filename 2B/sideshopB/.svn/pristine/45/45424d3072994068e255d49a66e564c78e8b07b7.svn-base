package com.lakala.module.user.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.service.CustomerService;
import com.lakala.module.user.vo.CustomerInput;
import com.lakala.util.ReturnMsg;

/**
 * 客户信息
 * @author zjj
 * @date 2015年2月28日
 */

@Component
@RequestMapping("/customer")
public class CustomerController {
	
	private static Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;

	/**
	 * 查看客户列表
	 * @param id 店铺ID
	 * @return
	 */
	@RequestMapping(value = "/customerlist",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput customerlist(CustomerInput input){
		ObjectOutput info = null;
		
		try {
			info = customerService.customerList(input);
		} catch (LakalaException e) {
			logger.error("获取客户列表失败！",e);
			
			info = new ObjectOutput();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		
		return info;
	}
}
