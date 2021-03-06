package com.lakala.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderInfo implements Serializable{
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
	
	private Date ordertime;
	
	private BigDecimal actualamount;
	
	private BigDecimal logiscalfee;
	
	private Integer paychanel;
	
	private Integer ispay;
	
	private Integer state;
	
	private Integer source;
	
	private String cusname;
	
	private String custelno;
	
	private Integer isdelivertohome;
	
	private String addressfull;
	
	private String addressprovincename;
	
	private String addresscityname;
	
	private String addressareaname;
	
	private String paytoken;
	
	private BigDecimal favorrulemoney;
	
	private OrderDateInfoV2 orderdatequeue;
	
	private List<OrderProviderInfo> orderproviderlist;

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

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public BigDecimal getActualamount() {
		return actualamount;
	}

	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}

	public BigDecimal getLogiscalfee() {
		return logiscalfee;
	}

	public void setLogiscalfee(BigDecimal logiscalfee) {
		this.logiscalfee = logiscalfee;
	}

	public Integer getPaychanel() {
		return paychanel;
	}

	public void setPaychanel(Integer paychanel) {
		this.paychanel = paychanel;
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getCustelno() {
		return custelno;
	}

	public void setCustelno(String custelno) {
		this.custelno = custelno;
	}

	public Integer getIsdelivertohome() {
		return isdelivertohome;
	}

	public void setIsdelivertohome(Integer isdelivertohome) {
		this.isdelivertohome = isdelivertohome;
	}

	public String getAddressfull() {
		return addressfull;
	}

	public void setAddressfull(String addressfull) {
		this.addressfull = addressfull;
	}

	public String getAddressprovincename() {
		return addressprovincename;
	}

	public void setAddressprovincename(String addressprovincename) {
		this.addressprovincename = addressprovincename;
	}

	public String getAddresscityname() {
		return addresscityname;
	}

	public void setAddresscityname(String addresscityname) {
		this.addresscityname = addresscityname;
	}

	public String getAddressareaname() {
		return addressareaname;
	}

	public void setAddressareaname(String addressareaname) {
		this.addressareaname = addressareaname;
	}

	public String getPaytoken() {
		return paytoken;
	}

	public void setPaytoken(String paytoken) {
		this.paytoken = paytoken;
	}

	public BigDecimal getFavorrulemoney() {
		return favorrulemoney;
	}

	public void setFavorrulemoney(BigDecimal favorrulemoney) {
		this.favorrulemoney = favorrulemoney;
	}

	public OrderDateInfoV2 getOrderdatequeue() {
		return orderdatequeue;
	}

	public void setOrderdatequeue(OrderDateInfoV2 orderdatequeue) {
		this.orderdatequeue = orderdatequeue;
	}

	public List<OrderProviderInfo> getOrderproviderlist() {
		return orderproviderlist;
	}

	public void setOrderproviderlist(List<OrderProviderInfo> orderproviderlist) {
		this.orderproviderlist = orderproviderlist;
	}

	
}
