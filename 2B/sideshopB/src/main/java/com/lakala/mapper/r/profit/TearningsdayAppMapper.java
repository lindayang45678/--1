package com.lakala.mapper.r.profit;

import java.util.HashMap;
import java.util.List;

import com.lakala.model.profit.TearningsdayApp;

/**
 * 
 * @author zhaoqiugen
 *
 */
public interface TearningsdayAppMapper {
	TearningsdayApp selectByPrimaryKey(Integer id);

	/**
	 * 查询收益
	 * 
	 * @param map
	 */
	TearningsdayApp selectByNetNo(HashMap<String, Object> map);
	
	/**
	 * 查询 统计收益
	 * 
	 * @param map
	 */
	TearningsdayApp selectByNetNoSum(HashMap<String, Object> map);
	
	/**
	 * 查询 累计到账收益
	 * 
	 * @param map
	 */
	TearningsdayApp selectAddUp(HashMap<String, Object> map);
	
	/**
	 * 查询 统计收益集合
	 * 
	 * @param map
	 */
	List<TearningsdayApp> selectByNetNoList(HashMap<String, Object> map);
	
	/**
	 * 查询 按月统计收益集合
	 * 
	 * @param map
	 */
	List<TearningsdayApp> selectMonthData(HashMap<String, Object> map);
}