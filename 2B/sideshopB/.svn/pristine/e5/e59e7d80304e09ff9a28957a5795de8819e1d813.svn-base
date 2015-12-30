package com.lakala.mapper.r.profit;

import java.util.List;
import java.util.Map;

import com.lakala.model.profit.Torderdetail;

public interface TorderdetailMapper {

	Torderdetail selectByPrimaryKey(Integer id);

	/**
	 * 根据时间段统计自营货款流水
	 * 
	 * @param map
	 * @return
	 */
	Torderdetail selectSumDuePay(Map<String, Object> map);

	/**
	 * 根据时间段统计自营货款到账
	 * 
	 * @param map
	 * @return
	 */
	Torderdetail selectSumDuePayPaid(Map<String, Object> map);

	/**
	 * 店主分润累计收益
	 * 
	 * @param map
	 * @return
	 */
	Torderdetail selectSumEarnings(Map<String, Object> map);
	
	/**
	 * 订单收益查询
	 * 
	 * @param map
	 * @return
	 */
	List<Torderdetail> queryEarningsOrder(Map<String, Object> map);
	
	/**
	 * 订单详细
	 * 
	 * @param map
	 * @return
	 */
	List<Torderdetail> selectByOrderProviderId(Map<String, Object> map);

}