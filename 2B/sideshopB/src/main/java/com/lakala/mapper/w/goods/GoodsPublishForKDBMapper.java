package com.lakala.mapper.w.goods;

import java.util.Map;

import com.lakala.base.model.GoodsPublishForKDB;

public interface GoodsPublishForKDBMapper {
    int deleteByPrimaryKey(Integer tgoodinfoid);

    int insert(GoodsPublishForKDB record);

    int insertSelective(GoodsPublishForKDB record);

    int updateByPrimaryKeySelective(GoodsPublishForKDB record);

    int updateByPrimaryKeyWithBLOBs(GoodsPublishForKDB record);

    int updateByPrimaryKey(GoodsPublishForKDB record);

	void updateSupplierNameBySupplierId(Map map);
}