package com.lakala.mapper.w.order;

import java.util.Map;


public interface ShouhuoMapper {
	void updatestateinloginfo(Map<String,Object> map);
	void updatedzstatefromcus(Map<String,Object> map);
	void updatedzcount(Map<String,Object> map);
	void updatedzstateinlogitem(Map<String,Object> map);
}
