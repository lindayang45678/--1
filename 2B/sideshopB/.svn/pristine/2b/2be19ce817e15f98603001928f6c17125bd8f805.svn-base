package com.lakala.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlDomUtil {
	
	/**
	 * 根据标签名，获取输入html的指定元素
	 */
	public static Iterator<Element> getElementsByTagName(String html, String tagName){
		//html字符串解析成Document对象
		Document doc = Jsoup.parse(html, "UTF-8");
		//获取指定的node
		Elements nodes = doc.getElementsByTag(tagName);
		return null == nodes ? null : nodes.iterator();
	}
	
	/**
	 * 获取指定的元素的指定属性值
	 */
	public static Map<String, String> getAttrValue(String html, String tagName, String attrName, String flag){
		Map<String, String> attrValue = new HashMap<String, String>();
		//获取指定的节点集合
		Iterator<Element> nodes = getElementsByTagName(html, tagName);
		//遍历节点集合
		while (null != nodes && nodes.hasNext()) {
			Element el = nodes.next();
			//当前节点的指定的属性值
			String value = el.attr(attrName);
			String sflag = el.attr(flag);
			//缓存属性值
			attrValue.put(sflag, null == value ? "" : value);
		}
		
		return attrValue;
	}
	
	/**
	 * 设置指定属性的属性值，并返回更新后的html
	 */
	
	public static String modHtml(String html, String tagName, String attrName, Map<String, String> newValue, String flag){
		List<String> attrValue = new ArrayList<String>();
		//html字符串解析成Document对象
		Document doc = Jsoup.parse(html, "UTF-8");
		//获取指定的node
		Elements nodes = doc.getElementsByTag(tagName);
		//获取指定的节点集合
		Iterator<Element> it = null != nodes ? nodes.iterator() : null;
		
		//遍历节点集合
		while (null != it && it.hasNext()) {
			Element el = it.next();
			//设置当前节点的指定的属性值
			if (null != newValue.get(el.attr(flag))) {
				el.attr(attrName, newValue.get(el.attr(flag)));
			}
		}
		
		return doc.toString();
	}
}
