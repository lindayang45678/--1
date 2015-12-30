package com.lakala.mapper.r.order;

import java.util.List;
import java.util.Map;

import com.lakala.model.ShouhuoOutputSql;
import com.lakala.module.order.vo.ShouhuoInput;

public interface ShouhuoMapper {
	List<ShouhuoOutputSql> getShouhuoInfo(ShouhuoInput si);
	List<ShouhuoOutputSql> getConfirmInfo(Map<String,Object> map);

	/**
	 * 收货商品发布价
	 * 
	 * @param si
	 * @return
	 */
	ShouhuoOutputSql getBreakRule(ShouhuoInput si);
}
