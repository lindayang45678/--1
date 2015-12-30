package com.lakala.module.profit.vo;

/**
 * 订单周期收益查询数据
 * 
 * @author pengyunle
 *
 */
public class OrderCycleProfitOutput {

	/** 验证令牌 */
	protected String token;
	/** 平台商品流水金额 */
	private String terraceSelfAmount;
	/** 平台商品流水单数 */
	private Long terraceSelf0rderNum;
	/** 平台商品结算金额 */
	private String terraceSettleAmount;
	/** 平台商品结算单数 */
	private Long terraceSettle0rderNum;
	/** 平台开始时间 */
	private String orderStartTime;
	/** 平台结束时间 */
	private String orderEndTime;
	/** 结算周期ID */
	private String settlementId;

	/** 自营商品流水金额 */
	private String ownSelfAmount;
	/** 自营商品流水单数 */
	private Long ownSelf0rderNum;
	/** 自营商品到账金额 */
	private String ownSettleAmount;
	/** 自营商品到账单数 */
	private Long ownSettle0rderNum;
	/** 收益时间 */
	private String earningsTime;
	
	/** 自营商品结算金额 */
	private String ownSettleAmount2;
	/** 自营商品结算单数 */
	private Long ownSettle0rderNum2;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTerraceSelfAmount() {
		return terraceSelfAmount;
	}

	public void setTerraceSelfAmount(String terraceSelfAmount) {
		this.terraceSelfAmount = terraceSelfAmount;
	}

	public Long getTerraceSelf0rderNum() {
		return terraceSelf0rderNum;
	}

	public void setTerraceSelf0rderNum(Long terraceSelf0rderNum) {
		this.terraceSelf0rderNum = terraceSelf0rderNum;
	}

	public String getTerraceSettleAmount() {
		return terraceSettleAmount;
	}

	public void setTerraceSettleAmount(String terraceSettleAmount) {
		this.terraceSettleAmount = terraceSettleAmount;
	}

	public Long getTerraceSettle0rderNum() {
		return terraceSettle0rderNum;
	}

	public void setTerraceSettle0rderNum(Long terraceSettle0rderNum) {
		this.terraceSettle0rderNum = terraceSettle0rderNum;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getOwnSelfAmount() {
		return ownSelfAmount;
	}

	public void setOwnSelfAmount(String ownSelfAmount) {
		this.ownSelfAmount = ownSelfAmount;
	}

	public Long getOwnSelf0rderNum() {
		return ownSelf0rderNum;
	}

	public void setOwnSelf0rderNum(Long ownSelf0rderNum) {
		this.ownSelf0rderNum = ownSelf0rderNum;
	}

	public String getOwnSettleAmount() {
		return ownSettleAmount;
	}

	public void setOwnSettleAmount(String ownSettleAmount) {
		this.ownSettleAmount = ownSettleAmount;
	}

	public Long getOwnSettle0rderNum() {
		return ownSettle0rderNum;
	}

	public void setOwnSettle0rderNum(Long ownSettle0rderNum) {
		this.ownSettle0rderNum = ownSettle0rderNum;
	}

	public String getEarningsTime() {
		return earningsTime;
	}

	public void setEarningsTime(String earningsTime) {
		this.earningsTime = earningsTime;
	}

	public String getOwnSettleAmount2() {
		return ownSettleAmount2;
	}

	public void setOwnSettleAmount2(String ownSettleAmount2) {
		this.ownSettleAmount2 = ownSettleAmount2;
	}

	public Long getOwnSettle0rderNum2() {
		return ownSettle0rderNum2;
	}

	public void setOwnSettle0rderNum2(Long ownSettle0rderNum2) {
		this.ownSettle0rderNum2 = ownSettle0rderNum2;
	}
}
