package com.lakala.module.wholesale.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by HOT.LIU on 2015/2/13.
 */
public class SkuDetails implements Serializable {

    private static final long serialVersionUID = 3045163199190576003L;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String marketPrice;
    private Integer o2oId;
    private String salePrice;
    private String skuFrontDisNameStr;
    private String skuIdStr;
    private Double soldStore;
    private Double store;
    private Integer tGoodInfoId;
    private Integer tGoodSkuInfoId;
    private BigDecimal promotionPrice;

    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 截止时间
     */
    private Date endTime;

    /**
     * 促销类型 190.团购,191.抢购,192.抽奖 订单满赠 67 订单满减68 订单返现69 订单补贴70 商品直降71 商品买赠72 加价购307
     */
    private int type;

    /**
     * 限购数量
     */
    private int purchaseCount = 0;

    public String getMarketPrice() {
        return df.format(new BigDecimal(marketPrice));
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = (marketPrice == null ? df.format(new BigDecimal(0)) : df.format(new BigDecimal(marketPrice)));
    }

    public Integer getO2oId() {
        return o2oId;
    }

    public void setO2oId(Integer o2oId) {
        this.o2oId = o2oId;
    }

    public String getSalePrice() {
        return df.format(new BigDecimal(salePrice));
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = (salePrice == null ? df.format(new BigDecimal(0)) : df.format(new BigDecimal(salePrice)));
    }

    public String getSkuFrontDisNameStr() {
        return skuFrontDisNameStr;
    }

    public void setSkuFrontDisNameStr(String skuFrontDisNameStr) {
        this.skuFrontDisNameStr = (skuFrontDisNameStr == null ? "" : skuFrontDisNameStr);
    }

    public String getSkuIdStr() {
        return skuIdStr;
    }

    public void setSkuIdStr(String skuIdStr) {
        this.skuIdStr = (skuIdStr == null ? "" : skuIdStr);
    }

    public Double getSoldStore() {
        return soldStore;
    }

    public void setSoldStore(Double soldStore) {
        this.soldStore = (soldStore == null ? Double.parseDouble("0") : soldStore);
    }

    public Double getStore() {
        return store;
    }

    public void setStore(Double store) {
        this.store = (store == null ? Double.parseDouble("0") : store);
    }

    public Integer getTGoodInfoId() {
        return tGoodInfoId;
    }

    public void setTGoodInfoId(Integer tGoodInfoId) {
        this.tGoodInfoId = tGoodInfoId;
    }

    public Integer getTGoodSkuInfoId() {
        return tGoodSkuInfoId;
    }

    public void setTGoodSkuInfoId(Integer tGoodSkuInfoId) {
        this.tGoodSkuInfoId = tGoodSkuInfoId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public BigDecimal getPromotionPrice() {
        return (promotionPrice == null ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : promotionPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = (promotionPrice == null ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : promotionPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
