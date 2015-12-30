package com.lakala.module.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lakala.module.comm.redis.RedisInterface;
import com.lakala.module.user.service.TokenService;

/**
 * 用于用户单点登录
 * 将TOKEN存储到REDIS
 * 存储结构：
 *  KEY : 13位手机号+1位来源
 *  VALUE : token
 *  
 * 获取到TOKEN后， 使用TokenUtil进行验证 ,具体见：@see TokenUtil
 * 
 * @author ph.li
 *
 */
public class TokenServiceImpl implements TokenService{

	@Autowired
	private RedisInterface redis;
	
	/**
	 * 根据手机号和来源作为KEY，存储TOKEN
	 * @param mobile
	 * @param source
	 */
	public boolean saveToken(String mobile , char source , String token){
		if(mobile == null || mobile.trim().equalsIgnoreCase("") || token == null || token.trim().equalsIgnoreCase("")){
			return false;
		}
		try{
		    redis.insert(mobile+source, token);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取TOKEN
	 * @param mobile
	 * @param source
	 * @return
	 */
	public String getToken(String mobile,char source){
		String token = null;
		try{
			token = redis.selectByKey(mobile+source);
		}catch(Exception e){
			
		}
		
		return token;
	}
	
}
