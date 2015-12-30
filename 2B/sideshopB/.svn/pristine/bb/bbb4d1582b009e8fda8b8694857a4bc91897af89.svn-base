package com.lakala.util;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;

public class Configuration {
	private static Map  map;
	static {
		reloadProperties();
	}
	
	public static String getValue(String key){
	   return (String)map.get(key);	
	}
	
	public static void reloadProperties(){
		try{
			map=new HashMap(13);
			Properties result = PropertyLoader.loadProperties("default.properties");
 			map.putAll(result);
		}
		catch(Exception e)
		{}
	}
	
}
