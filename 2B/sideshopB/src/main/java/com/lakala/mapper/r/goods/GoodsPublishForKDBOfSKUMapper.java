package com.lakala.mapper.r.goods;

import java.util.List;

import com.lakala.base.model.GoodsPublishForKDBOfSKU;

public interface GoodsPublishForKDBOfSKUMapper {
	
    GoodsPublishForKDBOfSKU selectByPrimaryKey(Integer tgoodskuinfoid);
    
    List<GoodsPublishForKDBOfSKU> selectByGoodsId(Integer goodsId);
}