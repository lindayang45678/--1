package com.lakala.module.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 消息通知
 * @author zjj
 * @date 2015年2月27日
 */
public class SideShopPoolMessageListener implements MessageListener{
	
	private static Log log = LogFactory.getLog(SideShopPoolMessageListener.class);

	@Override
	public void onMessage(Message message) {
		
		if(message instanceof TextMessage){
			
			TextMessage tm = (TextMessage) message;
			try {
				System.out.println(tm.getText());
			} catch (JMSException e) {
				log.error("消费者接受消息异常！",e);
			}
		}
	}

}
