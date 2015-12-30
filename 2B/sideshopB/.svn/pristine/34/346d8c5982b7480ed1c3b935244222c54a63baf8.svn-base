package com.lakala.mapper.w.goods;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.GoodsPublishForKDBOfSKUO2O;
import com.lakala.base.model.GoodsPublishForKDBOfSKU_RedisVO;

public interface GoodsPublishForKDBOfSKUO2OMapper {
	
	public List<GoodsPublishForKDBOfSKUO2O> selectByGoodsID(Integer tgoodinfoid);
	
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsPublishForKDBOfSKUO2O record);

    int insertSelective(GoodsPublishForKDBOfSKUO2O record);

    int updateByPrimaryKeySelective(GoodsPublishForKDBOfSKUO2O record);

    int updateByPrimaryKeyWithBLOBs(GoodsPublishForKDBOfSKUO2O record);

    int updateByPrimaryKey(GoodsPublishForKDBOfSKUO2O record);

	public int updateO2OByGoodsIdSelective(GoodsPublishForKDBOfSKUO2O o2o);

	public List<GoodsPublishForKDBOfSKU_RedisVO> selectDetailByO2OIds(Map<String,Object> params);

	int updateBySKUIDSelective(GoodsPublishForKDBOfSKUO2O o2o);

}