package com.lakala.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public final static String format1 = "yyyy-MM-dd";
	public final static String format2 = "yyyy-MM-dd HH:mm:ss";
	public final static String format3 = "yyyyMMddHHmm";	
	public final static String format4 = "yyyyMM";
	public final static String format5 = "yyyyMMdd";
	public final static String format6 = "yyyyMMddHHmmss";
	public final static String format7 = "yyyyMMddHHmmssSSS";
	/** 日期转化 */
	public final static long convertS2Date(String time){
		SimpleDateFormat df = new SimpleDateFormat(format2);
		try {
			return df.parse(time).getTime();
		} catch (ParseException pe) {
			return 0;
		}
	}
	
	/** 用于日期格式装换 */
	public final static String convertL2S1(Calendar cal){
		SimpleDateFormat df = new SimpleDateFormat(format1);
		return df.format(cal.getTime());
	}
	
	/** 用于日期格式装换 */
	public final static String convertL2S2(Calendar cal){
		SimpleDateFormat df = new SimpleDateFormat(format2);
		return df.format(cal.getTime());
	}
	
	/** 用于日期格式转换 */
	public final static long convertS3Date(String time){
		if(time == null) return 0;
		SimpleDateFormat df = new SimpleDateFormat(format3);
		try {
			return df.parse(time).getTime();
		} catch (ParseException pe) {
			return 0;
		}
	}
	
	/** 以日期格式命名文件 */
	public final static String convertS6Date(Date time){
		SimpleDateFormat df = new SimpleDateFormat(format6);
		return df.format(time);
	}
	
	/** 将日期类型转换为长整型 */
	public final static long converS6TDate(String time){
		if(time == null) return 0;
		SimpleDateFormat df = new SimpleDateFormat(format6);
		try {
			return df.parse(time).getTime();
		} catch (ParseException pe) {
			return 0;
		}
	}
	
	/** 得到当前的日期到月 */
	public final static String convertS4Date(){
		SimpleDateFormat df = new SimpleDateFormat(format4);
		return df.format(Calendar.getInstance().getTime());
	}
	
	/** 得到当前的日期到月 */
	public final static String convertS5Date(){
		SimpleDateFormat df = new SimpleDateFormat(format5);
		return df.format(Calendar.getInstance().getTime());
	}
	
	/** 得到日期时间 */
	public final static String converS5Date(long time){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat(format5);
		return df.format(cal.getTime());
	}

    public static String getYesterday(){
    	//系统昨天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,-1);
    	SimpleDateFormat df=new SimpleDateFormat(format5);    	
    	return df.format(calendar.getTime()).toString();
    }
    
    public static String getToday(){
    	//系统今天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,0);
    	SimpleDateFormat df=new SimpleDateFormat(format5);    	
    	return df.format(calendar.getTime()).toString();
    }
    
    public static String getTime(){
    	//系统今天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,0);
    	SimpleDateFormat df=new SimpleDateFormat(format6);    	
    	return df.format(calendar.getTime()).toString();
    }
    
    public static String getTodayTimeSSS(){
    	//系统今天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,0);
    	SimpleDateFormat df=new SimpleDateFormat(format7);    	
    	return df.format(calendar.getTime()).toString();
    }
    
    public static String getTimeHHmmss(){
    	//系统今天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,0);
    	SimpleDateFormat df=new SimpleDateFormat(format6);    	
    	return df.format(calendar.getTime()).toString().substring(8, 14);
    }
    
    public static String getTodayFormat2(){
    	//系统今天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,0);
    	SimpleDateFormat df=new SimpleDateFormat(format2);    	
    	return df.format(calendar.getTime()).toString();
    }
    
    public static String getTodayFormat3(){
    	//系统今天日期    	
    	Calendar   calendar=Calendar.getInstance();
    	calendar.roll(Calendar.DAY_OF_YEAR,0);
    	SimpleDateFormat df=new SimpleDateFormat(format3);    	
    	return df.format(calendar.getTime()).toString();
    }
    
    /**
     * 测试时间
     */
	public static void main(String[] args) {
		String  yesterday=getYesterday();
		System.out.println("yesterday=============="+yesterday);
		String  today=getToday();
		System.out.println("today=================="+today);
		String time =getTime();
		System.out.println("today==time================"+time);
		
		String time2 =getTimeHHmmss();
		System.out.println("today==time2================"+time2);
		
		String time3 =getTodayTimeSSS();
		System.out.println("today==time3================"+time3);
		
		String time4=getTodayFormat2();
		System.out.println("today==time4================"+time4);
		

	}
	
}
