package com.lakala.module.profit.service;

import java.text.ParseException;
import java.util.List;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.profit.vo.OrderCycleProfitOutput;
import com.lakala.module.profit.vo.OrderCycleProfitInput;
import com.lakala.module.profit.vo.OrderEarningsDetailOutput;
import com.lakala.module.profit.vo.OrderEarningsOutput;
import com.lakala.module.profit.vo.OrderEarningsInput;
import com.lakala.module.profit.vo.OrderEarningsDetailInput;
import com.lakala.module.profit.vo.OrderSettleInfoInput;
import com.lakala.module.profit.vo.OrderSettleInfoOutput;
import com.lakala.module.profit.vo.ProfitHomeOutput;
import com.lakala.module.profit.vo.ProfitHomeInput;

/**
 * 用户收益接口
 * 
 * @author pengyunle
 *
 */
public interface ProfitService {

	/**
	 * 收益首页数据取得
	 * 
	 * @param profit
	 * @see ProfitHomeInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<ProfitHomeOutput> earningsTotal(ProfitHomeInput profit);

	/**
	 * 根据店主手机号查询周期收益list
	 * 
	 * @param ocProfit
	 * @see OrderCycleProfitInput
	 * @return @see ObjectOutput
	 * @throws ParseException
	 * @throws LakalaException
	 */
	public ObjectOutput<List<OrderCycleProfitOutput>> getCycleEarningsList(OrderCycleProfitInput ocProfit) throws ParseException;

	/**
	 * 订单详情数据取得
	 * 
	 * @param opDetail
	 * @see OrderEarningsDetailInput
	 * @return @see ObjectOutput
	 * @throws ParseException
	 * @throws LakalaException
	 */
	public ObjectOutput<OrderEarningsDetailOutput> getOrderDetail(OrderEarningsDetailInput opDetail);

	/**
	 * 订单收益查询
	 * 
	 * @param odEarnings
	 * @return
	 */
	public ObjectOutput<List<OrderEarningsOutput>> queryEarningsOrder(OrderEarningsInput odEarnings);

	/**
	 * 自营结算信息取得
	 * 
	 * @param osEarnings
	 * @return
	 */
	ObjectOutput<List<OrderSettleInfoOutput>> queryOwnSettleInfo(OrderSettleInfoInput osEarnings);

	public ObjectOutput<List<OrderCycleProfitOutput>> getCycleOwnDateEarningsList(OrderCycleProfitInput ocProfit) throws ParseException;

}
