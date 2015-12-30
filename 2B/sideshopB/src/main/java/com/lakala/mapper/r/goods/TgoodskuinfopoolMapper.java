package com.lakala.mapper.r.goods;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.TgoodskuinfopoolWithBLOBs;

public interface TgoodskuinfopoolMapper {
	
    TgoodskuinfopoolWithBLOBs selectByPrimaryKey(Integer tgoodskuinfopoolid);
    
    List<TgoodskuinfopoolWithBLOBs> selectGoodSkuInfoByGoodId(Integer tgoodinfopoolid);
    
    TgoodskuinfopoolWithBLOBs selectSkuByPoolGoodsId(Map<String, Object> parm);
    
	List<TgoodskuinfopoolWithBLOBs> getRecommendGoods(Map<String, Object> map);
}