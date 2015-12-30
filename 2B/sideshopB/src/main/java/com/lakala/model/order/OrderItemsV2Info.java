package com.lakala.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemsV2Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7169417258517387033L;

	/**
	 * @author: yhg
	 * @project:sideshopB
	 * @time: 2015年3月5日 上午9:54:04
	 * @todo: TODO
	 */
	
	private Integer torderitemsid;
	
	private Integer goodsid;
	
	private String goodbigpic;
	
	private String goodsname;
	
    private Integer state;
    
    private Integer cancelstate;
    
    private Integer tgoodinfopoolid;

	public Integer getTorderitemsid() {
		return torderitemsid;
	}

	public void setTorderitemsid(Integer torderitemsid) {
		this.torderitemsid = torderitemsid;
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

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCancelstate() {
		return cancelstate;
	}

	public void setCancelstate(Integer cancelstate) {
		this.cancelstate = cancelstate;
	}

	public Integer getTgoodinfopoolid() {
		return tgoodinfopoolid;
	}

	public void setTgoodinfopoolid(Integer tgoodinfopoolid) {
		this.tgoodinfopoolid = tgoodinfopoolid;
	}
}
