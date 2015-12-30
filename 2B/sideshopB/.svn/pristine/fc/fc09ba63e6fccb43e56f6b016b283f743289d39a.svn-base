package com.lakala.module.user.service;
/**
 * TOKEN存储接口
 * @author ph.li
 *
 */
public interface TokenService {

	/**
	 * 根据手机号和来源作为KEY，存储TOKEN
	 * @param mobile
	 * @param source
	 */
	public boolean saveToken(String mobile , char source , String token);
	
	
	/**
	 * 获取TOKEN
	 * @param mobile
	 * @param source
	 * @return
	 */
	public String getToken(String mobile,char source);
}
