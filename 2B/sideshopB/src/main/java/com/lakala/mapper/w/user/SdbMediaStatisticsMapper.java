package com.lakala.mapper.w.user;

import java.util.Map;

import com.lakala.base.model.SDBMediaStatistics;

/**
 * Created by HOT.LIU on 2015/3/2.
 */
public interface SdbMediaStatisticsMapper {
    Map<String, Object> selectSdbMediaStatisticsByPsam(String psam);
    
	SDBMediaStatistics selectByPrimaryKey(Long id);
	
	SDBMediaStatistics selectByPsam(String psam);
}
