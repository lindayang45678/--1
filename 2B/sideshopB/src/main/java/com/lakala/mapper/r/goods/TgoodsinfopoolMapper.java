package com.lakala.mapper.r.goods;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.TgoodsinfopoolWithBLOBs;

public interface TgoodsinfopoolMapper {

    TgoodsinfopoolWithBLOBs selectByPrimaryKey(Integer tgoodinfopoolid);

    List<TgoodsinfopoolWithBLOBs> queryLastUserCate(String lastModUser);
    
    List<TgoodsinfopoolWithBLOBs> isExistedGoodsName(Map<String, Object> parm);
    
    List<TgoodsinfopoolWithBLOBs> queryPoolGoodListForSearch(Map<String, Object> parm);
    
    Integer queryPoolGoodListCountForSearch(Map<String, Object> parm);
    
    List<TgoodsinfopoolWithBLOBs> searchPoolGoodsByParm(Map<String, Object> parm);
    
    Integer searchPoolGoodsCountByParm(Map<String, Object> parm);
    
    List<TgoodsinfopoolWithBLOBs> getGoodsListByRealCat(Map<String, Object> parm);

    List<TgoodsinfopoolWithBLOBs> getGoodsListByProviderGoodsId(Map<String, Object> parmt);
}