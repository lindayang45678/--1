package com.lakala.mapper.w.goods;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.GoodsPublishForKDBOfSKU;
import com.lakala.base.model.GoodsPublishForKDBOfSKU_RedisVO;

public interface GoodsPublishForKDBOfSKUMapper {
	
	public List<GoodsPublishForKDBOfSKU_RedisVO> selectBySKUIds4Redis(Map<String,Object> params);
	
    int deleteByPrimaryKey(Integer tgoodskuinfoid);

    int insert(GoodsPublishForKDBOfSKU record);

    int insertSelective(GoodsPublishForKDBOfSKU record);

    int updateByPrimaryKeySelective(GoodsPublishForKDBOfSKU record);

    int updateByPrimaryKeyWithBLOBs(GoodsPublishForKDBOfSKU record);

    int updateByPrimaryKey(GoodsPublishForKDBOfSKU record);

	public int updateByGoodsIdSelective(GoodsPublishForKDBOfSKU sku);

	public void updateSupplierNameBySupplierId(Map map);
    
}