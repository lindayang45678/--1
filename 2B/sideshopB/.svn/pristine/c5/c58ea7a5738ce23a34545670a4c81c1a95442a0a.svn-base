package com.lakala.mapper.r.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.model.ProductDetailedInformation;

public interface TgoodskuinfoMapper {
    Tgoodskuinfo selectByPrimaryKey(Integer tgoodskuinfoid);
    
    List<Tgoodskuinfo> selectSKUByGoodsId(Integer goodsId);

    List<ProductDetailedInformation> getSoldoutList(Map<String, Object> parm);

	List<ProductDetailedInformation> getSoldoutListByPsamAndVirtualcatid(
			Map<String, Object> map);
	
	List<Tgoodskuinfo> selectShopperGoods(Map<String, Object> map);

	List<ProductDetailedInformation> getSoldListByPsamAndVirtualcatid(
			Map<String, Object> map);

	List<Integer> selectSaleGoodsByMap(HashMap<String, Object> parm);

	List<ProductDetailedInformation> getSoldoutListByPsamAndVirtualcatidByMap(
			Map<String, Object> map);
}