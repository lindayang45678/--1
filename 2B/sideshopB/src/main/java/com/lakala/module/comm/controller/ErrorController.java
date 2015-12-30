package com.lakala.module.comm.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.util.ReturnMsg;

@Controller
@RequestMapping("/error")
public class ErrorController {
	Logger logger = Logger.getLogger(ErrorController.class);
	
	@ResponseBody
	@RequestMapping(value = "/errorinfo")  
	public ObjectOutput errorinfo(){
		ObjectOutput info = new ObjectOutput();
		info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		
		return info;
	}
}
