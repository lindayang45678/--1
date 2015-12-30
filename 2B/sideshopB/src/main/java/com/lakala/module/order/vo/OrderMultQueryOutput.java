package com.lakala.module.order.vo;

import java.util.List;

import com.lakala.model.order.OrderProviderQueryInfo;

/**
 * 
 * @author yhg
 *
 */
public class OrderMultQueryOutput{
	
	/** 订单总数 */
	private Integer total;
	
	/** 显示前端的大订单号数组*/
	private List<OrderProviderQueryInfo> orderproviderlist;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<OrderProviderQueryInfo> getOrderproviderlist() {
		return orderproviderlist;
	}

	public void setOrderproviderlist(List<OrderProviderQueryInfo> orderproviderlist) {
		this.orderproviderlist = orderproviderlist;
	}

	
}
