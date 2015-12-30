package com.lakala.mapper.w.goods;

import com.lakala.base.model.Tgoodsinfopool;
import com.lakala.base.model.TgoodsinfopoolWithBLOBs;

public interface TgoodsinfopoolMapper {
    int deleteByPrimaryKey(Integer tgoodinfopoolid);

    int insert(TgoodsinfopoolWithBLOBs record);

    int insertSelective(TgoodsinfopoolWithBLOBs record);

    int updateByPrimaryKeySelective(TgoodsinfopoolWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TgoodsinfopoolWithBLOBs record);

    int updateByPrimaryKey(Tgoodsinfopool record);

}