package com.lakala.mapper.w.goods;

import java.util.Map;

import com.lakala.base.model.Tgoodinfo;


public interface TgoodsinfoMapper {
    int deleteByPrimaryKey(Integer tgoodinfoid);

    int insert(Tgoodinfo record);

    int insertSelective(Tgoodinfo record);
    
    int updateGoods4PublishByGoodsId(Tgoodinfo record);

    int updateByPrimaryKeySelective(Tgoodinfo record);

    int updateByPrimaryKeyWithBLOBs(Tgoodinfo record);

    int updateByPrimaryKey(Tgoodinfo record);

	void updateSupplierNameBySupplierId(Map map);
}