package com.lakala.mapper.r.sdbmediastatistics;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.SDBMediaStatistics;
import com.lakala.base.model.Tshopkeeperaddr;

/**
 * Created by HOT.LIU on 2015/3/2.
 */
public interface SdbMediaStatisticsMapper {
    Map<String, Object> selectSdbMediaStatisticsByPsam(String psam);
    
	SDBMediaStatistics selectByPrimaryKey(Long id);
	
	SDBMediaStatistics selectByPsam(String psam);
	
	SDBMediaStatistics getTermFbTypeByPSAM(String PSAM);

	SDBMediaStatistics selectSdbMediaStatisticsByAPPPsam(String psam);

	List<Tshopkeeperaddr> findShopkeeperAddrByNetNo(Map<String, Object> params);
	
	List<SDBMediaStatistics> selectByMobile(String mobile);
	List<SDBMediaStatistics> selectByNetno(String netno);
}
