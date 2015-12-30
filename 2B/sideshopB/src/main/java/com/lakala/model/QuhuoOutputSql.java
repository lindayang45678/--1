package com.lakala.model;

import java.math.BigDecimal;

public class QuhuoOutputSql {
	private String orderproviderid;
	private String cusname;
	private String custelno;
	private Integer paychanel;//支付方式
	private BigDecimal actualamount;//待付款金额
	private Integer state;
	private Integer returnstate;
	private Integer goodsid;
	private String goodsname;
	private String norms;
	private String goodsnum;
	private String goodbigpic;
	private String providername;
	private String ordertime;
	//商品订单id,查询取货信息时用不到,在确认取货时需要查询一下
	private Integer orderitemsid;
	private String orderid;
	private Integer logid;
	private Integer allorderid;
	private Integer platorself;
	private Integer source;
	
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	private Integer tgoodinfopoolid;  
	
	
	public Integer getPlatorself() {
		return platorself;
	}
	public void setPlatorself(Integer platorself) {
		this.platorself = platorself;
	}
	public Integer getAllorderid() {
		return allorderid;
	}
	public void setAllorderid(Integer allorderid) {
		this.allorderid = allorderid;
	}
	public String getProvidername() {
		return providername;
	}
	public void setProvidername(String providername) {
		this.providername = providername;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public Integer getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodbigpic() {
		return goodbigpic;
	}
	public void setGoodbigpic(String goodbigpic) {
		this.goodbigpic = goodbigpic;
	}
	public Integer getLogid() {
		return logid;
	}
	public void setLogid(Integer logid) {
		this.logid = logid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getOrderitemsid() {
		return orderitemsid;
	}
	public void setOrderitemsid(Integer orderitemsid) {
		this.orderitemsid = orderitemsid;
	}
	public String getOrderproviderid() {
		return orderproviderid;
	}
	public void setOrderproviderid(String orderproviderid) {
		this.orderproviderid = orderproviderid;
	}
	public String getCusname() {
		return cusname;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	public String getCustelno() {
		return custelno;
	}
	public void setCustelno(String custelno) {
		this.custelno = custelno;
	}
	public Integer getPaychanel() {
		return paychanel;
	}
	public void setPaychanel(Integer paychanel) {
		this.paychanel = paychanel;
	}
	public BigDecimal getActualamount() {
		return actualamount;
	}
	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getReturnstate() {
		return returnstate;
	}
	public void setReturnstate(Integer returnstate) {
		this.returnstate = returnstate;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	public String getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(String goodsnum) {
		this.goodsnum = goodsnum;
	}
	public Integer getTgoodinfopoolid() {
		 return null != tgoodinfopoolid && tgoodinfopoolid.compareTo(0) > 0 ? tgoodinfopoolid : null;
	}
	public void setTgoodinfopoolid(Integer tgoodinfopoolid) {
		this.tgoodinfopoolid = tgoodinfopoolid;
	}
	
}
