package com.lakala.util;

public class SecurityFactory {
	public final static String TYPE_AES = "AES";
	public final static String TYPE_DES = "DES";
	public final static String TYPE_RSA = "RSA";

	public static SecurityOperator getOperator(String type) {
		if (TYPE_AES.equals(type)) {
			return AESOperator.getInstance();
		} else if (TYPE_RSA.equals(type)) {
			return RSAEncrypt.getInstance();
		}

		return null;
	}
}
