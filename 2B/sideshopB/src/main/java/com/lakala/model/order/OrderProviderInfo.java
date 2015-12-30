package com.lakala.model.order;

import java.io.Serializable;
import java.util.List;

public class OrderProviderInfo implements Serializable{
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

    private String orderid;

    private Integer providerid;
    
	private String providername;
	
	private String clientservicetel;
	
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

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getProviderid() {
		return providerid;
	}

	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}

	public List<OrderItemsInfo> getOrderitemslist() {
		return orderitemslist;
	}

	public void setOrderitemslist(List<OrderItemsInfo> orderitemslist) {
		this.orderitemslist = orderitemslist;
	}

	public String getClientservicetel() {
		return clientservicetel;
	}

	public void setClientservicetel(String clientservicetel) {
		this.clientservicetel = clientservicetel;
	}
}
