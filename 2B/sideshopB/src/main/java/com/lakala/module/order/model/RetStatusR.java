package com.lakala.module.order.model;

import java.io.Serializable;

public class RetStatusR implements Serializable  {

	private static final long serialVersionUID = 8491280952365576838L;

	private String retCode;
	
	private String errMsg;
	
	private int skuid;
	
	private String goodsname;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getSkuid() {
		return skuid;
	}

	public void setSkuid(int skuid) {
		this.skuid = skuid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	
}
