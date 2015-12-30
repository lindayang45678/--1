package com.lakala.module.profit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lakala.exception.LakalaException;
import com.lakala.mapper.r.profit.TorderMapper;
import com.lakala.mapper.r.profit.TorderdetailMapper;
import com.lakala.mapper.r.profit.TorderitemsMapper;
import com.lakala.mapper.r.profit.TorderproviderMapper;
import com.lakala.mapper.r.profit.TrechargeMapper;
import com.lakala.model.profit.TearningscycleApp;
import com.lakala.model.profit.TearningsdayApp;
import com.lakala.model.profit.Torder;
import com.lakala.model.profit.Torderdetail;
import com.lakala.model.profit.Trecharge;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.service.GoodsService;
import com.lakala.module.profit.controller.ProfitConstant;
import com.lakala.module.profit.service.ProfitService;
import com.lakala.module.profit.vo.Oditem;
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
import com.lakala.util.DateUtils;
import com.lakala.util.NumberUtils;
import com.lakala.util.ReturnMsg;

/**
 * 用户收益接口实现
 * 
 * @author pengyunle
 *
 */
@Service("ProfitServiceBean")
public class ProfitServiceImpl implements ProfitService {
	/** 日志 */
	protected static final Logger log = Logger.getLogger(ProfitServiceImpl.class);

	@Autowired
	private com.lakala.mapper.r.profit.TearningscycleAppMapper earningscycleAppMapper_R;

	@Autowired
	private com.lakala.mapper.r.profit.TearningsdayAppMapper earningsdayAppMapper_R;

	@Autowired
	private com.lakala.mapper.r.profit.TorderearningsProviderMapper orderearningsProviderMapper_R;

	@Autowired
	private TorderMapper orderMapper_r;

	@Autowired
	private TorderitemsMapper orderitemsMapper_r;

	@Autowired
	private TorderproviderMapper orderproviderMapper_r;

	@Autowired
	private TrechargeMapper rechargeMapper_r;

	@Autowired
	private TorderdetailMapper orderdetailMapper_r;

	@Autowired
	private GoodsService goodsServiceImpl;

	private final String ZERO = "0.00";

	private final String START_DATE = "2015-07-01";

