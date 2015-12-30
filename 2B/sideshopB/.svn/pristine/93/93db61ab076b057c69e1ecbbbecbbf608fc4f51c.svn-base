package com.lakala.module.order.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.service.NoPayOrder2PayService;
import com.lakala.module.order.service.OrderService;
import com.lakala.module.order.vo.ToPayInput;
import com.lakala.util.ReturnMsg;

@Controller
@RequestMapping("/order/nopay")
public class NoPayOrder2PayController {
	Logger logger = Logger.getLogger(NoPayOrder2PayController.class);
	@Autowired
	private NoPayOrder2PayService noPayOrder2PayService;
	
	@Autowired
	private OrderService orderService;
	@ResponseBody
	@RequestMapping(value="/topay")
	public ObjectOutput<Map<String,Object>> topay(ToPayInput tpi) throws IOException{
		ObjectOutput<Map<String,Object>> info = null;
		try{
			info = noPayOrder2PayService.topayhandle(tpi);
			if(info.get_ReturnCode() != null && info.get_ReturnCode().equals(ReturnMsg.CODE_SUCCESS)){
				this.orderService.updatePayChanelFor2Pay(tpi);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<Map<String,Object>>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
}
