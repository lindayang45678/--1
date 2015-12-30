package com.lakala.module.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TCoupon implements Serializable  {

	private static final long serialVersionUID = 8491280952365576838L;
	
    private Long favorableid;

    private Long batchtickeid;

    private Date starttime;

    private Date endtime;

    private Double cost;

    private Integer status;

    private String username;

    private String tel;

    private Integer disabled;

    private String batchtickename;

    private String favorablecode;

    private String favcode;

    private Integer usenum;

    private Integer usednum;

    private Integer favusenum;
    
    private BigDecimal ordermount;
    
    private String deviceno;
    
    
    
    

    public String getDeviceno() {
		return deviceno;
	}

	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}

	public BigDecimal getOrdermount() {
		return ordermount;
	}

	public void setOrdermount(BigDecimal ordermount) {
		this.ordermount = ordermount;
	}

	public Long getFavorableid() {
        return favorableid;
    }

    public void setFavorableid(Long favorableid) {
        this.favorableid = favorableid;
    }

    public Long getBatchtickeid() {
        return batchtickeid;
    }

    public void setBatchtickeid(Long batchtickeid) {
        this.batchtickeid = batchtickeid;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getBatchtickename() {
        return batchtickename;
    }

    public void setBatchtickename(String batchtickename) {
        this.batchtickename = batchtickename == null ? null : batchtickename.trim();
    }

    public String getFavorablecode() {
        return favorablecode;
    }

    public void setFavorablecode(String favorablecode) {
        this.favorablecode = favorablecode == null ? null : favorablecode.trim();
    }

    public String getFavcode() {
        return favcode;
    }

    public void setFavcode(String favcode) {
        this.favcode = favcode == null ? null : favcode.trim();
    }

    public Integer getUsenum() {
        return usenum;
    }

    public void setUsenum(Integer usenum) {
        this.usenum = usenum;
    }

    public Integer getUsednum() {
        return usednum;
    }

    public void setUsednum(Integer usednum) {
        this.usednum = usednum;
    }

    public Integer getFavusenum() {
        return favusenum;
    }

    public void setFavusenum(Integer favusenum) {
        this.favusenum = favusenum;
    }
}