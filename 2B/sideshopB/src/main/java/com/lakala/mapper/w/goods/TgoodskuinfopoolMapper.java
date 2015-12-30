package com.lakala.mapper.w.goods;

import com.lakala.base.model.Tgoodskuinfopool;
import com.lakala.base.model.TgoodskuinfopoolWithBLOBs;

public interface TgoodskuinfopoolMapper {
    int deleteByPrimaryKey(Integer tgoodskuinfopoolid);

    int insert(TgoodskuinfopoolWithBLOBs record);

    int insertSelective(TgoodskuinfopoolWithBLOBs record);

    int updateByPrimaryKeySelective(TgoodskuinfopoolWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TgoodskuinfopoolWithBLOBs record);

    int updateByPrimaryKey(Tgoodskuinfopool record);
    
	void deleteByGoodsId(Integer tgoodinfoid);

}