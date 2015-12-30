package com.lakala.module.comm;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;


/**
 * 获取短信验证码
 * @author zjj
 * @date 2014年11月10日
 */
public class SmsVerificationCodeHelper {
	
	private final static String host = "10.1.80.44";
	private final static Integer port = 8080;
	private final static String uri = "http://10.1.80.44:8080/sms/authcode";
	private static Log log = LogFactory.getLog(SmsVerificationCodeHelper.class);
	
	public static void sendVerificationCode(String phone,String content) throws HttpException, IOException {
		
		if(!StringUtils.hasText(phone)){
			log.error("手机号码不能为空!");
			return;
		}
		
		HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost(host, port, "http");

        HttpMethod method = getPostMethod(phone,content);//使用POST方式提交数据
        
        client.executeMethod(method);
       //打印服务器返回的状态
//        System.out.println(method.getStatusLine());
        //打印结果页面
//        String response =
//           new String(method.getResponseBodyAsString().getBytes("8859_1"));
       //打印返回的信息
//        System.out.println(response);
        method.releaseConnection();

	}
	
    /**
     * 使用POST方式提交数据
     * @return
     */
    private static HttpMethod getPostMethod(String phone,String content){
        PostMethod post = new PostMethod(uri);
        NameValuePair simcard = new NameValuePair("m",phone);
        NameValuePair simcard1 = new NameValuePair("c",content);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");  
        post.setRequestBody(new NameValuePair[] { simcard,simcard1});
        return post;
    }
    
    public static void main(String[] args) {
//    	String host = "10.1.80.44";
//    	int port = 8080;
//    	String uri = "http://10.1.80.44:8080/sms/authcode";
    	String content = "测试中文连接";
		try {
			sendVerificationCode("18612189254",content);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
