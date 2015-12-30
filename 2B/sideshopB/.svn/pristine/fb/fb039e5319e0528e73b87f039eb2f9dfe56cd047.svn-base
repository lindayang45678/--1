package com.lakala.mapper.r.goodspublishkdbskuo2o;

import org.apache.ibatis.annotations.Param;

import com.lakala.module.goods.vo.TgoodsPublishKdbSkuO2o;

import java.util.List;
import java.util.Map;

public interface TgoodsPublishKdbSkuO2oMapper {
    List<Map<String, Object>> getSkuStockAndSoldSkuStock(@Param("skuids") List<String> skuids);

    List<Map<String, Object>> getIsSoldOutByGoodIdList(@Param("goodidList") List<String> goodidList);

    List<Map<String, Object>> selectGoodsByO2oidsAndGoodsId(@Param("o2oIds") List<Long> o2oIds, @Param("goodsId") int goodsId);

    List<Map<String, Object>> selectSkunetListBySkuidsAndPsam(Map map);

	TgoodsPublishKdbSkuO2o selectsaleo2oinfo(int parseInt);
}