package com.lakala.module.order.vo;

public class QuhuoOutputChild {
	private Integer goodsid;
	private String goodsname;
	private String norms;
	private String goodsnum;
	private String goodbigpic;
	
	private Integer tgoodinfopoolid;  
	
	
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
