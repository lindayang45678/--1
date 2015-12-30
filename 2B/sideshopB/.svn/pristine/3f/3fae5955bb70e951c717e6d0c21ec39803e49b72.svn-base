package com.lakala.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import w.com.lakala.order.util.weixin.model.Content;
import w.com.lakala.order.util.weixin.model.Message;
import w.com.lakala.order.util.weixin.model.TemplateMsg;


public class WeixinTemplateMsgUtil {
	private static final Logger log = Logger.getLogger(WeixinTemplateMsgUtil.class);

	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	
	//店主
	public static String sendWeiXinGoodDeliveringForSaler(String mobile, String title, String deliverName, String orderName, String reMark, String urlparam) throws IOException {
		String pUrl="http://10.1.80.44:8080/weixinSendMessage/send/2";
		URL urlObject = new URL(pUrl);
		HttpURLConnection urlConn = (HttpURLConnection) urlObject.openConnection();
	    urlConn.setDoInput(true);
	    urlConn.setDoOutput(true);
	    urlConn.setRequestMethod("POST");
	    urlConn.connect();
	    BufferedWriter ou = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
	    ou.write("mobile="+mobile+"&title="+title+"&deliverName="+deliverName+"&orderName="+orderName+"&reMark="+reMark+"&urlparam="+urlparam); 
	    ou.flush();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        String line;
        String lines = "";
        while ((line = reader.readLine()) != null){
            lines += line;
        }
        reader.close();
        // 断开连接
        return lines;
	}
	// 顾客
	public static String sendWeiXinGoodDeliveringForCustomer(String mobile, String title, String deliverName, String orderName, String reMark, String urlparam) throws IOException {
		String pUrl="http://10.1.80.44:8080/weixinSendMessage/send/3";
		URL urlObject = new URL(pUrl);
		HttpURLConnection urlConn = (HttpURLConnection) urlObject.openConnection();
	    urlConn.setDoInput(true);
	    urlConn.setDoOutput(true);
	    urlConn.setRequestMethod("POST");
	    urlConn.connect();
	    BufferedWriter ou = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
	    ou.write("mobile="+mobile+"&title="+title+"&deliverName="+deliverName+"&orderName="+orderName+"&reMark="+reMark+"&urlparam="+urlparam); 
	    ou.flush();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        String line;
        String lines = "";
        while ((line = reader.readLine()) != null){
            lines += line;
        }
        reader.close();
        // 断开连接
        return lines;
	}
			
	//店主微信 -- 多线程		
	public static String WeiXinGoodDeliveringForSalerCallable(final String mobile, final String title, final String deliverName, final String orderName, final String reMark, final String urlparam){
		
		String ret = "";
		if(threadPool==null){
			threadPool = Executors.newCachedThreadPool();
		}
		
        Future<String> future = threadPool.submit(new Callable<String>() {  
            public String call() throws Exception {  
            	return sendWeiXinGoodDeliveringForSaler(mobile,title,deliverName,orderName,reMark,urlparam);
            }  
        });
        //log.info("店主微信:"+future.get());
		return ret;  
	}
	
	//顾客微信 --多线程
    public static String WeiXinGoodDeliveringForCustomerCallable(final String mobile, final String title, final String deliverName, final String orderName, final String reMark, final String urlparam){
		
    	String ret = "";
    	if(threadPool==null){
			threadPool = Executors.newCachedThreadPool();
		}
    	
        Future<String> future = threadPool.submit(new Callable<String>() {  
            public String call() throws Exception {  
            	return sendWeiXinGoodDeliveringForCustomer(mobile,title,deliverName,orderName,reMark,urlparam);
            }  
        });
        //log.info("顾客微信:"+future.get());
		return ret;  
	}
	
	
	public static void main(String[] args){
		WeiXinGoodDeliveringForSalerCallable("13718806727", "水杯", "圆通", "水杯订单名称", "我是备注",null);

		
	}
}
