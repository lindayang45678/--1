package com.lakala.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.castor.core.util.Base64Decoder;


public class RSAEncrypt implements SecurityOperator {

	private static SecurityOperator instance;

	private RSAEncrypt() {

	}

	public static SecurityOperator getInstance() {
		if (instance == null)
			instance = new RSAEncrypt();
		return instance;
	}

	/**
	 * * 用公钥验证签名的正确性 * * @param message * @param signStr * @return * @throws
	 * Exception
	 */
	public boolean verifySign(String message, String signStr, String  key)
			throws Exception {
		
		return this.verify(KeyTool.loadPublicKey(key), message.getBytes("UTF-8"), Base64Decoder.decode(signStr));
	}

	public String sign(String message, String key) throws Exception {
		return Base64.encode( this.sign(KeyTool.loadPrivateKey(key), message.getBytes("UTF-8")));
	}

	public static final byte[] toBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}

	/**
	 * 加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	public byte[] sign(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		// 用私钥对信息生成数字签名
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		try {
			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initSign(privateKey);
			signet.update(cipherData);
			byte[] signed = signet.sign(); // 对信息的数字签名
			return signed;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (SignatureException e) {
			throw new Exception("签名失败");
		}

	}

	public boolean verify(RSAPublicKey publicKey, byte[] cipherData,
			byte[] signText) throws Exception {
		try {

			Signature signatureChecker = Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(publicKey);
			signatureChecker.update(cipherData);
			// 验证签名是否正常
			if (signatureChecker.verify(signText))
				return true;
			else
				return false;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (SignatureException e) {
			e.printStackTrace();
			throw new Exception("签名失败");
		}
	}

	public String decrypt(String key, String sSrc) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String encrypt(String key, String sSrc) throws Exception {
		throw new UnsupportedOperationException();
	}


}
