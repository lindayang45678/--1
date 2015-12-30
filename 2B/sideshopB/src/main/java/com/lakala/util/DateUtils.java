package com.lakala.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static DateFormat dateFormatYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
	
	public static DateFormat dateFormatYM = new SimpleDateFormat("yyyy-MM");
	
	public static DateFormat dateFormatYMD2 = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 格式化日期 
	 * @param date String
	 * @return
	 */
	public static String formatTimeToYMD(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormatYMD.format(date);

	}
	/**
	 * 格式化日期 
	 * @param date String
	 * @return
	 */
	public static Date formatTimeToYMDDate(Date date) {
		if (date == null) {
			return null;
		}
		try {
			return dateFormatYMD.parse(dateFormatYMD.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化日期 
	 * @param date String
	 * @return
	 */
	public static String formatTimeToYMDHMS(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormatYMDHMS.format(date);

	}
	
	/**
	 * 格式化日期 
	 * @param date String
	 * @return
	 */
	public static String formatTimeToYM(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormatYM.format(date);

	}
	
	/**
	 * 格式化日期  如果为空返回""
	 * @param date String
	 * @return
	 */
	public static String formatTimeToYMDHMS2(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormatYMDHMS.format(date);

	}
	
	/**
	 * 格式化日期字符串 
	 * @param date Date
	 * @return
	 */
	public static Date formatStrToYMDHMS(String  dateStr) throws ParseException {
		if (dateStr == null) {
			return null;
		}
		return dateFormatYMDHMS.parse(dateStr);
	}
	/**
	 * 格式化日期字符串 
	 * @param date Date
	 * @return
	 */
	public static Date formatStrToYMD(String  dateStr) throws ParseException {
		if (dateStr == null) {
			return null;
		}
		return dateFormatYMD.parse(dateStr);
	}
	

    
	/**将传过来的收益日期切分,进行查询*/
	public static String[] dateSplit(String date){
   
		    String[] str1 = date.split(" ");
		    
		    
		    String d = str1[0]+" "+"00:00:00";
		    String d2 = str1[0]+" "+"23:59:59";
		    
		    
		    String[] str = {d,d2};
		    
		   
		  return str;
		 
	}
	
	/**将日期格式转换成String类型的格式(文件夹的格式)*/
	public static String dateToStringTimeStamp(Date date) {
		String sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return sDate;
	}
	  
	/**将String格式转换成Date类型的格式(格式yyyy-MM-dd)*/
	public static Date stringToDate(String sdate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
