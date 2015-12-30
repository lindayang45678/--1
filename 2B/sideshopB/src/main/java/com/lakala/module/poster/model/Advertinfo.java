package com.lakala.module.poster.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Advertinfo implements Serializable {

	private static final long serialVersionUID = -4208842791091508453L;

	@JSONField(serialize = false)
	private Long posteritemid;// 广告条件id

	@JSONField(serialize = false)
	private String data;// 广告发布条件

	@JSONField(name = "a")
	private Long posterid;// 广告id

	@JSONField(name = "b")
	private String postername;// 广告名称

	@JSONField(serialize = false)
	private Date starttime;// 广告开始时间

	@JSONField(serialize = false)
	private Date endtime;// 广告结束时间

	@JSONField(name = "c")
	private Integer sort;// 广告顺序排序

	@JSONField(name = "d")
	private String imagepath;// 广告图片

	@JSONField(name = "e")
	private String url;// 广告url，当发布配型为url时该字段有值

	@JSONField(name = "f")
	private Long goodskuinfoid;// 广告指向商品单品，当发布类型为商品时该字段有值

	@JSONField(name = "g")
	private Long promotionsid;// 广告指向活动，当发布配型为活动时该字段有值

	@JSONField(name = "h")
	private Integer type;// 发布类型，1商品，2活动，3URL

	@JSONField(name = "i")
	private String valueName;// 类型值名称,例如商品就是商品名称，活动就是活动名称，url则对应地址

	@JSONField(name = "j")
	private Long goodInfoId;// 商品id

	@JSONField(serialize = false)
	private Long tabid;// 发布频道

	@JSONField(serialize = false)
	private Integer isnationwide;// 是否全国发布

	public Long getGoodInfoId() {
		return goodInfoId;
	}

	public void setGoodInfoId(Long goodInfoId) {
		this.goodInfoId = goodInfoId;
	}

	public Long getPosteritemid() {
		return posteritemid;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public void setPosteritemid(Long posteritemid) {
		this.posteritemid = posteritemid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getPosterid() {
		return posterid;
	}

	public void setPosterid(Long posterid) {
		this.posterid = posterid;
	}

	public String getPostername() {
		return postername;
	}

	public void setPostername(String postername) {
		this.postername = postername;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getGoodskuinfoid() {
		return goodskuinfoid;
	}

	public void setGoodskuinfoid(Long goodskuinfoid) {
		this.goodskuinfoid = goodskuinfoid;
	}

	public Long getPromotionsid() {
		return promotionsid;
	}

	public void setPromotionsid(Long promotionsid) {
		this.promotionsid = promotionsid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTabid() {
		return tabid;
	}

	public void setTabid(Long tabid) {
		this.tabid = tabid;
	}

	public Integer getIsnationwide() {
		return isnationwide;
	}

	public void setIsnationwide(Integer isnationwide) {
		this.isnationwide = isnationwide;
	}

}
