package com.lakala.module.user.controller;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.service.SmsVerificationCodeService;
import com.lakala.module.user.vo.SmsInput;
import com.lakala.util.ReturnMsg;

@Component
@RequestMapping("/auth")
public class SmsVerificationCodeController {
	
	private static Logger logger = Logger.getLogger(SmsVerificationCodeController.class);

	@Autowired
	private SmsVerificationCodeService smsService;
	
	@RequestMapping(value = "/getCode",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput getCode(SmsInput input){
		ObjectOutput info = null;

		try {
			info = smsService.sendVerificationCode(input);
		} catch (LakalaException e) {
			logger.error("发送验证码失败！",e);
			
			info = new ObjectOutput();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
}
