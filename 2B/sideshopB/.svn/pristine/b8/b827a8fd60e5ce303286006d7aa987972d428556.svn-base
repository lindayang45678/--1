package com.lakala.module.news.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.module.news.vo.MemberInput;
import com.lakala.module.news.vo.NewsInput;
import com.lakala.module.news.vo.NewsTestInput;
import com.lakala.module.news.vo.OrderInput;
import com.lakala.util.Configuration;
import com.lakala.util.HttpURLConn;

@Controller
@RequestMapping("/news")
public class NewsController {
private static String MESSAGE_PUSH_URL = Configuration.getValue("message_push_url");
	
	/**
	 * 2b消息列表统计数据
	 */
	@RequestMapping("/messagestat2b")
	@ResponseBody
	public String messagestat2b(MemberInput memberInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"messagestat2b", createParams(memberInput));
	}
	/**
	 * 2b消息列表
	 */
	@RequestMapping("/messagelist2b")
	@ResponseBody
	public String messagelist2b(MemberInput memberInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"messagelist2b", createParams(memberInput));
	}
	
	/**
	 * 2b发送推送消息：订单待支付
	 */
	@RequestMapping("/sendnopay2b")
	@ResponseBody
	public String sendnopay2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendnopay2b", createParams(orderInput));
	}
	
	/**
	 * 2b发送推送消息：订单支付成功
	 */
	@RequestMapping("/sendpaysucc2b")
	@ResponseBody
	public String sendpaysucc2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendpaysucc2b", createParams(orderInput));
	}
	
	/**
	 * 2b发送推送消息：已发货
	 */
	@RequestMapping("/senddelivered2b")
	@ResponseBody
	public String senddelivered2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"senddelivered2b", createParams(orderInput));
	}

	/**
	 * 2b发送推送消息：已签收
	 */
	@RequestMapping("/sendsigned2b")
	@ResponseBody
	public String sendsigned2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendsigned2b", createParams(orderInput));
	}

	/**
	 * 2b发送推送消息：退款
	 */
	@RequestMapping("/sendrefund2b")
	@ResponseBody
	public String sendrefund2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendrefund2b", createParams(orderInput));
	}

	/**
	 * 2b发送推送消息：未支付取消
	 */
	@RequestMapping("/sendnopaycancel2b")
	@ResponseBody
	public String sendnopaycancel2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendnopaycancel2b", createParams(orderInput));
	}

	/**
	 * 2b发送推送消息：用户取消订单
	 */
	@RequestMapping("/sendcancel2b")
	@ResponseBody
	public String sendcancel2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendcancel2b", createParams(orderInput));
	}

	/**
	 * 2b发送推送消息：商家拒绝订单
	 */
	@RequestMapping("/sendrefuse2b")
	@ResponseBody
	public String sendrefuse2b(OrderInput orderInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"sendrefuse2b", createParams(orderInput));
	}
	
	/**
	 * 阅读发送的消息
	 */
	@RequestMapping("/readmessage")
	@ResponseBody
	public String readmessage(NewsInput newsInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"readmessage", createParams(newsInput));
	}
	
	@RequestMapping("/testsendmessage2b")
	@ResponseBody
	public String testsendmessage2b(NewsTestInput newsTestInput){
		return HttpURLConn.post(MESSAGE_PUSH_URL+"testsendmessage2b", createParams(newsTestInput));
	}
	
	
	//将类转成map
	private Map<String, String> createParams(Object model) {
		Map<String, String> params = new HashMap<String, String>();
		
		try{
			// 获取实体类的所有属性，返回Field数组
			Field[] field = model.getClass().getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				// 获取属性的名字
				String name = field[i].getName();
				String nameTmp = name;
				// 关键。。。可访问私有变量
				field[i].setAccessible(true);
				// 将属性的首字母大写
				nameTmp = nameTmp.replaceFirst(nameTmp.substring(0, 1), nameTmp.substring(0, 1).toUpperCase());
				// 如果type是类类型，则前面包含"class "，后面跟类名
	            Method m = model.getClass().getMethod("get" + nameTmp);
	             // 调用getter方法获取属性值
	            Object o = m.invoke(model);
	            if(o != null){
		            String value = String.valueOf(o);
		            params.put(name, value);
	            }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return params;
	}

}
