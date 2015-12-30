package com.lakala.mapper.w.logisticsinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import w.com.lakala.order.model.Tlogisticsinfo;
import w.com.lakala.order.model.TlogisticsinfoExample;

public interface TlogisticsinfoMapper {
    int countByExample(TlogisticsinfoExample example);

    int deleteByExample(TlogisticsinfoExample example);

    int deleteByPrimaryKey(Integer logisticsid);

    int insert(Tlogisticsinfo record);

    int insertSelective(Tlogisticsinfo record);

    List<Tlogisticsinfo> selectByExample(TlogisticsinfoExample example);

    Tlogisticsinfo selectByPrimaryKey(Integer logisticsid);

    int updateByExampleSelective(@Param("record") Tlogisticsinfo record, @Param("example") TlogisticsinfoExample example);

    int updateByExample(@Param("record") Tlogisticsinfo record, @Param("example") TlogisticsinfoExample example);

    int updateByPrimaryKeySelective(Tlogisticsinfo record);

    int updateByPrimaryKey(Tlogisticsinfo record);
}