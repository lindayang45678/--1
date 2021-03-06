package com.lakala.module.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.service.UserService;
import com.lakala.module.user.vo.UserInfoOutput;
import com.lakala.module.user.vo.UserLoginInput;
import com.lakala.module.user.vo.UserRegisterInput;
import com.lakala.util.ReturnMsg;

/**
 * 用户登录
 * @author ph.li
 *
 */
@Controller
@RequestMapping("/user")
public class LoginController {
	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	
	/**
	 * 小B用户登录接口
	 * @param user 登录输入
	 * @see UserLoginInput
	 * @return
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> login(UserLoginInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.login(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户获取用户密码
	 * @param user 输入
	 * @see UserLoginInput
	 * @return 
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/getpwd",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> payLogin(UserLoginInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.getPassword(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户登出接口
	 * @param user 登出输入
	 * @see UserLoginInput
	 * @return
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> logout(UserLoginInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.logout(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户注册接口，第一步验证验证码，是否在media_statistics表注册，是否在用户中心注册
	 * @param user 注册输入
	 * @see UserRegisterInput
	 * @return 注册输出
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/register1",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> register1(UserRegisterInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.saveUserRegister1(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户注册接口，第二步，输入店铺详细注册信息开始真正注册
	 * @param user 注册输入
	 * @see UserRegisterInput
	 * @return 注册输出
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/register2")
	public @ResponseBody ObjectOutput<UserInfoOutput> register2(UserRegisterInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.saveUserRegister2(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户更改密码接口
	 * @param user 输入
	 * @see UserUpdPwdInput
	 * @return 注册输出
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/updpwd",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> updPwd(UserRegisterInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.updateUserPwd(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户找回密码接口
	 * @param user 输入
	 * @see UserUpdPwdInput
	 * @return 注册输出
	 * @see UserInfoOutput
	 */
	@RequestMapping(value = "/forgetpwd",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> forgetPwd(UserRegisterInput user){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.updateForgetPwd(user);
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户获取加密参数
	 * @param 
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	@RequestMapping(value = "/getem",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> getEM(){
		ObjectOutput<UserInfoOutput> info = null;
		try{
			info = userService.getEM4JSEncrypt();
		}catch(LakalaException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 小B用户更新设备token用于消息推送
	 * @param user 输入
	 * @see UserUpdPwdInput
	 * @return 输出
	 * @see UserInfoOutput
	 * yangjunguo 2015-07-24
	 */
	@RequestMapping(value = "/updpushtoken",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserInfoOutput> updateTerminalToken(UserLoginInput user,HttpServletRequest request){
		ObjectOutput<UserInfoOutput> info = null;
		
		try{
			if ((request.getContentType() == null ? "" : request.getContentType().toUpperCase()).startsWith("APPLICATION/JSON")) {
				String[] postData = IOUtils.readLines(request.getInputStream());
				if (postData.length > 0 && StringUtils.isNotBlank(postData[0]) && postData[0].trim().startsWith("{") && postData[0].trim().endsWith("}")) {
					user = JSON.parse(postData[0], UserLoginInput.class);
				}
			}
			info = userService.updateTerminalToken(user);
		}catch(LakalaException | IOException | ParseException e){
			logger.error(e.getMessage());
			info = new ObjectOutput<UserInfoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		} 
		return info;
	}
}
