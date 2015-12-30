package com.lakala.mapper.r.goods;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.Timages;

public interface TimagesMapper {
	
    Timages selectByPrimaryKey(Integer timageid);

    List<Timages> queryImgByGoodsId(Map<String, Object> parm);
}