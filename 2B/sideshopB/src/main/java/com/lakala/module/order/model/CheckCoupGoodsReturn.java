package com.lakala.module.order.model;

import java.io.Serializable;

public class CheckCoupGoodsReturn implements Serializable {
	
	private static final long serialVersionUID = 8491280952365576838L;
	
	private String skuo2oid;
	
	private String skuid;   //商品skuID 
	
	private String goodsname;// 商品名称
	
	private String goodssaleprice;//商品售价
	
	private String goodscount;//商品数量
	
	private String channel;//渠道 
	
	private String isgift;//是否赠品0 -不是，1-是
	
	private String parentid;//赠品关联的商品的skuid 
	
	private String goodtotalprice;//商品小计，（售价*数量）
	
	private String goodsactualprice;//商品实际支付金额

	public String getSkuo2oid() {
		return skuo2oid;
	}

	public void setSkuo2oid(String skuo2oid) {
		this.skuo2oid = skuo2oid;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	

	public String getGoodssaleprice() {
		return goodssaleprice;
	}

	public void setGoodssaleprice(String goodssaleprice) {
		this.goodssaleprice = goodssaleprice;
	}

	public String getGoodscount() {
		return goodscount;
	}

	public void setGoodscount(String goodscount) {
		this.goodscount = goodscount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIsgift() {
		return isgift;
	}

	public void setIsgift(String isgift) {
		this.isgift = isgift;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	

	public String getGoodtotalprice() {
		return goodtotalprice;
	}

	public void setGoodtotalprice(String goodtotalprice) {
		this.goodtotalprice = goodtotalprice;
	}

	public String getGoodsactualprice() {
		return goodsactualprice;
	}

	public void setGoodsactualprice(String goodsactualprice) {
		this.goodsactualprice = goodsactualprice;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
