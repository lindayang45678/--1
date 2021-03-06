package com.lakala.model.profit;

import java.math.BigDecimal;
import java.util.Date;

public class Trecharge {
	private Integer id;

	private String payid;

	private String netpoint;

	private Long netid;

	private String shopername;

	private String shoppermobile;

	private Integer ordercount;

	private BigDecimal duepay;

	private Integer status;

	private Date createtime;

	private String failedreason;

	private String wallet;

	private Date paytime;

	private String payno;

	/** 自营今日结算订单单数 */
	private Long ownSettleNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getNetpoint() {
		return netpoint;
	}

	public void setNetpoint(String netpoint) {
		this.netpoint = netpoint;
	}

	public Long getNetid() {
		return netid;
	}

	public void setNetid(Long netid) {
		this.netid = netid;
	}

	public String getShopername() {
		return shopername;
	}

	public void setShopername(String shopername) {
		this.shopername = shopername;
	}

	public String getShoppermobile() {
		return shoppermobile;
	}

	public void setShoppermobile(String shoppermobile) {
		this.shoppermobile = shoppermobile;
	}

	public Integer getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(Integer ordercount) {
		this.ordercount = ordercount;
	}

	public BigDecimal getDuepay() {
		return duepay;
	}

	public void setDuepay(BigDecimal duepay) {
		this.duepay = duepay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getFailedreason() {
		return failedreason;
	}

	public void setFailedreason(String failedreason) {
		this.failedreason = failedreason;
	}

	public String getWallet() {
		return wallet;
	}

	public void setWallet(String wallet) {
		this.wallet = wallet;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public Long getOwnSettleNum() {
		return ownSettleNum;
	}

	public void setOwnSettleNum(Long ownSettleNum) {
		this.ownSettleNum = ownSettleNum;
	}

	public String getPayno() {
		return payno;
	}

	public void setPayno(String payno) {
		this.payno = payno;
	}

}