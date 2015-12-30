package com.lakala.util;

import java.io.IOException;
import java.math.BigInteger;

/**
 * token工具
 * 13位时间+11位手机号码+设备号
 * @author ph.li
 *
 */
public class TokenUtil {

	//失效时间7*24*60*60*1000 = 604800000
	private final static BigInteger VALID = new BigInteger("604800000");
	
	//KEY
	private final static String KEY = "lakalaSi";
	
	//获取TOKEN
	@Deprecated
	public static String getToken(final String mobile){	
		StringBuffer sb = new StringBuffer(25);
		sb.append(System.currentTimeMillis());
		if(mobile != null){
			sb.append(1).append(mobile);
		}else{
			sb.append(0);
		}
		String token = null;
		try {
			token = DES.encrypt(sb.toString(), KEY);  
		} catch (Exception e) {
		}
		sb.setLength(0);
		return token;
	}
	
	//根据手机号和设备号(终端设备号，无则不验证)获取TOKEN,无手机号码或设备号，则不生成设备号
	//13位时间+1位来源+11位手机号码+设备号
	public static String getToken(final String mobile,final String deviceNo){	
		if(mobile == null || mobile.trim().equalsIgnoreCase("") || deviceNo == null || deviceNo.trim().equalsIgnoreCase("")){
			return null;
		}
		StringBuffer sb = new StringBuffer(64);
		sb.append(System.currentTimeMillis());
		sb.append(mobile).append(deviceNo);
		String token = null;
		try {
			token = DES.encrypt(sb.toString(), KEY);  
		} catch (Exception e) {
		}
		sb.setLength(0);
		return token;
	}
	
	//判断TOKEN是否有效(y:有效  n:无效)
	@Deprecated
	public static boolean isValidToken(final String mobile,final String token){
		if(token == null) return false;
		String value = null;
		try {
			value = DES.decrypt(token,KEY);
		} catch (IOException e) {
		} catch (Exception e) {
			
		}
		if(value != null){
			//判断时效先
			long t = -1;
			try{
				t = Long.parseLong(value.substring(0,13));
			}catch(NumberFormatException e){
				
			}
			if(t == -1) return false;
			t = System.currentTimeMillis()-t;
			if(VALID.compareTo(BigInteger.valueOf(t))<= 0) return false;
			if(value.charAt(13) == '0') return true;
			if(value.indexOf(mobile) > -1){
				//手机号是OK的
				return true;
			}
		}
		return false;
	}
	
	//判断TOKEN是否有效(y:有效  n:无效)
	public static boolean isValidToken(final String token,final String mobile,final String deviceNo){
		if(token == null) return false;
		String value = null;
		try {
			value = DES.decrypt(token,KEY);
		} catch (IOException e) {
		} catch (Exception e) {
			
		}
		if(value != null){
			//判断时效先
			long t = -1;
			try{
				t = Long.parseLong(value.substring(0,13));
			}catch(NumberFormatException e){
				
			}
			if(t == -1) return false;
			t = System.currentTimeMillis()-t;
			if(VALID.compareTo(BigInteger.valueOf(t))<= 0) return false;
			value = value.substring(13);
			System.out.println("value="+value);
			if(value.substring(0,11).equalsIgnoreCase(mobile.trim()) && value.substring(11).equalsIgnoreCase(deviceNo.trim())){
				//是OK的
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		/*String token = getToken("18201290254");
		System.out.println(token.length());
		boolean flag = isValidToken("18201290254","pHMCGV5M+LV+PwSiLDK0EfbvoGxXT2kecpeOjf89yqI=");
		System.out.println(flag);*/
		String token = getToken("18201290254","23ojwoeu9eu");
		System.out.println(token.length());
		boolean flag = isValidToken(token,"18201290254","23ojwoeu8eu");
		System.out.println(flag);

	}
	
}
