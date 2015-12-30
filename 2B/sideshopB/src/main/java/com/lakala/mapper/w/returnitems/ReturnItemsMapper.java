package com.lakala.mapper.w.returnitems;

import com.lakala.base.model.ReturnItems;

/**
 * Created by HOT.LIU on 2015/3/3.
 */
public interface ReturnItemsMapper {

    /**
     * Insert selective.
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(ReturnItems record);

    int updateByPrimaryKeySelective(ReturnItems record);
}