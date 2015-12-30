package com.lakala.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

public class RSA {

    private static Logger logger = Logger.getLogger(RSA.class);
    
    private static final String PUBLIC_KEY_MTS = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjTuSRErEquzrKcGt35XdOtHWqWB94XU70XZs2qb+Ae4A+21qPW2kD0yRggyiY53DjX/jfzZD4r4Nj8qcBeuXr4dUh2bLcQRHfMdV/5fg3jLmLjhSHabVVarURc0HoFOJuXxjSPrn/2r/l5O42x0UszhyWpIigj4QKNp8MD3JGRQIDAQAB";

//    private static final String PUBLIC_KEY_NATIVE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCU4LYh4cpdNQG8xfw5w00V+T2FZB4MR7svH1zgpzzdRdkKylGhEfWtB0SXflnECPKGAR1n+xppX0zfMepteJdQLlJU1wpY4GUXDRGZmtGqUlubbveEfG2pRLSWj3ztItbRxXOO3xGIZ2H85LVbMbdmKVTf0uqvyfj3jOA78vsjbQIDAQAB";
//    private static final String PRIVATE_KEY_NATIVE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJTgtiHhyl01AbzF/DnDTRX5PYVkHgxHuy8fXOCnPN1F2QrKUaER9a0HRJd+WcQI8oYBHWf7GmlfTN8x6m14l1AuUlTXCljgZRcNEZma0apSW5tu94R8balEtJaPfO0i1tHFc47fEYhnYfzktVsxt2YpVN/S6q/J+PeM4Dvy+yNtAgMBAAECgYBEzdVes0si6Gx1Icr/pxLpJNcZ3rtEUaJglM4HxUKLwMweAILZPcOcw88fdHVn8/qhk8JTW+lI6ZJNVHRTQ3gqEcfA8jjh+9T/Z34UWhgkVw4/5np2M28G7+RF+NDDteW7qFMw4gKY9slMw4Gck4sob+kV3TvkAPaCPWFXQZ/4AQJBAMWoI1U2xCP48C2Ho152Ap4BvfyggtoHSYshMFhAjz9zlgeylzxFNzZ8gbWJmcEP8t3GhReu9fv42AIRM0e+wc0CQQDA0psKme9wXIE9ac+f67GVXD2PDq8GBiJWWo0NhsaA/e3cu3rnvRP8RVU1z6jrPzfVWQXitQVtfTBHS9EP7MghAkAJBIjIJH2CXqMmkJ+leaDY8J9oXTJbHCYA0PzRqfBfJrjblQxNVaMVO0z3qVV4d2/PKnV8BSF343yHa515UnypAkEAi4ZZhdxJc+ab5hJwmGl2AHvUV3Xqk9NQeWfgdQ83CBO2UGig0JryoTKSK/PtaPw/rHNUXO1b1hQmIRDtYDaXwQJACEYOiwLx3TNFVc25q3ZrWmRLWCERPSjqcfesX7dMLkcePOoVS+of18iv3xhBoWmY1RAAg9g87ir64IOUCMs/5A==";
    
    private static final String PUBLIC_KEY_NATIVE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPe+G+F78meqkshqdLj3CZnvdG0c3bHSCJ5nRamnjh0dkDYCkj5DiKG93QM4diOM4umg3y7wsVlsIf+PTl2VcQ/z67J4Tlfnv+EVROB65lnYd7BM8TwlpNrRNV0G9T7fbW+IRzSc263irQ+WkWYUviUC4Y0TEdHzD1NfayBClxDQIDAQAB";
    private static final String PRIVATE_KEY_NATIVE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI974b4XvyZ6qSyGp0uPcJme90bRzdsdIInmdFqaeOHR2QNgKSPkOIob3dAzh2I4zi6aDfLvCxWWwh/49OXZVxD/PrsnhOV+e/4RVE4HrmWdh3sEzxPCWk2tE1XQb1Pt9tb4hHNJzbreKtD5aRZhS+JQLhjRMR0fMPU19rIEKXENAgMBAAECgYAEb4X5BG7qV5xnPfpqAZMvjJSEYfQFNA9PdApgyqm2AXC+A/kBnQqKanYe+5jR1oO5ORX5Tp5/GlwB9QZNHLzuQ7Gh05aloc+NaORZYPJ/+10RrdFYrdxBFydA4Ofwr3XN1D2TsLFTBr1yCgKlsVNFagivtcwkE5Ja6hLHbNCb2QJBANBYIKKaW7gily+PNmEOeqYGgKC3c5LeNH7cuVTLlsHoxkKGclyagFSzBzpKw4fAmavFpAYWDJfBKbHvZfSZMgsCQQCwTcWURZ0tDfBNdPKXOXNzM4QCKYF0W6F39mruRMUU/v4BH5+Y6vzd0KUr86cB200Gs4t6AMxVF4lAFkVJ77BHAkEAucA4qIAykgLVBCQM+Y9tLiTR/XhR5HmUGB82ZTF2Yb/lM16nC4T8MYfEQ+ZwWdIPsCPvzzVNWOdxAyFLpz4GbQJAEMU1GCePDewXgaawNMZYPwsVcYc1MzZMI7ci8MtF2/ttefniGo+vwgHI1Gd26WxM5WhKyi4tQaLdgWUc7i2InQJBAKjsarKXk+PjsgnroBb43gY8lpwpZh7j052cPx9gaXttfo1h6cw2LFz9Kvrtl48UzGbU6jk/quRgYxm0AaShI4A=";
    
