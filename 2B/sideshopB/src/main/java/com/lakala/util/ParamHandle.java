package com.lakala.util;

import java.util.Map;
import java.util.Map.Entry;

public class ParamHandle {

	public static String toParam(Map<String, String> map){
		StringBuffer sb = new StringBuffer();
		for(Entry<String, String> entry : map.entrySet()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
}
