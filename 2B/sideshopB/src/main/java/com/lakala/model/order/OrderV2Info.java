package com.lakala.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderV2Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4999016618306380980L;

	/**
	 * @author: yhg
	 * @project:sideshopB
	 * @time: 2015年3月5日 上午9:35:51
	 * @todo: TODO
	 */
	private Integer tallorderid;
	
	private String torderid;
	
	private BigDecimal actualamount;
	
	private BigDecimal favorrulemoney;
	
	private BigDecimal logiscalfee;
	
	private Integer source;
	
	private Integer ispay;
	
	private Integer paychanel;
	
	private String paytoken;
	
	private String ordertime;
	
	private List<OrderProviderV2QueryInfo> orderproviderlist;

	public Integer getTallorderid() {
		return tallorderid;
	}

	public void setTallorderid(Integer tallorderid) {
		this.tallorderid = tallorderid;
	}

	public String getTorderid() {
		return torderid;
	}

	public void setTorderid(String torderid) {
		this.torderid = torderid;
	}

	public BigDecimal getActualamount() {
		return actualamount;
	}

	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}

	public BigDecimal getFavorrulemoney() {
		return favorrulemoney;
	}

	public void setFavorrulemoney(BigDecimal favorrulemoney) {
		this.favorrulemoney = favorrulemoney;
	}

	public BigDecimal getLogiscalfee() {
		return logiscalfee;
	}

	public void setLogiscalfee(BigDecimal logiscalfee) {
		this.logiscalfee = logiscalfee;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public Integer getPaychanel() {
		return paychanel;
	}

	public void setPaychanel(Integer paychanel) {
		this.paychanel = paychanel;
	}

	public String getPaytoken() {
		return paytoken;
	}

	public void setPaytoken(String paytoken) {
		this.paytoken = paytoken;
	}

	
	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public List<OrderProviderV2QueryInfo> getOrderproviderlist() {
		return orderproviderlist;
	}

	public void setOrderproviderlist(List<OrderProviderV2QueryInfo> orderproviderlist) {
		this.orderproviderlist = orderproviderlist;
	}

	
}
