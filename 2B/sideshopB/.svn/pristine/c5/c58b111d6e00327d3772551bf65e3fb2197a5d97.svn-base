package com.lakala.mapper.r.profit;

import java.util.HashMap;
import java.util.List;

import com.lakala.model.profit.TearningscycleApp;
/**
 * 
 * @author zhaoqiugen
 *
 */
public interface TearningscycleAppMapper {
	TearningscycleApp selectByPrimaryKey(Integer id);

	/**
	 * 根据店主手机号查询周期收益list
	 * @param map
	 * @return
	 */
	List<TearningscycleApp> selectCycleEarnings(HashMap<String, Object> map);

	List<TearningscycleApp> selectCycleEarningsByNetNo(HashMap<String, Object> map);

	TearningscycleApp selectCycleEarningsBySettlementId(HashMap<String, Object> map);
	
	TearningscycleApp selectAddUp(HashMap<String, Object> map);
}