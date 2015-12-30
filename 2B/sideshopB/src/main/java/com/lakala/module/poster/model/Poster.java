package com.lakala.module.poster.model;

import java.io.Serializable;

public class Poster implements Serializable {

    private static final long serialVersionUID = 4897173113600523228L;
	
	private String posterid;
	
	private String postername;
	
	private String posterdes;
	
	private int postertype;
	
	private String goodsid;
	
	private String url;

	public String getPosterid() {
		return posterid;
	}

	public void setPosterid(String posterid) {
		this.posterid = posterid;
	}

	public String getPostername() {
		return postername;
	}

	public void setPostername(String postername) {
		this.postername = postername;
	}

	public String getPosterdes() {
		return posterdes;
	}

	public void setPosterdes(String posterdes) {
		this.posterdes = posterdes;
	}

	public int getPostertype() {
		return postertype;
	}

	public void setPostertype(int postertype) {
		this.postertype = postertype;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
