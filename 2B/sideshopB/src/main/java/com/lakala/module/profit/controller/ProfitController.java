package com.lakala.module.profit.controller;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lakala.exception.LakalaException;
import com.lakala.model.profit.Torder;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.profit.service.ProfitService;
import com.lakala.module.profit.vo.OrderCycleProfitInput;
import com.lakala.module.profit.vo.OrderCycleProfitOutput;
import com.lakala.module.profit.vo.OrderEarningsDetailInput;
import com.lakala.module.profit.vo.OrderEarningsDetailOutput;
import com.lakala.module.profit.vo.OrderEarningsInput;
import com.lakala.module.profit.vo.OrderEarningsOutput;
import com.lakala.module.profit.vo.OrderSettleInfoInput;
import com.lakala.module.profit.vo.OrderSettleInfoOutput;
import com.lakala.module.profit.vo.ProfitHomeInput;
import com.lakala.module.profit.vo.ProfitHomeOutput;
import com.lakala.util.ReturnMsg;

/**
 * 用户收益
 * 
 * @author ZHAOQIUGEN
 *
 */
@Controller
@RequestMapping("/profit")
public class ProfitController {

	Logger logger = Logger.getLogger(ProfitController.class);

	@Autowired
	private ProfitService profitService;

	/**
	 * 收益首页查询
	 * 
	 * @param profit
	 *            进入收益首页查询输入
	 * @see ProfitHomeInput
	 * @return 收益输出
	 * @throws ParseException
	 * @see ProfitInfoOutput
	 */
	@RequestMapping(value = "/toProfitHome")
	public @ResponseBody ObjectOutput<ProfitHomeOutput> toProfitHome(ProfitHomeInput profit) {
		logger.info("参数确认：" + JSON.toJSON(profit));
		ObjectOutput<ProfitHomeOutput> info = null;
		try {
			info = profitService.earningsTotal(profit);
		} catch (Exception e) {
			logger.error(e);
			info = new ObjectOutput<ProfitHomeOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	
	/**
	 * 订单收益查询
	 * 
	 * @param odEarnings
	 * @return
	 * @throws ParseException
	 * @throws LakalaException
	 */
	@RequestMapping(value = "/queryEarningsOrder")
	public @ResponseBody ObjectOutput<List<OrderEarningsOutput>> queryEarningsOrder(OrderEarningsInput odEarnings) {
		logger.info("queryEarningsOrder参数确认：" + JSON.toJSON(odEarnings));
		ObjectOutput<List<OrderEarningsOutput>> info = null;
		try {
			info = profitService.queryEarningsOrder(odEarnings);
		} catch (Exception e) {
			logger.error(e);
			info = new ObjectOutput<List<OrderEarningsOutput>>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}


	/**
	 * 订单周期收益（累计点击）
	 * 
	 * @param ocProfit
	 *            订单周期收益查询输入
	 * @see OrderCycleProfitInput
	 * @return 订单周期收益查询输出
	 * @throws ParseException
	 * @see OrderCycleProfitOutput
	 */
	@RequestMapping(value = "/getCycleEarningsList")
	public @ResponseBody ObjectOutput<List<OrderCycleProfitOutput>> getCycleEarningsList(OrderCycleProfitInput ocProfit)
			throws ParseException, LakalaException {
		logger.info("getCycleEarningsList参数确认：" + JSON.toJSON(ocProfit));
		ObjectOutput<List<OrderCycleProfitOutput>> info = null;
		try {
			info = profitService.getCycleEarningsList(ocProfit);
		} catch (Exception e) {
			logger.error(e);
			info = new ObjectOutput<List<OrderCycleProfitOutput>>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 自营订单日收益
	 * 
	 * @param ocProfit
	 *            订单周查询输入
	 * @see OrderCycleProfitInput
	 * @return 订单收益查询输出
	 * @throws ParseException
	 * @see OrderCycleProfitOutput
	 */
	@RequestMapping(value = "/getCycleOwnDateEarningsList")
	public @ResponseBody ObjectOutput<List<OrderCycleProfitOutput>> getCycleOwnDateEarningsList(OrderCycleProfitInput ocProfit)
			throws ParseException, LakalaException {
		logger.info("getCycleEarningsList参数确认：" + JSON.toJSON(ocProfit));
		ObjectOutput<List<OrderCycleProfitOutput>> info = null;
		try {
			info = profitService.getCycleOwnDateEarningsList(ocProfit);
		} catch (Exception e) {
			logger.error(e);
			info = new ObjectOutput<List<OrderCycleProfitOutput>>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	
	/**
	 * 商品结算信息
	 * 
	 * @param ocProfit
	 *            订单周期收益查询输入
	 * @see OrderCycleProfitInput
	 * @return 订单周期收益查询输出
	 * @throws ParseException
	 * @see OrderCycleProfitOutput
	 */
	@RequestMapping(value = "/getOwnSettleInfo")
	public @ResponseBody ObjectOutput<List<OrderSettleInfoOutput>> getOwnSettleInfo(OrderSettleInfoInput osIp)
			throws ParseException, LakalaException {
		logger.info("getOwnSettleInfo参数确认：" + JSON.toJSON(osIp));
		ObjectOutput<List<OrderSettleInfoOutput>> info = null;
		try {
			info = profitService.queryOwnSettleInfo(osIp);
		} catch (Exception e) {
			logger.error(e);
			info = new ObjectOutput<List<OrderSettleInfoOutput>>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}

	/**
	 * 订单收益详情页
	 * 
	 * @param opDetail
	 *            订单收益详情页查询输入
	 * @see OrderEarningsDetailInput
	 * @return 订单收益详情页查询输出
	 * @throws ParseException
	 * @see Torder
	 */
	@RequestMapping(value = "/getOrderDetail")
	public @ResponseBody ObjectOutput<OrderEarningsDetailOutput> getOrderDetail(OrderEarningsDetailInput opDetail) {
		logger.info("getOrderDetail参数确认：" + JSON.toJSON(opDetail));
		ObjectOutput<OrderEarningsDetailOutput> info = null;
		try {
			info = profitService.getOrderDetail(opDetail);
		} catch (Exception e) {
			logger.error(e);
			info = new ObjectOutput<OrderEarningsDetailOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
}
