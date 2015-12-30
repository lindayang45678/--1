package com.lakala.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lakala.exception.LakalaException;

public class Msg2App2CUtil implements Serializable{

	
	/**
	 * @author: yhg
	 * @project:lakalaAdmin
	 * @time: 2015年7月15日 上午11:12:15
	 * @todo: TODO
	 */
	private static final long serialVersionUID = 8483974274953305919L;
	private static final String MSG2APP2C_URL = Configuration.getValue("message_push_url");	
	public static final String OPTTYPE_URL_PAID = "sendpaysucc2c";  //订单支付成功
	public static final String OPTTYPE_URL_DELIVERED = "senddelivered2c";   //已发货
	public static final String OPTTYPE_URL_SIGNED = "sendsigned2c";   //已签收
	public static final String OPTTYPE_URL_RETURNMONEY = "sendrefund2c";  //退款
	public static final String OPTTYPE_URL_RETURNORDER_CUSTOMER = "sendcancel2c";  //用户取消订单
	public static final String OPTTYPE_URL_RETURNORDER_SHOPS = "sendrefuse2c";  //商家拒绝订单
	private static final String MSG2APP2C_RETURN_TRUE = "000000";
	public static final int TIMEOUT = 3000;
	public static final Integer TORDER_SOURCE_2C = 467; 
	
	
	/**
	 * 订单的发货、签收、取消、支付、退单同步APP2C消息
	 * @param ordervalue 参数值
	 * @param paramtype  参数
	 * @return
	 * @author: yhg 
	 * @time: 2015年7月15日 上午11:34:04
	 * @todo: TODO
	 */
	
	/**
	 * 订单的发货、签收、取消、支付、退单同步APP2C消息
	 * @param dowhat     什么操作;sendpaysucc2c订单支付成功;senddelivered2c已发货;sendsigned2c已签收;sendrefund2c退款;sendcancel2c退单
	 * @param paramtype  参数 tOrderId标识店主级订单操作;orderProviderID标识供应商级订单操作;tOrderItemsId标识商品级订单操作;
	 * @param paramvalue 参数值
	 * @return
	 * @author: yhg 
	 * @time: 2015年7月15日 上午11:46:48
	 * @todo: TODO
	 */
	public static String syncmsg2app2c(String dowhat,String paramtype,String paramvalue) throws LakalaException{
			
			String ret = "000000";
			String lines = "";
			BufferedReader reader = null;
			
			try{
				if(!StringUtils.isEmpty(paramvalue)&&!StringUtils.isEmpty(paramtype)&&!StringUtils.isEmpty(dowhat)){
					URL urlObject = new URL(MSG2APP2C_URL.concat(dowhat));
					HttpURLConnection urlConn = (HttpURLConnection) urlObject.openConnection();
					urlConn.setConnectTimeout(TIMEOUT);
				    urlConn.setDoInput(true);
				    urlConn.setDoOutput(true);
				    urlConn.setRequestMethod("POST");
				    urlConn.connect();
				    BufferedWriter ou = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
				    ou.write(""+paramtype+"="+paramvalue);
				    ou.flush();
				    reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));//设置编码,否则中文乱码
			        String line;
			        while ((line = reader.readLine()) != null){
			            lines += line;
			        }
			        JSONObject data= JSONObject.parseObject(lines);
			        ret = String.valueOf(data.get("_ReturnCode"));
			        if(!StringUtils.isEmpty(ret)&&!MSG2APP2C_RETURN_TRUE.equals(ret)){
			        	throw new Exception("消息同步至APP2C失败,返回值:"+String.valueOf(data.get("_ReturnMsg")));
			        }
				}else{
					ret = "-71";
				}
			}catch(Exception e){
				ret = "-72";
				System.out.println("--------------->>>>App2B工程消息同步至APP2C失败(方法:"+dowhat+";参数:"+paramtype+";+参数值:"+paramvalue+"),异常信息如下:"+e.getMessage());
			}finally{
				// 断开连接
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					ret = "-72";
					System.out.println("--------------->>>>App2B工程消息同步至APP2C失败(方法:"+dowhat+";参数:"+paramtype+";+参数值:"+paramvalue+"),异常信息如下:"+e.getMessage());
				}
			}
			
			return ret;
		}
	
	public static void main(String args[]) throws Exception{
		String paramtype = "tOrderId";//tOrderId或orderProviderID或tOrderItemsId
		String paramvalue = "150715104853165RBU90";
		//sendpaysucc2c订单支付成功
		System.out.println("1、订单支付成功,返回值:"+syncmsg2app2c(OPTTYPE_URL_PAID,paramtype,paramvalue));
		
		//senddelivered2c已发货
		System.out.println("2、已发货,返回值:"+syncmsg2app2c(OPTTYPE_URL_DELIVERED,paramtype,paramvalue));
		
		//sendsigned2c已签收
		System.out.println("3、已签收,返回值:"+syncmsg2app2c(OPTTYPE_URL_SIGNED,paramtype,paramvalue));
		
		//sendrefund2c退款
		System.out.println("4、退款,返回值:"+syncmsg2app2c(OPTTYPE_URL_RETURNMONEY,paramtype,paramvalue));
		
		//sendcancel2c用户取消
		System.out.println("5、用户取消,返回值:"+syncmsg2app2c(OPTTYPE_URL_RETURNORDER_CUSTOMER,paramtype,paramvalue));
		
		//sendcancel2csendrefuse2c
		System.out.println("6、商家拒绝,返回值:"+syncmsg2app2c(OPTTYPE_URL_RETURNORDER_CUSTOMER,paramtype,paramvalue));
		
	}
}
