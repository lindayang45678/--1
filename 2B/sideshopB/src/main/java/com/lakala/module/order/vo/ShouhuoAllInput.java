package com.lakala.module.order.vo;

import com.lakala.module.comm.ObjectInput;
/**
 * 店主全部收货输入参数
 * @author ls
 *
 */
public class ShouhuoAllInput extends ObjectInput{
	private String orderproviderid;
	private String psam;

	public String getOrderproviderid() {
		return orderproviderid;
	}

	public void setOrderproviderid(String orderproviderid) {
		this.orderproviderid = orderproviderid;
	}

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}

	
	

}
