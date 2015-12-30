package com.lakala.module.order.vo;

import com.lakala.module.comm.ObjectInput;
import com.lakala.util.Constants;
/**
 * 顾客取货输入参数
 * @author ls
 *
 */
public class QuhuoInput extends ObjectInput{
	private String sincecode;
	private String orderitemsidflag;
	private Integer state = 102;
	private String netno;
	private Integer cancelstate = Constants.TORDER_CANCELSTATE_WQX;

	public Integer getCancelstate() {
		return cancelstate;
	}

	public void setCancelstate(Integer cancelstate) {
		this.cancelstate = cancelstate;
	}

	public String getNetno() {
		return netno;
	}

	public void setNetno(String netno) {
		this.netno = netno;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOrderitemsidflag() {
		return orderitemsidflag;
	}

	public void setOrderitemsidflag(String orderitemsidflag) {
		this.orderitemsidflag = orderitemsidflag;
	}

	public String getSincecode() {
		return sincecode;
	}

	public void setSincecode(String sincecode) {
		this.sincecode = sincecode;
	}
}
