package com.lakala.mapper.r.goods;

import java.util.List;

import com.lakala.base.model.Tpropertyvalue;

public interface TpropertyvalueMapper {
    Tpropertyvalue selectByPrimaryKey(Integer tpropertyvalueid);
    
    List<Tpropertyvalue> queryByPropertyId(Integer tpropertyid);

	List<Tpropertyvalue> queryByPropertyIds(List<Integer> keyIds);
}