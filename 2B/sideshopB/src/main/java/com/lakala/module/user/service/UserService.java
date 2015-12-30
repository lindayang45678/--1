package com.lakala.module.user.service;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.vo.UserInfoOutput;
import com.lakala.module.user.vo.UserLoginInput;
import com.lakala.module.user.vo.UserRegisterInput;

/**
 * 用户业务接口
 * @author ph.li
 *
 */
public interface UserService {

	/**
	 * 小B用户登录验证业务
	 * @param user @see UserLoginInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> login(UserLoginInput user) throws LakalaException;

	/**
	 * 小B用户获取用户密码
	 * @param user @see UserLoginInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> getPassword(UserLoginInput user) throws LakalaException;
	
	/**
	 * 小B用户登出验证业务
	 * @param user @see UserLoginInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> logout(UserLoginInput user) throws LakalaException;

	/**
	 * 用户注册验证业务
	 * @param user @see UserRegisterInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> saveUserRegister1(UserRegisterInput user) throws LakalaException;

	/**
	 * 用户注册验证业务
	 * @param user @see UserRegisterInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> saveUserRegister2(UserRegisterInput user) throws LakalaException;
	
	/**
	 * 小B用户更改密码验证业务
	 * @param user @see UserRegisterInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> updateUserPwd(UserRegisterInput user) throws LakalaException;

	/**
	 * 小B用户忘记密码验证业务
	 * @param user @see UserRegisterInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> updateForgetPwd(UserRegisterInput user) throws LakalaException;

	/**
	 * 小B用户获取加密参数
	 * @param 
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<UserInfoOutput> getEM4JSEncrypt() throws LakalaException;
	
	/**
	 * 小B用户更新设备token用于消息推送
	 * @param user @see UserLoginInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 * yangjunguo 2015-07-24
	 */
	public ObjectOutput<UserInfoOutput> updateTerminalToken(UserLoginInput user) throws LakalaException;
}