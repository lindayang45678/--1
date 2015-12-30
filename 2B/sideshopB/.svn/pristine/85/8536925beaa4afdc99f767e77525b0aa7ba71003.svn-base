package com.lakala.module.profit.controller;

/**
 * 收益模块常量
 * 
 * @author zhaoqiugen
 *
 */
public class ProfitConstant {

	/** 商品自营平台标识 自营 452 */
	public static final String EARNINGS_TYPE_SELF = "452";
	/** 商品自营平台标识 平台 453 */
	public static final String EARNINGS_TYPE_TERRACE = "453";

	/** 结算标识 已结算 */
	public static final int SETTMENT_IS = 79;
	/** 结算标识 未结算 */
	public static final int SETTMENT_ISNOT = 78;

	/**
	 * 售后状态取得
	 * 
	 * @param afterState
	 * @return
	 */
	public static String getAfterState(int afterState) {
		if (120 == afterState) {
			return "已退款";
		} else {
			return "无售后";
		}
	}
	
	/**
	 * 售后状态取得
	 * 
	 * @param afterState
	 * @return
	 */
	public static String getSource(int source) {
		if (95 == source) {
			return "开店宝";
		} else if(334 == source) {
			return "收款宝";
		}else if(357 == source) {
			return "wap商城";
		}else if(467 == source) {
			return "身边小店";
		}else if(466 == source) {
			return "身边小店商户";
		}
		return "";
	}
}
