package com.lakala.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return null或者空返回false ; 否则返回true
	 */
	public static boolean verdict(String str){
		try{
			return !(str == null || str.trim().length() == 0 || "null".equals(str));
		}catch(NullPointerException e){
			return false;
		}
	}
	
	public static String stringDateTime(Date date){
		if(date==null){
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	public static String stringDate(Date date){
		if(date==null){
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	public static Date toDate(String time){
		if(time==null || time.trim().equals("")){
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(time);
		} catch (ParseException e) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return dateFormat.parse(time);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 判断字符串是否为数值: true 是数值; false 不是数值
	 */
	public static boolean isNumber(String s){
		try {
			new BigDecimal(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 将字符串数组，转换成字符串，元素间用"|"分隔
	 */
	public static String arrayToString(String[] s){
		String str = "";
		for (int i = 0; i < s.length; i++) {
			if(i == s.length - 1)
				str += s[i];
			else
				str += (s[i] + "|");
		}
		return str;
	}
	
	
	 /***
     * 根据传入的数组attr，取出其中的值，两两拼成一串，存入到List中
     * @param attr  传入的数组
     * @return 返回一个List，如传入的数组为{1,2,3,4,5},返回的list结构为 list.get(0)="1,2"; list.get(1)="3,4";list.get(2)="5"
     */
    public static ArrayList mergeTwoByArray(String[] attr){
    	
    	ArrayList<String> list=new ArrayList<String>();
	   //取模为0，表示一共有偶数个
	   for(int i=0;i<attr.length/2;i++){
		  list.add(attr[2*i]+":"+attr[2*i+1]);
	   }
	   
	   //取模为1，表示一共有奇数个 ,在偶数个的基础上增加最后一个数值
 	   if(attr.length%2==1){
 		  list.add(attr[attr.length-1]);
 	   }
	    	
    	return list;
    }
    
    
    
    /**
     * id串去重
     * @param ids
     * @return 返回去重后的带单引号的id串，如'2323','32323','333'
     */
    public static String getFilterSameStr(String ids){
    	String resultstr = "";
    	String[] idsattr = ids.split(",");
    	
    	//用TreeSet保证传入和转换有序
    	TreeSet<String>  set = new TreeSet<String>();
    	set = getFilterSameSet(idsattr);
    	
    	for(String setv : set){
    		resultstr = resultstr +",'"+setv+"'";
    	}
    	
    	if(resultstr.length()>1){
    		resultstr = resultstr.substring(1);
    	}
    	
    	return resultstr;
    }
    
    /**
     * id串去重
     * @param ids
     * @return 返回去重后的不带单引号的id串,如232,3343,2323
     */
    public static String getFilterSameStrNoQuote(String ids){
    	String resultstr = "";
    	String[] idsattr = ids.replace("'", "").split(",");
    	
    	//用TreeSet保证传入和转换有序
    	TreeSet<String>  set = new TreeSet<String>();
    	set = getFilterSameSet(idsattr);
    	
    	for(String setv : set){
    		resultstr = resultstr +","+setv+"";
    	}
    	
    	if(resultstr.length()>1){
    		resultstr = resultstr.substring(1);
    	}
    	
    	return resultstr;
    }
    
    
    /**
     * id串去重
     * @param ids
     * @return 返回去重后的TreeSet
     */
    public static TreeSet<String> getFilterSameSet(String[] attr){
    	
    	//用TreeSet保证传入和转换有序
    	TreeSet<String>  set = new TreeSet<String>();
    	for(String v : attr){
    		if(v.length()>0){
    		   set.add(v);	
    		}
    		
    	}
    	
    	return set;
    }
    
    /**
     * 日期对象比较大小
     * true:前面的日期晚于后面的日期，包括时间相等时返回false
     * @param d1
     * @param d2
     * @return
     */
    public static boolean compareDate(Date d1, Date d2){
    	long l1 = d1.getTime();
    	long l2 = d2.getTime();
    	return (l1-l2)>0;
    }
    
	/**
	 * 获取当前日期字符串（yyyyMMdd）
	 */
	public static String getNowDateStr(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
}
