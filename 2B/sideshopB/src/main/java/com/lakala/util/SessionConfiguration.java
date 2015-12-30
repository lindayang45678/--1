package com.lakala.util;

import java.util.Properties;

public class SessionConfiguration {
	
	//每次都重新获取
	public static String getValue(String key){
		Properties result = PropertyLoader.loadProperties("default.properties");
	    return result.getProperty(key);	
	}
	
	public static void main(String[] argvs){
		System.out.println(getValue("publish_type_name_default"));
	}
}
