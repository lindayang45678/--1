package com.lakala.module.order.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.service.ExpressService;
import com.lakala.module.order.vo.ExpressInput;
import com.lakala.module.order.vo.ExpressOutput;
import com.lakala.util.ReturnMsg;

@Controller
@RequestMapping("/order/express")
public class ExpressController {
	Logger logger = Logger.getLogger(OrderController.class);
	@Autowired
	private ExpressService es;
	@ResponseBody
	@RequestMapping(value = "/search")  
	public ObjectOutput<ExpressOutput> searchExpressInfo(ExpressInput ei){
		ObjectOutput<ExpressOutput> info = null;
		try{
			info = es.getExpressInfo(ei);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<ExpressOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
}
