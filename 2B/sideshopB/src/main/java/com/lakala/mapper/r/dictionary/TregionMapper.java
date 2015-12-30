package com.lakala.mapper.r.dictionary;

import java.util.List;

import com.lakala.base.model.Tregion;

public interface TregionMapper {

    Tregion selectByPrimaryKey(Integer id);
    
    /**
     * 查询市区下的所有片区 zhiziwei
     */
    List<Tregion> selectSsByRsCode(String code);
}