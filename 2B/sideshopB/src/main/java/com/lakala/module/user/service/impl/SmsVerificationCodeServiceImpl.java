package com.lakala.module.user.service.impl;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lakala.mapper.r.user.TverificationcodeMapper;
import com.lakala.model.user.Tverificationcode;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.comm.SmsVerificationCodeHelper;
import com.lakala.module.user.service.SmsVerificationCodeService;
import com.lakala.module.user.vo.SmsInput;
import com.lakala.util.ReturnMsg;

@Component
public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService{
	
	private static Logger logger = Logger.getLogger(SmsVerificationCodeServiceImpl.class);
	
	@Autowired
	private TverificationcodeMapper codeMapper_r;
	
	@Autowired
	private com.lakala.mapper.w.user.TverificationcodeMapper codeMapper_w;
	
	@Override
	public ObjectOutput sendVerificationCode(SmsInput input) {
		
		ObjectOutput info = new ObjectOutput();
		if(null == input || !StringUtils.hasText(input.getPhone())){

			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg("请输入手机号码！");
			return info;
		}
		

        long r = Math.abs(new Random().nextLong());
        String vc = String.valueOf(r).substring(0, 6);
        String content = "您在拉卡拉身边小店获取的验证码为："+vc + ",感谢使用！";
        
		try {
			SmsVerificationCodeHelper.sendVerificationCode(input.getPhone(), content);
		} catch (Exception e) {
			logger.error("发送验证码失败，请检查短信接口是否正常！",e);
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg("发送验证码失败，请检查短信接口是否正常！");
			return info;
		} 
		
		Tverificationcode code = new Tverificationcode();
		code.setCode(Integer.parseInt(vc));
		code.setMobile(input.getPhone());
		code.setSource(Integer.parseInt(input.getType()));
		codeMapper_w.insert(code);
		
		info.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		info.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		return info;
	}

	@Override
	public Tverificationcode queryVerificationCode(Tverificationcode code) {
		return codeMapper_r.selectByExample(code);
	}

	@Override
	public int updateVerificationCode(Tverificationcode code) {
		return codeMapper_w.update(code);
	}
}
