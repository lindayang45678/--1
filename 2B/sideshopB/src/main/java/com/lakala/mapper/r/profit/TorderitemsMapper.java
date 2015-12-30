package com.lakala.mapper.r.profit;

import java.util.HashMap;
import java.util.List;

import com.lakala.model.profit.Torderitems;

public interface TorderitemsMapper {

	List<Torderitems> selectByOrderProviderId(HashMap<String, Object> paramMap);
	com.lakala.base.model.Torderitems selectByPrimaryKey(String torderitemsid);
}