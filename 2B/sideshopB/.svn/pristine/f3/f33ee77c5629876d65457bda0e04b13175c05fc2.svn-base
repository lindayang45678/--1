package com.lakala.module.user.service;

import com.lakala.exception.LakalaException;
import com.lakala.model.user.Tverificationcode;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.vo.SmsInput;

public interface SmsVerificationCodeService {

	public ObjectOutput sendVerificationCode(SmsInput info) throws LakalaException;
	
	/**
	 * 根据条件查询短信验证码
	 * @param code
	 * @return
	 */
	public Tverificationcode queryVerificationCode(Tverificationcode code);
	
	/**
	 * 更新短信验证码为已用
	 * @param code
	 * @return
	 */
	public int updateVerificationCode(Tverificationcode code);
}
