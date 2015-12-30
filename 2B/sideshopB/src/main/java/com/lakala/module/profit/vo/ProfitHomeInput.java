package com.lakala.module.profit.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 用户进入收益首页查询输入
 * 
 * @author zhaoqiugen
 *
 */
public class ProfitHomeInput extends ObjectInput {
	/** 电商网点编号 */
	private String ecNetNo;

	public String getEcNetNo() {
		return ecNetNo;
	}

	public void setEcNetNo(String ecNetNo) {
		this.ecNetNo = ecNetNo;
	}

}
