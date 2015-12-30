package com.lakala.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AESOperator implements SecurityOperator{
	private static AESOperator instance = null;
	private static String KEY_AES="AES/ECB/PKCS5Padding";
	private static String KEY_AES_CBC="AES/CBC/PKCS5Padding";
	private AESOperator() {

	}

	public static AESOperator getInstance() {
		if (instance == null)
			instance = new AESOperator();
		return instance;
	}

	public String encrypt(String key,String sSrc) throws Exception {
		String _key=key;
		String _ivParameter="";
		String cipherKey="";
		if(key.length()==16){
			_key=key;
			cipherKey=KEY_AES;
		}
		if(key.length()==32){
			_key=key.substring(0,16);
			_ivParameter=key.substring(16,32);
			cipherKey=KEY_AES_CBC;
		}
		Cipher cipher = Cipher.getInstance(cipherKey);
		byte[] raw = _key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		if(!"".equals(_ivParameter)){
			
			IvParameterSpec iv = new IvParameterSpec(_ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		}else{
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec );
		}
		
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}
	
	// 解密
	public String decrypt(final String key,String sSrc) throws Exception {
		try {
			String _key=key;
			String _ivParameter="";
			String cipherKey="";
			if(key.length()==16){
				_key=key;
				cipherKey=KEY_AES;
			}
			if(key.length()==32){
				_key=key.substring(0,16);
				_ivParameter=key.substring(16,32);
				cipherKey=KEY_AES_CBC;
			}
			Cipher cipher = Cipher.getInstance(cipherKey);
			byte[] raw = _key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			if(!"".equals(_ivParameter)){
				IvParameterSpec iv = new IvParameterSpec(_ivParameter.getBytes());
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			}else{
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			}
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}


	public boolean verifySign(String message, String signStr, String key)
			throws Exception {
		throw new UnsupportedOperationException();
	}

	public String sign(String message, String  key) throws Exception {
		throw new UnsupportedOperationException();
	}
}