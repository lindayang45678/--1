package com.lakala;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lakala.exception.LakalaException;
import com.lakala.model.Torder;
import com.lakala.module.order.service.OrderService;

public class ServiceTest {

	public static ClassPathXmlApplicationContext context = null;
	public static void main(String[] args) throws IOException, LakalaException {
		context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		context.start();
		System.out.println("--------------------");
		OrderService os = (OrderService)context.getBean("OrderServiceBean");
		Torder order = os.loadOrderByKey("15012011493149123737");
		System.out.println(order);
		if(order != null){
			System.out.println("allid:"+order.getTorderid());
		}
		
	}
}