    /** 
     * 私钥 
     */  
    private RSAPrivateKey privateKey;  
  
    /** 
     * 公钥 
     */  
    private RSAPublicKey publicKey;  
      
    /** 
     * 字节数据转字符串专用集合 
     */  
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
        'f'};
  
    /** 
     * 获取私钥 
     * @return 当前的私钥对象 
     */  
    public RSAPrivateKey getPrivateKey() {  
        return privateKey;  
    }  
  
    /** 
     * 获取公钥 
     * @return 当前的公钥对象 
     */  
    public RSAPublicKey getPublicKey() {  
        return publicKey;  
    }  
  
    /** 
     * 随机生成密钥对 
     */  
    public void genKeyPair(){  
        KeyPairGenerator keyPairGen= null;  
        try {  
            keyPairGen= KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        keyPairGen.initialize(1024, new SecureRandom());  
        KeyPair keyPair= keyPairGen.generateKeyPair();  
        this.privateKey= (RSAPrivateKey) keyPair.getPrivate();  
        this.publicKey= (RSAPublicKey) keyPair.getPublic();  
    }  
  
    /** 
     * 从文件中输入流中加载公钥 
     * @param in 公钥输入流 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public void loadPublicKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            loadPublicKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥输入流为空");  
        }  
    }  
  
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = Base64.encode(keyBytes);
        return s;
    }
  
    /** 
     * 从字符串中加载公钥 
     * @param publicKeyStr 公钥数据字符串 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public void loadPublicKey(String publicKeyStr) throws Exception{  
        try {  
//            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**  * 用公钥验证签名的正确性  *  * @param message  * @param signStr  * @return  * @throws Exception  */
    public boolean verifySign(String message, String signStr, PublicKey key) throws Exception {
        if (message == null || signStr == null || key == null) {
            return false;
        }
        Signature signetcheck = Signature.getInstance("MD5withRSA");
        signetcheck.initVerify(key);
        signetcheck.update(message.getBytes("UTF-8"));
        return signetcheck.verify(toBytes(signStr));
    }
  
    public byte[] sign(String message, PrivateKey key) throws Exception {
        Signature signetcheck = Signature.getInstance("MD5withRSA");
        signetcheck.initSign(key);
        signetcheck.update(message.getBytes("UTF-8"));
        return signetcheck.sign();
    }
    public static final byte[] toBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    /** 
     * 从文件中加载私钥 
     * @param keyFileName 私钥文件名 
     * @return 是否成功 
     * @throws Exception  
     */  
    public void loadPrivateKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            loadPrivateKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("私钥数据读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥输入流为空");  
        }  
    }  
  
    public void loadPrivateKey(String privateKeyStr) throws Exception{  
        try {  
//            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            this.privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
  
    /** 
     * 加密过程 
     * @param publicKey 公钥 
     * @param plainTextData 明文数据 
     * @return 
     * @throws Exception 加密过程中的异常信息 
     */  
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{  
        if(publicKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");  

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output= cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
  
    /** 
     * 解密过程 
     * @param privateKey 私钥 
     * @param cipherData 密文数据 
     * @return 明文 
     * @throws Exception 解密过程中的异常信息 
     */  
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{  
        if (privateKey== null){  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output= cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }         
    }  
  
      
    /** 
     * 字节数据转十六进制字符串 
     * @param data 输入数据 
     * @return 十六进制内容 
     */  
    public static String byteArrayToString(byte[] data){  
        StringBuilder stringBuilder= new StringBuilder();  
        for (int i=0; i<data.length; i++){  
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);  
            //取出字节的低四位 作为索引得到相应的十六进制标识符  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
            if (i<data.length-1){  
                //stringBuilder.append('');  
            }  
        }  
        return stringBuilder.toString();  
    }  
  
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_CHAR[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_CHAR[b[i] & 0x0f]);
        }
        return sb.toString();
    }
    
    public static byte[] hex2byte(byte[] b) { 

        if((b.length%2)!=0) 

           throw new IllegalArgumentException("长度不是偶数"); 

            byte[] b2 = new byte[b.length/2]; 

            for (int n = 0; n < b.length; n+=2) { 

              String item = new String(b,n,2); 

              b2[n/2] = (byte)Integer.parseInt(item,16); 

            } 

        return b2; 
    }
    
    public static String decrypt(String encryptStr) throws Exception {
        RSA rsaEncrypt= new RSA();  
        String decryptStr = "";
  
        //加载私钥  
        try {  
            rsaEncrypt.loadPrivateKey(PRIVATE_KEY_NATIVE);
            logger.info("加载私钥成功");
        } catch (Exception e) {  
        	logger.info(e.getMessage());
        	logger.info("加载私钥失败");
        }  
  
        try {  
            //解密  
            byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), RSA.toBytes(encryptStr));  
            logger.info("明文长度:"+ plainText.length);
            logger.info(new String(plainText));
            
            decryptStr = new String(plainText);
        } catch (Exception e) {  
        	logger.info(e.getMessage()); 
        }  
        return decryptStr;
    }  

    public static String encrypt(String decryptStr) throws Exception {
        RSA rsaEncrypt= new RSA();  
        String encryptStr = "";
        
        //加载公钥  
        try {  
            rsaEncrypt.loadPublicKey(PUBLIC_KEY_NATIVE);
            logger.info("加载公钥成功"); 
        } catch (Exception e) {  
        	logger.info(e.getMessage()); 
        	logger.info("加载公钥失败"); 
        }  
  
        try {  
            //加密  
            byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), decryptStr.getBytes());  
            logger.info("密文长度:"+ cipher.length); 
            logger.info(RSA.byteArrayToString(cipher)); 
            logger.info(RSA.toHexString(cipher)); 
            
            encryptStr = RSA.byteArrayToString(cipher);
        } catch (Exception e) {  
        	logger.info(e.getMessage()); 
        }  
        return encryptStr;
    }  

    public static String encryptMTS(String decryptStr) throws Exception {
        RSA rsaEncrypt= new RSA();  
        String encryptStr = "";
        
        //加载公钥  
        try {  
            rsaEncrypt.loadPublicKey(PUBLIC_KEY_MTS);
            logger.info("加载公钥成功");
        } catch (Exception e) {  
        	logger.info(e.getMessage());
        	logger.info("加载公钥失败");
        }  
  
        try {  
            //加密  
            byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), decryptStr.getBytes());  
            logger.info("密文长度:"+ cipher.length);
            logger.info(RSA.byteArrayToString(cipher));
//            logger.info(RSA.toHexString(cipher));
            
            encryptStr = RSA.byteArrayToString(cipher);
        } catch (Exception e) {  
        	logger.info(e.getMessage());
        }  
        return encryptStr;
    }  
    
    public static void main(String[] args) {
    	
    	try {
    		System.out.println("密文：" + encrypt("123456"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
//    	try {
//    		System.out.println("明文：" + decrypt("1efba45b430c3bad91596d9dee807a098ab9f019fddf04575e100858f28cadee12624a1696db1ab02b120b1ae87434556cd1a357d19580a319859dc7feeec9ecf2c010955d5193740310b77a571cb7d54b756cdbcf75fb3b2d3be805aa3c2b9b0658107f9604351be12a335d2fd2fc95c7a0a68758485d3974660af930b3b692"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    	
    	
//    	try {
//			String str = encryptMTS("123aaa");
//			System.out.println("str=>" + str);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}  
