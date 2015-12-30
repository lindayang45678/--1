package com.lakala.util;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * RSA加密原理概述  
 * RSA的安全性依赖于大数的分解，公钥和私钥都是两个大素数（大于100的十进制位）的函数。  
 * 据猜测，从一个密钥和密文推断出明文的难度等同于分解两个大素数的积  
 * ===================================================================  
 * （该算法的安全性未得到理论的证明）  
 * ===================================================================  
 * 密钥的产生：  
 * 1.选择两个大素数 p,q ,计算 n=p*q;  
 * 2.随机选择加密密钥 e ,要求 e 和 (p-1)*(q-1)互质  
 * 3.利用 Euclid 算法计算解密密钥 d , 使其满足 e*d = 1(mod(p-1)*(q-1)) (其中 n,d 也要互质)  
 * 4:至此得出公钥为 (n,e) 私钥为 (n,d)  
 * ===================================================================  
 * 加解密方法：  
 * 1.首先将要加密的信息 m(二进制表示) 分成等长的数据块 m1,m2,...,mi 块长 s(尽可能大) ,其中 2^s<n  
 * 2:对应的密文是： ci = mi^e(mod n)  
 * 3:解密时作如下计算： mi = ci^d(mod n)  
 * ===================================================================  
 * RSA速度  
 * 由于进行的都是大数计算，使得RSA最快的情况也比DES慢上100倍，无论 是软件还是硬件实现。  
 * 速度一直是RSA的缺陷。一般来说只用于少量数据 加密。 
 *文件名：RSAUtil.java<br>
 *@author 董利伟<br>
 *版本:<br>
 *描述：<br>
 *创建时间:2008-9-23 下午09:58:16<br>
 *文件描述：<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
public class RSAUtil {

	public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
 
	public static void main(String[] args) {
		Map<String, Object> keyMap;
		try {
			keyMap = initKey();
			String publicKey =  getPublicKey(keyMap);
			System.out.println(publicKey);
			String privateKey =  getPrivateKey(keyMap);
			System.out.println(privateKey);
			
			
		} catch (Exception e) { 
		    e.printStackTrace();
		}	 
	}
	
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
         Key key = (Key) keyMap.get(PUBLIC_KEY); 
         byte[] publicKey = key.getEncoded(); 
         return encryptBASE64(key.getEncoded());
	}
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
	    Key key = (Key) keyMap.get(PRIVATE_KEY); 
	    byte[] privateKey =key.getEncoded(); 
	    return encryptBASE64(key.getEncoded());
	}  
           
    public static byte[] decryptBASE64(String key) throws Exception {               
        return (new BASE64Decoder()).decodeBuffer(key);               
    }                                 
               
    public static String encryptBASE64(byte[] key) throws Exception {               
        return (new BASE64Encoder()).encodeBuffer(key);               
    }       
    
    public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
}
