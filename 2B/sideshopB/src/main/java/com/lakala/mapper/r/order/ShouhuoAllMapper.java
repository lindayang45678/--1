package com.lakala.mapper.r.order;


import java.util.List;
import java.util.Map;

import com.lakala.base.model.Torderitems;



public interface ShouhuoAllMapper {
	List<Torderitems> selectorderitems(Map<String,Object> map);
	List<Torderitems> selectlogids(Map<String,Object> map);
	String selectorderid(Map<String,Object> map);
}
