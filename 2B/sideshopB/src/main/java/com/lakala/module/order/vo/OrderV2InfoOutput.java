package com.lakala.module.order.vo;

import java.util.List;

import com.lakala.model.order.OrderProviderV2Info;
import com.lakala.model.order.OrderV2Info;
import com.lakala.module.sreturn.model.ReturnMoble;

/**
 * 
 * @author yhg
 *
 */
public class OrderV2InfoOutput{
	
	private Integer total;
	
	/** 显示前端的大订单列表 -该列表目前主要在批发订单待付款才会出现*/
	private List<OrderV2Info> orderlist;
	
	/** 显示前端的供应商订单列表*/
	private List<OrderProviderV2Info> orderproviderlist;
	
	/** 显示前端的售后列表*/
	List<ReturnMoble> returnlist; 

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<OrderV2Info> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<OrderV2Info> orderlist) {
		this.orderlist = orderlist;
	}

	public List<OrderProviderV2Info> getOrderproviderlist() {
		return orderproviderlist;
	}

	public void setOrderproviderlist(List<OrderProviderV2Info> orderproviderlist) {
		this.orderproviderlist = orderproviderlist;
	}

	public List<ReturnMoble> getReturnlist() {
		return returnlist;
	}

	public void setReturnlist(List<ReturnMoble> returnlist) {
		this.returnlist = returnlist;
	}

	
}
