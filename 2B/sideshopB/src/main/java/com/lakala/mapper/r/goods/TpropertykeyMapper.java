package com.lakala.mapper.r.goods;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.Tpropertykey;

public interface TpropertykeyMapper {
	
    Tpropertykey selectByPrimaryKey(Integer tpropertykeyid);
    
    List<Tpropertykey> queryProList(Map<String, Integer> parm);
    
    Tpropertykey getProCount();
    
    List<Tpropertykey> getGoodProperty(Integer tRealCateId);
}