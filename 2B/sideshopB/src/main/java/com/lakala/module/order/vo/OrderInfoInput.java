package com.lakala.module.order.vo;

import com.lakala.module.comm.ObjectInput;



/**
 * 订单查询输入
 * @author yhg
 *
 */
public class OrderInfoInput extends ObjectInput{
	
	/**--订单查询类别
	 *
	 *     代码     :备注
		pfdfh  :待发货-用于批发订单;
		pfyfh  :已发货-用于批发订单;
		pfbfsh :部分收货-用于批发订单;
		pfysh  :已收货-用于批发订单;
		lsdfh  :待发货-用于零售订单;
		lsyfh  :已发货 -用于零售订单;
		lsyqs  :已签收 -用于零售订单;
		lssh   :售后 -用于零售订单;
		sxxdzsh:需店主送货订单-用于需店主送货; 
	--**/
	private String querytype; 
	
	private String ecnetno;  //ec网点编号
	
	private String siteno;   //网点编号
	
	private String deviceno;  //终端编号

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

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

	public String getDeviceno() {
		return deviceno;
	}

	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}

	
	
}
