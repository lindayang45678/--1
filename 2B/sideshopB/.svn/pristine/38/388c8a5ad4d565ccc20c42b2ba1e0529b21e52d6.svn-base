/**
 * 
 */
package com.lakala.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author XQ
 *
 */
public class GenerateIDUtil {

	/**
	 * 生成电商网点编号
	 * @param prefix 前缀
	 * @param date 包含的日期数据，如果为null，取当前日期
	 * @return string
	 */
	public static String createECnetpointID(String prefix, String mobile, Date date) {
		String rtnStr = "";
		String dateStr = "";
		String randomStr = "";
		String codeStr = "";
		Date dt = date;
		if (null == date) {
			dt = new Date();
		}
		try {
			dateStr = new SimpleDateFormat("yyMMdd").format(dt);
			mobile = mobile.trim();
			codeStr = numericToString(Long.parseLong(mobile), 32);
			randomStr = getRandomString(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rtnStr = prefix + dateStr + codeStr + randomStr;
		return rtnStr;
	}
    
	/*
	 * 生成随机PSAM
	 */
	public static String createPsamID(String prefix, String mobile) {
		String billId = "";
		String time = "";
		String codeStr = "";
		StringBuilder digit = new StringBuilder();
		try {
			time = new SimpleDateFormat("yyMMdd").format(new Date());
			codeStr = numericToString(Long.parseLong(mobile), 32);
			String random = new java.util.Random().nextInt(100) + "";
			int len = random.length();
			if (len < 2) {
				for (int i=0;i<2-len;i++) {
					digit.append("0");
				}
			}
			char[] cs = random.toCharArray();
			for (char c : cs) {
				digit.append(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		billId = prefix + time + codeStr + digit.toString();
		return billId;
	}
	
	/**
	 * 生成8位随机字符串(字母、数字的组合)
	 * @param length
	 * @return
	 */
	private static String getRandomString(int length){
	    Random random=new Random();
	    StringBuffer sb=new StringBuffer();
	    for(int i=0;i<length;i++){
	       int number=random.nextInt(3);
	       long result=0;
	       switch(number){
	          case 0:
	              result=Math.round(Math.random()*25+65);
	              sb.append(String.valueOf((char)result));
	              break;
	         case 1:
	             result=Math.round(Math.random()*25+97);
	             sb.append(String.valueOf((char)result));
	             break;
	         case 2:     
	             sb.append(String.valueOf(new Random().nextInt(10)));
	             break;
	        }
	   
	     }
	     return sb.toString();
	 }
	
	final static char[] digits = {
	      '0', '1', '2', '3', '4', '5', '6', '7',
	      '8','9', 'A', 'B', 'C', 'D', 'E', 'F', 
	      'G', 'H','L', 'J', 'K','M', 'N',  'P',
	      'R', 'S', 'T', 'U', 'V', 'X', 'Y','Q' };
	
	 private static String numericToString(long i, int system) {
	     long num = 0;
	     if (i < 0) {
	        num = ((long) 2 * 0x7fffffff) + i + 2;
	     } else {
	        num = i;
	     }
	     char[] buf = new char[32];
	     int charPos = 32;
		 while ((num / system) > 0) {
		     buf[--charPos] = digits[(int) (num % system)];
		     num /= system;
		 }
		 buf[--charPos] = digits[(int) (num % system)];
		 return new String(buf, charPos, (32 - charPos));
	 }
	 
	 public static void main(String[] args) {
//		 String str = "13776691443,15724540834,18256988012,15256980915,18156832580,15606767518,13366616078,15055110928,13637416356,13589094778,15874981518";
//		 String[] arry = str.split("\\,");
//		 for (String s : arry) {
//			 String out = createECnetpointID("E", s ,new Date());
//			 System.out.println(out + "\t" + s);
//		 }
		 
		 StringBuffer sb = new StringBuffer();
		 for (int i=0;i<100000;i++) {
			 Long l = Long.parseLong("1" + getRandomLong(10));
			 String codeStr = numericToString(l, 32);
			 sb.append(codeStr).append("\t");
		 }
		 try {
				FileWriter fw = new FileWriter("d:\\output.txt");
				fw.write(sb.toString(),0,sb.toString().length());    
		        fw.flush();
		        fw.close();
		        fw = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private static String getRandomLong(int length){
	    Random random=new Random();
	    StringBuffer sb=new StringBuffer();
	    for(int i=0;i<length;i++){
	       int number=random.nextInt(3);
	       switch(number){
	          case 0:
	        	  sb.append(String.valueOf(new Random().nextInt(10)));
	              break;
	         case 1:
	        	 sb.append(String.valueOf(new Random().nextInt(10)));
	             break;
	         case 2:     
	             sb.append(String.valueOf(new Random().nextInt(10)));
	             break;
	        }
	   
	     }
	     return sb.toString();
	 }
}
