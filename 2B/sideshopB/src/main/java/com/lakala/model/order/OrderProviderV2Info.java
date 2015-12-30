package com.lakala.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderProviderV2Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3228315954155382516L;

	/**
	 * @author: yhg
	 * @project:sideshopB
	 * @time: 2015年3月5日 上午10:05:10
	 * @todo: TODO
	 */
	
	private String torderproviderid;

    private Integer providerid;
    
	private String providername;
	
	private String ordertime;
	
	private BigDecimal actualamount;
	
	private Integer state;
	
	private Integer source;
	
	private List<OrderItemsInfo> orderitemslist;   //该订单所属的供应商下的商品子订单列表

	public String getProvidername() {
		return providername;
	}

	public void setProvidername(String providername) {
		this.providername = providername;
	}

	public String getTorderproviderid() {
		return torderproviderid;
	}

	public void setTorderproviderid(String torderproviderid) {
		this.torderproviderid = torderproviderid;
	}

	public Integer getProviderid() {
		return providerid;
	}

	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public BigDecimal getActualamount() {
		return actualamount;
	}

	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
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

	public List<OrderItemsInfo> getOrderitemslist() {
		return orderitemslist;
	}

	public void setOrderitemslist(List<OrderItemsInfo> orderitemslist) {
		this.orderitemslist = orderitemslist;
	}
	
}
