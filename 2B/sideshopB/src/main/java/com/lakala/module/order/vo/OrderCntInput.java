package com.lakala.module.order.vo;

import com.lakala.module.comm.ObjectInput;



/**
 * 订单查询输入
 * @author yhg
 *
 */
public class OrderCntInput extends ObjectInput{
	
	private String ecnetno;  //ec网点编号
	
	private String siteno;   //网点编号

	private String ordercate;   //订单类别 pfjh-批发进货;lsdd-零售订单;xdzsh-需店主送货
	
	private String deviceno;  //终端编号

	public String getEcnetno() {
		return ecnetno;
	}

	public void setEcnetno(String ecnetno) {
		this.ecnetno = ecnetno;
	}

	public String getSiteno() {
		return siteno;
	}

	public void setSiteno(String siteno) {
		this.siteno = siteno;
	}

	public String getOrdercate() {
		return ordercate;
	}

	public void setOrdercate(String ordercate) {
		this.ordercate = ordercate;
	}

	public String getDeviceno() {
		return deviceno;
	}

	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}
	
}
