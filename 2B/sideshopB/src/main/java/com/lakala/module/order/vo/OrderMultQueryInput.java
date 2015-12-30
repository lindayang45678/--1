package com.lakala.module.order.vo;

import com.lakala.module.comm.ObjectInput;



/**
 * 订单查询输入
 * @author yhg
 *
 */
public class OrderMultQueryInput extends ObjectInput{
	
	private String ecnetno;  //ec网点编号
	
	private String siteno;   //网点编号
	
	private String queryordertotal;  //yes-标识查询订单总数；no-不查订单总数(传空也标识不查订单总数)

	private String ispay;   //是否支付串  149,150
	
	private String paychannel;  //支付方式串  237,240
	
	private String ispayfornodeliver;  //是否显示货到付款订单
	
	private String cancelstate;   //订单取消状态
	
	private String state;   //订单发货状态串 99,101,102

	private String source;  //订单来源  95,96,357
	
	private String torderid;  //大订单号
	
	private String torderproviderid;  //供应商订单号
	
	private String starttime;   //查询开始时间
	
	private String endtime;     //查询结束时间
	
	private String custelno;    //手机号
	
	private String is3h;        //1、362：一小时送达;363:三到五小时送达 ;364:三到五天送达;365:五天以上送达 2、is3h=363 state=102 isdelivertohome=87的情况下，标识订单需店主送货上门 ;3、该参数可用于订单管理
	
	private String ispfchannelcode;  //1、标识批发进货订单;2、不为空可传ispfchannelcode:‘true’ ;3、该参数可用于订单管理
	
	private String nopfchannelcode; //1、标识非批发进货订单或零售订单;2、不为空可传nopfchannelcode:’true’;3、该参数可用于订单管理
	
	private String isdelivertohome; //配送方式--86:快递到店; 87:快递到家 
	
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

	public String getQueryordertotal() {
		return queryordertotal;
	}

	public void setQueryordertotal(String queryordertotal) {
		this.queryordertotal = queryordertotal;
	}

	public String getIspay() {
		return ispay;
	}

	public void setIspay(String ispay) {
		this.ispay = ispay;
	}

	public String getPaychannel() {
		return paychannel;
	}

	public void setPaychannel(String paychannel) {
		this.paychannel = paychannel;
	}

	public String getIspayfornodeliver() {
		return ispayfornodeliver;
	}

	public void setIspayfornodeliver(String ispayfornodeliver) {
		this.ispayfornodeliver = ispayfornodeliver;
	}

	public String getCancelstate() {
		return cancelstate;
	}

	public void setCancelstate(String cancelstate) {
		this.cancelstate = cancelstate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getCustelno() {
		return custelno;
	}

	public void setCustelno(String custelno) {
		this.custelno = custelno;
	}

	public String getTorderid() {
		return torderid;
	}

	public void setTorderid(String torderid) {
		this.torderid = torderid;
	}

	public String getTorderproviderid() {
		return torderproviderid;
	}

	public void setTorderproviderid(String torderproviderid) {
		this.torderproviderid = torderproviderid;
	}

	public String getIs3h() {
		return is3h;
	}

	public void setIs3h(String is3h) {
		this.is3h = is3h;
	}

	public String getIspfchannelcode() {
		return ispfchannelcode;
	}

	public void setIspfchannelcode(String ispfchannelcode) {
		this.ispfchannelcode = ispfchannelcode;
	}

	public String getNopfchannelcode() {
		return nopfchannelcode;
	}

	public void setNopfchannelcode(String nopfchannelcode) {
		this.nopfchannelcode = nopfchannelcode;
	}

	public String getIsdelivertohome() {
		return isdelivertohome;
	}

	public void setIsdelivertohome(String isdelivertohome) {
		this.isdelivertohome = isdelivertohome;
	}
	
	
	
}
