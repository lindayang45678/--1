package com.lakala.util;


public interface SecurityOperator {
	
	/**
	 * 解密
	 * @param key 密钥
	 * @param sSrc 密文
	 * @return
	 * @throws Exception
	 */
	public String decrypt(final String key,final String sSrc) throws Exception ;
	/**
	 * 加密
	 * @param key 密钥
	 * @param sSrc 明文
	 * @return
	 * @throws Exception
	 */
	public String encrypt(final String key,final String sSrc) throws Exception;
	
	/**
	 * 验签
	 * @param message
	 * @param signStr
	 * @param key
	 * @return
	 */
	public boolean verifySign(final String message, final String signStr, String key)throws Exception;
	
	/**
	 * 签名
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String sign(final String message,final String key) throws Exception ;
}