	/**
	 * 用户进入到收益首页查询业务
	 * 
	 * @param profit
	 * @see ProfitHomeInput
	 * @return @see ObjectOutput
	 * @throws LakalaException
	 */
	public ObjectOutput<ProfitHomeOutput> earningsTotal(ProfitHomeInput profit) {
		ObjectOutput<ProfitHomeOutput> data = new ObjectOutput<ProfitHomeOutput>();

		// 传参check
		if (profit == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 继续判断里面的必须参数
		if (profit.getEcNetNo() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("网点编号为空!");
			return data;
		}
		ProfitHomeOutput info = new ProfitHomeOutput();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 设定电商网点编号
		map.put("ecNetNo", profit.getEcNetNo());
		map.put("executetimeStart", DateUtils.dateFormatYMD.format(calendar.getTime()) + " 00:00:00");
		map.put("executetimeEnd", DateUtils.dateFormatYMD.format(calendar.getTime()) + " 23:59:59");
		map.put("earningsType", ProfitConstant.EARNINGS_TYPE_TERRACE);
		
		
		// 自营·························
		// 自营今日流水
		Torderdetail todayDuePay = orderdetailMapper_r.selectSumDuePay(map);
		if (todayDuePay == null)
			todayDuePay = new Torderdetail();
		info.setOwnTodayEarnings(NumberUtils.decimalTwoPoint(todayDuePay.getFlowAmount()));
		info.setOwnTodayOrderNum(todayDuePay.getOwnOrderNum());
		// 自营今日结算
		info.setOwnSettleTodayAmont(NumberUtils.decimalTwoPoint(todayDuePay.getDuepay()));
		info.setOwnSettleTodayNum(todayDuePay.getOwnOrderNum());
		// 自营今日到账
		Torderdetail todayAmount = orderdetailMapper_r.selectSumDuePayPaid(map);
		if (todayAmount == null)
			todayAmount = new Torderdetail();
		info.setOwnSettleAmont(NumberUtils.decimalTwoPoint(todayAmount.getDuepay()));
		info.setOwnSettleNum(NumberUtils.IsNullToZero(todayAmount.getOwnSettleNum()));
		info.setOwnSettleDate(DateUtils.formatTimeToYMD(todayAmount.getPaytime()));

		// 自营累计流水（从2015-04-01 开始）
		map.put("executetimeStart", START_DATE + " 00:00:00");
		Torderdetail aggregateDuePay = orderdetailMapper_r.selectSumDuePay(map);
		if (aggregateDuePay == null)
			aggregateDuePay = new Torderdetail();
		info.setOwnAddEarnings(NumberUtils.decimalTwoPoint(aggregateDuePay.getFlowAmount()));
		info.setOwnAddOrderNum(aggregateDuePay.getOwnOrderNum());
		// 自营累计结算
		info.setOwnSettleAddUpAmont(NumberUtils.decimalTwoPoint(aggregateDuePay.getDuepay()));
		info.setOwnSettleAddUpNum(aggregateDuePay.getOwnOrderNum());

		// 自营累计到账
		TearningsdayApp settleAmountBean = earningsdayAppMapper_R.selectAddUp(map);
		if (settleAmountBean == null) {
			settleAmountBean = new TearningsdayApp();
			settleAmountBean.setSettleAmount(BigDecimal.valueOf(0));
			settleAmountBean.setSelf0rdernum(0L);
		}
		info.setOwnTotalSettleAmont(NumberUtils.decimalTwoPoint(NumberUtils.IsNullToZero(settleAmountBean.getSettleAmount()).add(NumberUtils.IsNullToZero(todayAmount.getDuepay()))));
		info.setOwnTotalSettleNum(NumberUtils.IsNullToZero(settleAmountBean.getSettle0rderNum())+NumberUtils.IsNullToZero(todayAmount.getOwnSettleNum()));

		// 平台·························
		Map<String, Object> mapCycleSettlemen = new HashMap<String, Object>();
		mapCycleSettlemen = getCycleSettlementId(calendar.getTime());
		HashMap<String, Object> mapTerrace = new HashMap<String, Object>();
		// 设定电商网点编号
		mapTerrace.put("ecNetNo", profit.getEcNetNo());
		mapTerrace.put("executetimeStart", DateUtils.dateFormatYMD.format(mapCycleSettlemen.get("startTime")) + " 00:00:00");
		mapTerrace.put("executetimeEnd", DateUtils.dateFormatYMD.format(mapCycleSettlemen.get("endTime")) + " 23:59:59");
		mapTerrace.put("earningsType", ProfitConstant.EARNINGS_TYPE_TERRACE);
		// 本月分润
		Torderdetail aggregateDuePays = orderdetailMapper_r.selectSumEarnings(mapTerrace);
		if (aggregateDuePays == null)
			aggregateDuePays = new Torderdetail();
		info.setTerraceEarnings(NumberUtils.decimalTwoPoint(aggregateDuePays.getNetprofit()));
		info.setTerraceOrderNum(NumberUtils.IsNullToZero(aggregateDuePays.getTerraceOrderNum()));
		// 本月到账
		mapTerrace.put("isSettle", 79);
		Torderdetail settleDuePays = orderdetailMapper_r.selectSumEarnings(mapTerrace);
		info.setTerraceSettleAmont(NumberUtils.decimalTwoPoint(settleDuePays.getNetprofit()));
		info.setTerraceSettleNum(NumberUtils.IsNullToZero(settleDuePays.getTerraceOrderNum()));
		info.setTerraceSettleDateS(DateUtils.dateFormatYMD.format(mapCycleSettlemen.get("startTime")));
		info.setTerraceSettleDateE(DateUtils.dateFormatYMD.format(mapCycleSettlemen.get("endTime")));

		// 平台累计到账
		TearningscycleApp settleAmountBean2 = earningscycleAppMapper_R.selectAddUp(map);
		if (settleAmountBean2 == null) {
			settleAmountBean2 = new TearningscycleApp();
		}
		info.setTerraceTotalSettleAmont(NumberUtils.decimalTwoPoint(settleAmountBean2.getSettleAmount()));

		// 累计流水（从2015-04-01 开始）
		mapTerrace.put("mapTerrace", ProfitConstant.EARNINGS_TYPE_TERRACE);
		Torderdetail aggregateEarnings = orderdetailMapper_r.selectSumEarnings(map);
		if (aggregateEarnings == null)
			aggregateEarnings = new Torderdetail();
		info.setTerraceAddEarnings(NumberUtils.decimalTwoPoint(aggregateEarnings.getNetprofit()));
		info.setTerraceAddOrderNum(NumberUtils.IsNullToZero(aggregateEarnings.getTerraceOrderNum()));
		// info.setToken(TokenUtil.getToken(profit.getMobile()));
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(info);
		return data;
	}

	/**
	 * 订单收益查询
	 * 
	 * @param odEarnings
	 * @return
	 */
	@Override
	public ObjectOutput<List<OrderEarningsOutput>> queryEarningsOrder(OrderEarningsInput odEarnings) {
		ObjectOutput<List<OrderEarningsOutput>> data = new ObjectOutput<List<OrderEarningsOutput>>();
		// 传参check
		if (odEarnings == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 继续判断里面的必须参数
		if (odEarnings.getEcNetNo() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("网点编号为空!");
			return data;
		}
		if (odEarnings.getEarningsType() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("收益类型为空!");
			return data;
		}
		if (odEarnings.getPage() == null || odEarnings.getPageSize() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("分页参数为空!");
			return data;
		}
		List<OrderEarningsOutput> dataList = new ArrayList<OrderEarningsOutput>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderProviderId", odEarnings.getOrderProviderId());
		paramMap.put("executetimeStart", odEarnings.getStartTime() + " 00:00:00");
		paramMap.put("executetimeEnd", odEarnings.getEndTime() + " 23:59:59");
		paramMap.put("orderSource", odEarnings.getOrderSource());
		paramMap.put("isAfterSale", odEarnings.getIsAfterSale());
		paramMap.put("earningsType", odEarnings.getEarningsType());
		paramMap.put("tel", odEarnings.getMobile());
		paramMap.put("pageStart", odEarnings.getPageSize() * (odEarnings.getPage() - 1));
		paramMap.put("pageSize", odEarnings.getPageSize());
		paramMap.put("ecNetNo", odEarnings.getEcNetNo());
		paramMap.put("isSettle", odEarnings.getIsSettle());
		OrderEarningsOutput info = null;
		List<Torderdetail> orderList = orderdetailMapper_r.queryEarningsOrder(paramMap);
		for (Torderdetail bean : orderList) {
			info = new OrderEarningsOutput();
			// 订单号
			info.setProviderOrderId(bean.getOrderproviderid());
			// 支付方式
			info.setPayMode(bean.getPayMode());
			// 自营结算金额
			info.setSettlementAmount(NumberUtils.decimalTwoPoint(bean.getDuepay()));
			// 售后状态
			info.setAfterState(ProfitConstant.getAfterState(bean.getOrderproperty()));
			// 订单流水金额（龙飞确认）
			info.setOrderAmount(NumberUtils.decimalTwoPoint(bean.getFlowAmount()));
			// 平台商品收益
			info.setEarningsAmount(NumberUtils.decimalTwoPoint(bean.getNetprofit()));
			info.setSettlementAmount2(NumberUtils.decimalTwoPoint(bean.getDuepay()));
			info.setCouponValue(NumberUtils.decimalTwoPoint(bean.getGoodsfavorulemoney()));
			info.setOrderTime(DateUtils.formatTimeToYMDHMS(bean.getOrdertime()));
			// 收益开始时间
			info.setStartTime(odEarnings.getStartTime());
			// 收益结束时间
			info.setEndTime(odEarnings.getEndTime());
			// 收益时间（正向订单）
			if (1 != bean.getOrderdetailtype()) {
				info.setEarningsDate(DateUtils.formatTimeToYMD(bean.getReceivetime()));
			}
			// 收益时间（退货订单）
			else {
				info.setEarningsDate(DateUtils.formatTimeToYMD(bean.getRefundtime()));
			}
			info.setReturnFlg(bean.getOrderdetailtype());
			// 结算流水ID
			info.setPayId(bean.getShoppersettlementid());
			// TokenUtil.getToken(odEarnings.getMobile());
			dataList.add(info);
		}
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(dataList);
		return data;
	}

	/**
	 * 自营商品结算信息取得
	 * 
	 * @param osEarnings
	 * @return
	 */
	@Override
	public ObjectOutput<List<OrderSettleInfoOutput>> queryOwnSettleInfo(OrderSettleInfoInput osEarnings) {
		ObjectOutput<List<OrderSettleInfoOutput>> data = new ObjectOutput<List<OrderSettleInfoOutput>>();
		// 传参check
		if (osEarnings == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 继续判断里面的必须参数
		if (osEarnings.getEcNetNo() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("网点编号为空!");
			return data;
		}
		if (osEarnings.getEarningsType() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("收益类型为空!");
			return data;
		}
		List<OrderSettleInfoOutput> dataList = new ArrayList<OrderSettleInfoOutput>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("executetimeStart", osEarnings.getStartTime() + " 00:00:00");
		paramMap.put("executetimeEnd", osEarnings.getEndTime() + " 23:59:59");
		paramMap.put("earningsType", osEarnings.getEarningsType());
		paramMap.put("ecNetNo", osEarnings.getEcNetNo());
		OrderSettleInfoOutput info = new OrderSettleInfoOutput();
		if (ProfitConstant.EARNINGS_TYPE_SELF.equals(osEarnings.getEarningsType())) {
			List<Trecharge> orderList = rechargeMapper_r.selectDuePayList(paramMap);
			if (orderList == null || orderList.size() == 0) {
				data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				data.set_ReturnMsg("结算信息为空");
				return data;
			}
			for (Trecharge bean : orderList) {
				info = new OrderSettleInfoOutput();
				info.setSettleAmount(NumberUtils.decimalTwoPoint(bean.getDuepay()));
				info.setPayId(bean.getPayno());
				info.setPayTime(DateUtils.formatTimeToYMDHMS(bean.getPaytime()));
				info.setPayStatus(String.valueOf(bean.getStatus()));
				info.setErroLog(bean.getFailedreason());
				info.setPayRank("自营商品货款结算");
				dataList.add(info);
			}
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnData(dataList);
		} else {
			List<Trecharge> orderList = rechargeMapper_r.selectDuePayProfitList(paramMap);
			if (orderList == null || orderList.size() == 0) {
				data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				data.set_ReturnMsg("结算信息为空");
				return data;
			}
			for (Trecharge bean : orderList) {
				info = new OrderSettleInfoOutput();
				info.setSettleAmount(NumberUtils.decimalTwoPoint(bean.getDuepay()));
				info.setPayId(bean.getPayno());
				info.setPayStatus(String.valueOf(bean.getStatus()));
				info.setErroLog(bean.getFailedreason());
				info.setPayTime(DateUtils.formatTimeToYMDHMS(bean.getPaytime()));
				info.setPayRank("平台商品分润结算");
				dataList.add(info);
			}
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			data.set_ReturnData(dataList);
		}
		return data;
	}

	/**
	 * 查询周期收益list
	 * 
	 * @param ocProfit
	 * @see OrderCycleProfitInput
	 * @return @see ObjectOutput
	 * @throws ParseException
	 * @throws LakalaException
	 */
	public ObjectOutput<List<OrderCycleProfitOutput>> getCycleEarningsList(OrderCycleProfitInput ocProfit) throws ParseException {
		ObjectOutput<List<OrderCycleProfitOutput>> data = new ObjectOutput<List<OrderCycleProfitOutput>>();
		// 传参check
		if (ocProfit == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 继续判断里面的必须参数
		if (ocProfit.getEcNetNo() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("网点编号为空!");
			return data;
		}
		if (ocProfit.getEarningsType() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("收益类型为空!");
			return data;
		}
		if (ocProfit.getPage() == null || ocProfit.getPageSize() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("分页参数为空!");
			return data;
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		// 设定电商网点编号
		map.put("ecNetNo", ocProfit.getEcNetNo());

		// 返回结果list
		List<OrderCycleProfitOutput> returnList = new ArrayList<OrderCycleProfitOutput>();
		OrderCycleProfitOutput info = null;
		// 自营收益
		if (ProfitConstant.EARNINGS_TYPE_SELF.equals(ocProfit.getEarningsType())) {
			
			// 默认设为显示3个月内收益，如果改动前台传值
			if(ocProfit.getPageSize()==null || ocProfit.getPageSize()==0){
				ocProfit.setPageSize(3);
			}
			Map<String, Map<String, Object>> settlementIdMap = getOwnSelfSettlementIds(new Date(), ocProfit);
			List<String> settlementIds = new ArrayList<String>();
			for (Entry<String, Map<String, Object>> entry : settlementIdMap.entrySet()) {
				settlementIds.add(entry.getKey());
			}
			map.put("settlementIds", settlementIds);
			Map<String, OrderCycleProfitOutput> resuMapMap = new HashMap<String, OrderCycleProfitOutput>();
			List<TearningsdayApp> monthData = earningsdayAppMapper_R.selectMonthData(map);
			for (Entry<String, Map<String, Object>> entry : settlementIdMap.entrySet()) {
				// 判断周期是否有记录
				boolean hasFlg = false;
				for (TearningsdayApp bean : monthData) {
				    if(!bean.getSettlementid().equals(entry.getKey())){
				    	continue;
				    }
					if (!resuMapMap.containsKey(bean.getSettlementid())) {
						info = new OrderCycleProfitOutput();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(DateUtils.formatStrToYMD(bean.getSettlementid() + "-01"));
						calendar.set(Calendar.DAY_OF_MONTH, 1);
						info.setOrderStartTime(DateUtils.formatTimeToYMD(calendar.getTime()));
						calendar.add(Calendar.MONTH, 1);
						calendar.set(Calendar.DAY_OF_MONTH, 0);
						info.setOrderEndTime(DateUtils.formatTimeToYMD(calendar.getTime()));
						if (bean.getIssettlement() == 78) {
							info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(bean.getFlowAmount()));
							info.setOwnSelf0rderNum(bean.getSelf0rdernum());
							info.setOwnSettleAmount2(NumberUtils.decimalTwoPoint(bean.getSelfamount()));
							info.setOwnSettle0rderNum2(bean.getSelf0rdernum());
						} else {
							info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(bean.getSettleAmount()));
							info.setOwnSettle0rderNum(bean.getSettle0rderNum());
						}
						hasFlg = true;
						resuMapMap.put(bean.getSettlementid(), info);
					} else {
						info = resuMapMap.get(bean.getSettlementid());
						if (bean.getIssettlement() == 78) {
							info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(bean.getFlowAmount()));
							info.setOwnSelf0rderNum(bean.getSelf0rdernum());
							info.setOwnSettleAmount2(NumberUtils.decimalTwoPoint(bean.getSelfamount()));
							info.setOwnSettle0rderNum2(bean.getSelf0rdernum());
						} else {
							info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(bean.getSettleAmount()));
							info.setOwnSettle0rderNum(bean.getSettle0rderNum());
						}
					}
				}
				if (!hasFlg) {
					crateNullOwnSelfData(resuMapMap, entry);
				}
			}
			Calendar calendar = Calendar.getInstance();
			for (Entry<String, OrderCycleProfitOutput> entry : resuMapMap.entrySet()) {
				info = entry.getValue();
				if (info.getOwnSelfAmount() == null) {
					info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(null));
				}
				if (info.getOwnSettleAmount() == null) {
					info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(null));
				}
				if (info.getOwnSettleAmount2() == null) {
					info.setOwnSettleAmount2(NumberUtils.decimalTwoPoint(null));
				}
				if (info.getOwnSelf0rderNum() == null) {
					info.setOwnSelf0rderNum(0L);
				}
				if (info.getOwnSettle0rderNum() == null) {
					info.setOwnSettle0rderNum(0L);
				}
				if (info.getOwnSettle0rderNum2() == null) {
					info.setOwnSettle0rderNum2(0L);
				}
				if (DateUtils.formatTimeToYM(calendar.getTime()).equals(entry.getKey())) {
					OrderCycleProfitOutput infoToday = getOwnSelfToydayData(ocProfit);
					info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(new BigDecimal(info.getOwnSelfAmount()).add(new BigDecimal(infoToday
							.getOwnSelfAmount()))));
					info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(new BigDecimal(info.getOwnSettleAmount()).add(new BigDecimal(
							infoToday.getOwnSettleAmount()))));
					info.setOwnSettleAmount2(NumberUtils.decimalTwoPoint(new BigDecimal(info.getOwnSettleAmount2()).add(new BigDecimal(
							infoToday.getOwnSettleAmount2()))));
					info.setOwnSelf0rderNum(info.getOwnSelf0rderNum() + infoToday.getOwnSelf0rderNum());
					info.setOwnSettle0rderNum(info.getOwnSettle0rderNum() + infoToday.getOwnSettle0rderNum());
					info.setOwnSettle0rderNum2(info.getOwnSettle0rderNum2() + infoToday.getOwnSettle0rderNum2());
				}
				returnList.add(info);
			}
			Collections.sort(returnList, new Comparator<OrderCycleProfitOutput>() {
				public int compare(OrderCycleProfitOutput o1, OrderCycleProfitOutput o2) {
					// return (o2.getValue() - o1.getValue());
					return (o2.getOrderStartTime()).toString().compareTo(o1.getOrderStartTime());
				}
			});
		}
		// 平台收益
		else {
			Map<String, Map<String, Object>> settlementIdMap = getCycleSettlementIds(new Date(), ocProfit);
			List<String> settlementIds = new ArrayList<String>();
			for (Entry<String, Map<String, Object>> entry : settlementIdMap.entrySet()) {
				settlementIds.add(entry.getKey());
			}
			map.put("settlementIds", settlementIds);
			List<TearningscycleApp> terraceDataList = earningscycleAppMapper_R.selectCycleEarningsByNetNo(map);

			Map<String, OrderCycleProfitOutput> resuMapMap = new HashMap<String, OrderCycleProfitOutput>();
			for (Entry<String, Map<String, Object>> entry : settlementIdMap.entrySet()) {
				// 判断周期是否有记录
				boolean hasFlg = false;
				for (TearningscycleApp bean : terraceDataList) {
					if (entry.getKey().equals(bean.getSettlementid())) {
						if (!resuMapMap.containsKey(bean.getSettlementid())) {
							info = new OrderCycleProfitOutput();
							info.setSettlementId(bean.getSettlementid());
							info.setOrderEndTime(DateUtils.formatTimeToYMD((Date) entry.getValue().get("endTime")));
							info.setOrderStartTime(DateUtils.formatTimeToYMD((Date) entry.getValue().get("startTime")));
							info.setTerraceSelf0rderNum(NumberUtils.IsNullToZero(bean.getTerrace0rdernum()));
							info.setTerraceSelfAmount(NumberUtils.decimalTwoPoint(bean.getTerraceamount()));
							info.setTerraceSettle0rderNum(NumberUtils.IsNullToZero(bean.getSettle0rderNum()));
							info.setTerraceSettleAmount(NumberUtils.decimalTwoPoint(bean.getSettleAmount()));
						} else {
							info = resuMapMap.get(bean.getSettlementid());
							if (bean.getIssettlement() == 78) {
								info.setTerraceSelf0rderNum(NumberUtils.IsNullToZero(bean.getTerrace0rdernum()));
								info.setTerraceSelfAmount(NumberUtils.decimalTwoPoint(bean.getTerraceamount()));
							} else {
								info.setTerraceSettle0rderNum(NumberUtils.IsNullToZero(bean.getSettle0rderNum()));
								info.setTerraceSettleAmount(NumberUtils.decimalTwoPoint(bean.getSettleAmount()));
							}
						}
						hasFlg = true;
						resuMapMap.put(bean.getSettlementid(), info);
					}
					if (!hasFlg) {
						crateNullCycleData(resuMapMap, entry);
					}
				}
			}
			for (Entry<String, Map<String, Object>> entry : settlementIdMap.entrySet()) {
				if (terraceDataList == null || terraceDataList.size() == 0) {
					crateNullCycleData(resuMapMap, entry);
				}
			}
			for (Entry<String, OrderCycleProfitOutput> entry : resuMapMap.entrySet()) {
				returnList.add(entry.getValue());
			}
			Collections.sort(returnList, new Comparator<OrderCycleProfitOutput>() {
				public int compare(OrderCycleProfitOutput o1, OrderCycleProfitOutput o2) {
					// return (o2.getValue() - o1.getValue());
					return (o2.getSettlementId()).toString().compareTo(o1.getSettlementId());
				}
			});
		}
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(returnList);
		return data;
	}

	private void crateNullOwnSelfData(Map<String, OrderCycleProfitOutput> resuMapMap, Entry<String, Map<String, Object>> entry)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.formatStrToYMD(entry.getKey() + "-01"));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		OrderCycleProfitOutput info = new OrderCycleProfitOutput();
		info.setOrderStartTime(DateUtils.formatTimeToYMD(calendar.getTime()));
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		info.setOrderEndTime(DateUtils.formatTimeToYMD(calendar.getTime()));
		info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(BigDecimal.valueOf(0)));
		info.setOwnSelf0rderNum(0L);
		info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(BigDecimal.valueOf(0)));
		info.setOwnSettle0rderNum(0L);
		resuMapMap.put(entry.getKey(), info);
	}

	/**
	 * 查询自营日收益list
	 * 
	 * @param ocProfit
	 * @see OrderCycleProfitInput
	 * @return @see ObjectOutput
	 * @throws ParseException
	 * @throws LakalaException
	 */
	public ObjectOutput<List<OrderCycleProfitOutput>> getCycleOwnDateEarningsList(OrderCycleProfitInput ocProfit) throws ParseException {
		ObjectOutput<List<OrderCycleProfitOutput>> data = new ObjectOutput<List<OrderCycleProfitOutput>>();
		// 传参check
		if (ocProfit == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		// 继续判断里面的必须参数
		if (ocProfit.getEcNetNo() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("网点编号为空!");
			return data;
		}
		if (ocProfit.getEarningsType() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("收益类型为空!");
			return data;
		}
		if (ocProfit.getPage() == null || ocProfit.getPageSize() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("分页参数为空!");
			return data;
		}
		// 返回结果list
		List<OrderCycleProfitOutput> returnList = new ArrayList<OrderCycleProfitOutput>();
		OrderCycleProfitOutput info = null;
		HashMap<String, Object> map;
		// 包含今天数据时 取得今天数据
		boolean isHasToday = !DateUtils.formatTimeToYMDDate(Calendar.getInstance().getTime()).before(
				DateUtils.formatStrToYMD(ocProfit.getStartTime()))
				&& !DateUtils.formatTimeToYMDDate(Calendar.getInstance().getTime()).after(DateUtils.formatStrToYMD(ocProfit.getEndTime()));
		if (isHasToday) {
			info = getOwnSelfToydayData(ocProfit);
			returnList.add(info);
		}
		map = new HashMap<String, Object>();
		// 设定电商网点编号
		map.put("ecNetNo", ocProfit.getEcNetNo());

		map.put("executetimeStart", ocProfit.getStartTime() + " 00:00:00");
		map.put("executetimeEnd", ocProfit.getEndTime() + " 23:59:59");
		List<TearningsdayApp> ownDataList = earningsdayAppMapper_R.selectByNetNoList(map);
		Map<String, OrderCycleProfitOutput> dataMap = new HashMap<String, OrderCycleProfitOutput>();
		for (TearningsdayApp own : ownDataList) {
			if (dataMap.containsKey(DateUtils.dateFormatYMD.format(own.getEarningsdate()))) {
				info = dataMap.get(DateUtils.dateFormatYMD.format(own.getEarningsdate()));
			} else {
				info = new OrderCycleProfitOutput();
				info.setEarningsTime(DateUtils.formatTimeToYMD(own.getEarningsdate()));
			}
			// 自营流水
			if (own.getIssettlement() == 78) {
				info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(own.getFlowAmount()));
				info.setOwnSelf0rderNum(own.getSelf0rdernum());
				info.setOwnSettleAmount2(NumberUtils.decimalTwoPoint(own.getSelfamount()));
				info.setOwnSettle0rderNum2(own.getSelf0rdernum());
			}
			// 自营账期(已结算)
			else {
				info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(own.getSettleAmount()));
				info.setOwnSettle0rderNum(own.getSettle0rderNum());
			}
			dataMap.put(DateUtils.dateFormatYMD.format(own.getEarningsdate()), info);
		}
		Calendar calendar2 = Calendar.getInstance();
		if (calendar2.getTime().after(DateUtils.formatStrToYMDHMS(ocProfit.getEndTime() + " 23:59:59"))) {
			calendar2.setTime(DateUtils.dateFormatYMD.parse(ocProfit.getEndTime()));
		}
		int monthFlg = calendar2.get(Calendar.MONTH);
		if (isHasToday) {
			calendar2.add(Calendar.DAY_OF_MONTH, -1);
		}
		for (int i = 1; i <= 31; i++) {
			if (calendar2.get(Calendar.MONTH) != monthFlg) {
				break;
			}
			info = new OrderCycleProfitOutput();
			if (dataMap.containsKey(DateUtils.formatTimeToYMD(calendar2.getTime()))) {
				info = dataMap.get(DateUtils.formatTimeToYMD(calendar2.getTime()));
			} else {
				info.setEarningsTime(DateUtils.formatTimeToYMD(calendar2.getTime()));
			}
			if (info.getOwnSelf0rderNum() == null) {
				info.setOwnSelf0rderNum(0L);
			}
			if (info.getOwnSelfAmount() == null) {
				info.setOwnSelfAmount(ZERO);
			}
			if (info.getOwnSettle0rderNum() == null) {
				info.setOwnSettle0rderNum(0L);
			}
			if (info.getOwnSettleAmount() == null) {
				info.setOwnSettleAmount(ZERO);
			}
			if (info.getOwnSettle0rderNum2() == null) {
				info.setOwnSettle0rderNum2(0L);
			}
			if (info.getOwnSettleAmount2() == null) {
				info.setOwnSettleAmount2(ZERO);
			}
			returnList.add(info);
			if (!calendar2.getTime().after(DateUtils.formatStrToYMDHMS(ocProfit.getStartTime() + " 23:59:59"))) {
				break;
			}
			calendar2.add(Calendar.DATE, -1);
		}
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(returnList);
		return data;
	}

	private OrderCycleProfitOutput getOwnSelfToydayData(OrderCycleProfitInput ocProfit) {
		OrderCycleProfitOutput info;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 设定电商网点编号
		map.put("ecNetNo", ocProfit.getEcNetNo());
		map.put("executetimeStart", DateUtils.dateFormatYMD.format(calendar.getTime()) + " 00:00:00");
		map.put("executetimeEnd", DateUtils.dateFormatYMD.format(calendar.getTime()) + " 23:59:59");
		map.put("earningsType", ProfitConstant.EARNINGS_TYPE_TERRACE);
		// 今日流水
		Torderdetail todayDuePay = orderdetailMapper_r.selectSumDuePay(map);
		if (todayDuePay == null)
			todayDuePay = new Torderdetail();
		info = new OrderCycleProfitOutput();
		info.setOwnSelfAmount(NumberUtils.decimalTwoPoint(todayDuePay.getFlowAmount()));
		info.setOwnSelf0rderNum(todayDuePay.getOwnOrderNum());
		info.setOwnSettleAmount2(NumberUtils.decimalTwoPoint(todayDuePay.getDuepay()));
		info.setOwnSettle0rderNum2(todayDuePay.getOwnOrderNum());
		// 今日到账
		Torderdetail todayAmount = orderdetailMapper_r.selectSumDuePayPaid(map);
		if (todayAmount == null)
			todayAmount = new Torderdetail();
		info.setOwnSettleAmount(NumberUtils.decimalTwoPoint(todayAmount.getDuepay()));
		info.setOwnSettle0rderNum(NumberUtils.IsNullToZero(todayAmount.getOwnSettleNum()));
		info.setEarningsTime(DateUtils.formatTimeToYMD(calendar.getTime()));
		return info;
	}

	/**
	 * 显示周期没有记录做空数据
	 * 
	 * @param returnList
	 * @param entry
	 */
	private void crateNullCycleData(Map<String, OrderCycleProfitOutput> map, Entry<String, Map<String, Object>> entry) {
		OrderCycleProfitOutput info = new OrderCycleProfitOutput();
		info.setSettlementId(entry.getKey());
		info.setOrderEndTime(DateUtils.formatTimeToYMD((Date) entry.getValue().get("endTime")));
		info.setOrderStartTime(DateUtils.formatTimeToYMD((Date) entry.getValue().get("startTime")));
		info.setTerraceSelf0rderNum(0L);
		info.setTerraceSelfAmount(ZERO);
		info.setTerraceSettle0rderNum(0L);
		info.setTerraceSettleAmount(ZERO);
		map.put(entry.getKey(), info);
	}

	/**
	 * 订单详情页查询业务
	 * 
	 * @param opDetail
	 * @see OrderEarningsDetailInput
	 * @return @see ObjectOutput
	 * @throws ParseException
	 * @throws LakalaException
	 */
	public ObjectOutput<OrderEarningsDetailOutput> getOrderDetail(OrderEarningsDetailInput opDetail) {
		ObjectOutput<OrderEarningsDetailOutput> data = new ObjectOutput<OrderEarningsDetailOutput>();
		if (opDetail == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		if (opDetail.getOrderProviderId() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("订单号为空!");
			return data;
		}
		if (opDetail.getReturnFlg() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("是否售后为空!");
			return data;
		}
		if (opDetail.getEarningsType() == null) {
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("收益类型为空!");
			return data;
		}
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("executetimeStart", opDetail.getEarningsDate() + " 00:00:00");
		paramMap.put("executetimeEnd", opDetail.getEarningsDate() + " 23:59:59");
		paramMap.put("orderProviderId", opDetail.getOrderProviderId());
		paramMap.put("earningsType", opDetail.getEarningsType());
		paramMap.put("returnFlg", opDetail.getReturnFlg());
		paramMap.put("payId", opDetail.getPayId());

		// 查询主订单二级订单详细
		Torder order = orderMapper_r.selectByOrderProviderId(paramMap);
		// 查询三级订单
		List<Torderdetail> orderitemsList = orderdetailMapper_r.selectByOrderProviderId(paramMap);

		// 结果设定
		OrderEarningsDetailOutput od = new OrderEarningsDetailOutput();
		od.setProviderOrderId(opDetail.getOrderProviderId());
		od.setOrderDate(DateUtils.formatTimeToYMDHMS(order.getOrdertime()));
		od.setCusName(order.getCusname());
		od.setCusTelNo(order.getCustelno());
		od.setIsDeliverTohome(order.getIsdelivertohomeStr());
		od.setAddr(order.getAddressfull());
		od.setSource(ProfitConstant.getSource(order.getSource()));
		od.setReturnFlg(opDetail.getReturnFlg());
		od.setEarningsType(opDetail.getEarningsType());
		od.setPayMode(order.getPayChanelStr());
		// od.setToken(TokenUtil.getToken(opDetail.getMobile()));

		// 平台分润（结算金额）
		BigDecimal terraceGoodsProfit = BigDecimal.valueOf(0);
		// 自营货款（到账金额）
		BigDecimal ownGoodsAccounts = BigDecimal.valueOf(0);
		// 自营货款（结算金额）
		BigDecimal ownGoodsAccounts2 = BigDecimal.valueOf(0);
		// 订单金额
		BigDecimal orderProviderAmount = BigDecimal.valueOf(0);
		// 订单流水金额
		BigDecimal orderFlowAmount = BigDecimal.valueOf(0);
		// 支付手续费率
		BigDecimal payRate = BigDecimal.valueOf(0);
		// 支付手续费
		BigDecimal payRateAmount = BigDecimal.valueOf(0);
		// 运费
		BigDecimal freight = BigDecimal.valueOf(0);
		// 优惠金额
		BigDecimal goodsFavoRuleMoney = BigDecimal.valueOf(0);
		// 店主结算ID
		String payId = null;
		List<Oditem> oditemList = new ArrayList<Oditem>();
		for (Torderdetail items : orderitemsList) {
			Oditem oditem = new Oditem();
			oditem.setGoodCount(items.getGoodscount());
			oditem.setGoodName(items.getGoodsname());
			oditem.setGoodSalePrice(items.getGoodssaleprice());
			oditem.setOrderState(items.getStateStr());
			oditem.setReceiveDate(DateUtils.formatTimeToYMDHMS2(items.getReceivetime()));
			oditem.setInvaliState(items.getInvalidStateStr());
			oditem.setReturnDate(DateUtils.formatTimeToYMDHMS2(items.getReturntime()));
			oditem.setRefundTime(DateUtils.formatTimeToYMDHMS2(items.getRefundtime()));
			oditem.setOrderPrice(NumberUtils.IsNullToZero(items.getGoodsfinalprice()));
			oditem.setDuyPay(NumberUtils.IsNullToZero(items.getDuepay()));
			terraceGoodsProfit = terraceGoodsProfit.add(NumberUtils.IsNullToZero(items.getNetprofit()));
			oditem.setEarningsAmount(NumberUtils.IsNullToZero(items.getNetprofit()));
			//参数直降活动标示：0-未参与，1-参与
			oditem.setDirectflag(items.getDirectflag());
			oditemList.add(oditem);
			if (payRate == BigDecimal.valueOf(0)) {
				payRate = NumberUtils.IsNullToZero(items.getPayrate());
			}
			payRateAmount = payRateAmount.add(NumberUtils.IsNullToZero(items.getPayrateamount()));
			ownGoodsAccounts = ownGoodsAccounts.add(NumberUtils.IsNullToZero(items.getDuepay()));
			ownGoodsAccounts2=ownGoodsAccounts2.add(NumberUtils.IsNullToZero(items.getDuepay()));
			orderProviderAmount = orderProviderAmount.add(NumberUtils.IsNullToZero(items.getGoodsfinalprice()));
			goodsFavoRuleMoney = goodsFavoRuleMoney.add(NumberUtils.IsNullToZero(items.getGoodsfavorulemoney()));
			orderFlowAmount=orderFlowAmount.add(NumberUtils.IsNullToZero(items.getFlowAmount()));
			freight = freight.add(NumberUtils.IsNullToZero(items.getLogisticsfee()));
			if (payId == null) {
				payId = items.getShoppersettlementid();
			}
			// 商品图片
			oditem.setGoodPicture(items.getGoodBigPic());
			// 商品规格
			String goodSpecification = goodsServiceImpl.transeSkuToDisc(items.getSkuFrontDisNameStr(), items.getSkuIdStr(),
					items.gettRealCateId());
			oditem.setGoodSpecification(goodSpecification);
			oditem.setGoodId(String.valueOf(items.getGoodsid()));
			if (items.gettGoodInfoPoolId() == null || items.gettGoodInfoPoolId() <= 0) {
				oditem.setTgoodinfopoolid(null);
			} else {
				oditem.setTgoodinfopoolid(String.valueOf(items.gettGoodInfoPoolId()));
			}
		}
		od.setOditmList(oditemList);
		od.setPayRate(NumberUtils.decimalTwoPoint(payRate));
		od.setPayRateAmount(NumberUtils.decimalTwoPoint(payRateAmount));
		od.setTerraceGoodsProfit(NumberUtils.decimalTwoPoint(terraceGoodsProfit));
		od.setOwnGoodsAccounts(NumberUtils.decimalTwoPoint(ownGoodsAccounts));
		od.setFreight(NumberUtils.decimalTwoPoint(freight));
		od.setDuyPay(NumberUtils.decimalTwoPoint(ownGoodsAccounts));
		od.setOrderProviderAmount(NumberUtils.decimalTwoPoint(orderProviderAmount));
		od.setShopCouponValue("0.00");
		od.setCouponValue(NumberUtils.decimalTwoPoint(goodsFavoRuleMoney));
		od.setOrderAmount(NumberUtils.decimalTwoPoint(orderFlowAmount));
		od.setSettleAmount2(NumberUtils.decimalTwoPoint(ownGoodsAccounts2));
		if (ProfitConstant.EARNINGS_TYPE_SELF.equals(opDetail.getEarningsType())) {
			// 结算信息取得
			Trecharge recharge = rechargeMapper_r.selectByPayId(payId);
			if (recharge != null) {
				od.setSettleAmount(NumberUtils.decimalTwoPoint(recharge.getDuepay()));
				od.setPayId(recharge.getPayno());
				od.setErroLog(recharge.getFailedreason() == null ? "" : recharge.getFailedreason());
				od.setPayTime(DateUtils.formatTimeToYMDHMS(recharge.getPaytime()));
				od.setPayStatus(String.valueOf(recharge.getStatus()));
			}
			od.setPayRank("自营商品货款结算");
		} else {
			// 结算信息取得
			Trecharge recharge = rechargeMapper_r.selectProfitByPayId(payId);
			if (recharge != null) {
				od.setSettleAmount(NumberUtils.decimalTwoPoint(recharge.getDuepay()));
				od.setPayId(recharge.getPayno());
				od.setErroLog(recharge.getFailedreason());
				od.setPayTime(DateUtils.formatTimeToYMDHMS(recharge.getPaytime()));
				od.setPayStatus(String.valueOf(recharge.getStatus()));
			}
			od.setPayRank("自营商品货款结算");
		}
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(od);
		return data;
	}

	/**
	 * 2014.10.21~2014.11.20 startTime :2014.10.20 middleTime :2014.11.21
	 * endTime:2014.12.21 周期批次：为2014102120141120 2014.11.21~2014.12.20
	 * startTime:2014.10.20 middleTime :2014.11.21 endTime:2014.12.21
	 * 周期批次：为2014112120141220
	 * 
	 * @param executeTime
	 * @param sfymd
	 * @return
	 */

	private Map<String, Object> getCycleSettlementId(Date executeTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		String cycleSettlementId = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(executeTime);
		calendar.set(Calendar.DAY_OF_MONTH, 20);
		Date middleTime = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 21);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, 20);
		Date endTime = calendar.getTime();
		if ((executeTime.before(middleTime) || executeTime.equals(middleTime))
				&& (executeTime.after(startTime) || executeTime.equals(startTime))) {
			// 本次处理 月批次ID
			cycleSettlementId = DateUtils.dateFormatYMD2.format(startTime) + DateUtils.dateFormatYMD2.format(middleTime);
			map.put("startTime", startTime);
			map.put("endTime", middleTime);
		} else if ((executeTime.before(endTime) || executeTime.equals(endTime))
				&& (executeTime.after(middleTime) || executeTime.equals(middleTime))) {
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 21);
			cycleSettlementId = DateUtils.dateFormatYMD2.format(calendar.getTime()) + DateUtils.dateFormatYMD2.format(endTime);
			map.put("startTime", calendar.getTime());
			map.put("endTime", endTime);
		}
		map.put("settlementId", cycleSettlementId);
		return map;
	}

	/**
	 * 取得结算周期list
	 * 
	 * @param executeTime
	 * @return
	 */
	private Map<String, Map<String, Object>> getCycleSettlementIds(Date executeTime, OrderCycleProfitInput ocProfit) {
		Map<String, Map<String, Object>> settlementIdMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.add(Calendar.MONTH, 0);
		if (ocProfit.getPage() != 1) {
			calendar.add(Calendar.MONTH, -ocProfit.getPage() * ocProfit.getPageSize());
		}
		for (int i = 1; i <= ocProfit.getPageSize(); i++) {
			map = getCycleSettlementId(calendar.getTime());
			settlementIdMap.put((String) map.get("settlementId"), map);
			calendar.add(Calendar.MONTH, -1);
		}
		return settlementIdMap;
	}

	private Map<String, Object> getOwnSelfSettlementId(Date executeTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(executeTime);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		Date endTime = calendar.getTime();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("settlementId", DateUtils.formatTimeToYM(startTime));
		return map;
	}

	/**
	 * 取得结算周期list
	 * 
	 * @param executeTime
	 * @return
	 */
	private Map<String, Map<String, Object>> getOwnSelfSettlementIds(Date executeTime, OrderCycleProfitInput ocProfit) {
		Map<String, Map<String, Object>> settlementIdMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		if (ocProfit.getPage() != 1) {
			calendar.add(Calendar.MONTH, -ocProfit.getPage() * ocProfit.getPageSize());
		}
		for (int i = 1; i <= ocProfit.getPageSize(); i++) {
			map = getOwnSelfSettlementId(calendar.getTime());
			settlementIdMap.put((String) map.get("settlementId"), map);
			calendar.add(Calendar.MONTH, -1);
			try {
				if (calendar.getTime().before(DateUtils.formatStrToYMD(START_DATE))) {
                      break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return settlementIdMap;
	}

}
