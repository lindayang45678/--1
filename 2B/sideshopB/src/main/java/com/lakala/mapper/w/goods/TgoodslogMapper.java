package com.lakala.mapper.w.goods;

import com.lakala.base.model.Tgoodslog;
import com.lakala.base.model.TgoodslogWithBLOBs;

public interface TgoodslogMapper {
    int deleteByPrimaryKey(Integer tgoodslogid);

    int insert(TgoodslogWithBLOBs record);

    int insertSelective(TgoodslogWithBLOBs record);

    int updateByPrimaryKeySelective(TgoodslogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TgoodslogWithBLOBs record);

    int updateByPrimaryKey(Tgoodslog record);
}