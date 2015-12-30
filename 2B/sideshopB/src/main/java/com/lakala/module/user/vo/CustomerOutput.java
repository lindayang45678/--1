package com.lakala.module.user.vo;

import java.util.List;

import com.lakala.model.user.TShopCustomer;

public class CustomerOutput {
	
	private List<TShopCustomer> customerList;
	
	private Integer page;
	
	private Integer pageSize;
	
	private Integer count;

	public List<TShopCustomer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<TShopCustomer> customerList) {
		this.customerList = customerList;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
