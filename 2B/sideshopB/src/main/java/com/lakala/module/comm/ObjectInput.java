package com.lakala.module.comm;
/**
 * 输入父类
 * @author ph.li
 *
 */
public abstract class ObjectInput {

	/** 令牌值 */
	protected String token;
	/** 手机号 */
	protected String mobile;
	
	/**分页参数  第N页，从1开始*/
	private Integer page;
	
	/**每页数据行数*/
	private Integer pageSize;
	
	/*
	 * 登陆设备号
	 */
	private String deviceno;
	
	
	
	
	public String getDeviceno() {
		return deviceno;
	}
	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
