package com.lakala.mapper.w.goods;

import com.lakala.base.model.Timages;

public interface TimagesMapper {
    int deleteByPrimaryKey(Integer timageid);

    int insert(Timages record);

    int insertSelective(Timages record);

    int updateByPrimaryKeySelective(Timages record);

    int updateByPrimaryKey(Timages record);
    
    void deleteBytargetId(Long targetId);

}