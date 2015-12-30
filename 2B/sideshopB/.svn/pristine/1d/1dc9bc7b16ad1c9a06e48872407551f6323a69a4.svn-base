package com.lakala;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lakala.exception.LakalaException;

/**
 * 测试下redis的连接和取值
 * @author ph.li
 *
 */
public class RedisTest{

	public static ClassPathXmlApplicationContext context = null;
	public static void main(String[] args) throws IOException, LakalaException {
		context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		context.start();
		System.out.println("--------------------");
		/*RedisServiceImpl rt = context.getBean(RedisServiceImpl.class);
		String value = rt.getRedisValueByKey("t");
		System.out.println("value:"+value);*/
	}
}
