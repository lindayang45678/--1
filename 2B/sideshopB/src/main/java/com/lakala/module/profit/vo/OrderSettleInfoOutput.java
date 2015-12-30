package com.lakala.module.profit.vo;

/**
 * 收益查询数据
 * 
 * @author pengyunle
 *
 */
public class OrderSettleInfoOutput {

	/** 验证令牌 */
	protected String token;
	/** 到账金额 */
	private String settleAmount;
	/** 结算金额 */
	private String settleAmount2;
	/** 支付说明 */
	private String payRank;
	/** 支付状态 */
	private String payStatus;
	/** 支付流水号 */
	private String payId;
	/** 到账时间 */
	private String payTime;
	/** 支付失败原因 */
	private String erroLog;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getPayRank() {
		return payRank;
	}

	public void setPayRank(String payRank) {
		this.payRank = payRank;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getErroLog() {
		return erroLog;
	}

	public void setErroLog(String erroLog) {
		this.erroLog = erroLog;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getSettleAmount2() {
		return settleAmount2;
	}

	public void setSettleAmount2(String settleAmount2) {
		this.settleAmount2 = settleAmount2;
	}
}
