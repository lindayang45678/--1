package com.lakala.mapper.r.order;

import java.util.List;
import java.util.Map;

import com.lakala.model.QuhuoOutputSql;
import com.lakala.module.order.vo.QuhuoInput;

public interface QuhuoMapper {
	public List<QuhuoOutputSql> getQuhuoInfo(QuhuoInput qi);
	public int getItemidCountByOrderid(Map<String,Object> map);
}
