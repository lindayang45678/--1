package com.lakala.model.coupon;

import java.math.BigDecimal;

public class FavorableruleCouponBatch {
    private Long id;

    private Long favid;

    private Long batchid;

    private BigDecimal orderamount;

    private Integer couponcount;

    private BigDecimal couponmax;

    private Integer disabled;

    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFavid() {
        return favid;
    }

    public void setFavid(Long favid) {
        this.favid = favid;
    }

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(Long batchid) {
        this.batchid = batchid;
    }

    public BigDecimal getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
    }

    public Integer getCouponcount() {
        return couponcount;
    }

    public void setCouponcount(Integer couponcount) {
        this.couponcount = couponcount;
    }

    public BigDecimal getCouponmax() {
        return couponmax;
    }

    public void setCouponmax(BigDecimal couponmax) {
        this.couponmax = couponmax;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}