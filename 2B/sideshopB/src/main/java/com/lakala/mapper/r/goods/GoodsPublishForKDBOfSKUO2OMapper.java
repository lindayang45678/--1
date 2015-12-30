package com.lakala.mapper.r.goods;

import java.util.List;

import com.lakala.base.model.GoodsPublishForKDBOfSKUO2O;

public interface GoodsPublishForKDBOfSKUO2OMapper {
    GoodsPublishForKDBOfSKUO2O selectByPrimaryKey(Integer id);

	public List<GoodsPublishForKDBOfSKUO2O> selectByGoodsID(Integer goodsID);
	
	public long getCountByGoodsID(Integer goodsID);
}