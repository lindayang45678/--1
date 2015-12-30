package com.lakala.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p/>
 * Created by HOT.LIU on 2015/3/2.
 */
public class ProductDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = "a")
    private Long goodInfoId;// 商品id

    @JSONField(name = "b")
    private Long goodSkuInfoId;// 商品skuid

    @JSONField(name = "c")
    private Long o2oid;// o2o表id，通过此查询价格与库存

    @JSONField(name = "d")
    private Long favId = 0L;// 活动id

    @JSONField(name = "e")
    private BigDecimal promotionPrice = new BigDecimal(0);// 直降价

    @JSONField(name = "f")
    private Integer activityFlag = 0;// 活动标识,1为疯抢、2为每团

    @JSONField(name = "g")
    private String[] virtualCatIds;// 虚拟类目数组，用于生成类目json

    @JSONField(name = "h")
    private String[] virtualCatNames;

    @JSONField(name = "i")
    private Integer[] virtualCatSort;

    @JSONField(name = "j")
    private String lastVirtualCatId;// 类目最后根节点id，便于通过类目查询商品

    @JSONField(name = "k")
    private ProductDetailedInformation productDetailedInformation;// 商品详细

    @JSONField(name = "l")
    private Integer sort = 0;

    @JSONField(name = "m")
    private Integer store = 0; // 库存

    @JSONField(name = "n")
    private String goodName;// 商品名称

    @JSONField(name = "o")
    private String goodBigPic;// 商品图片

    @JSONField(name = "p")
    private BigDecimal salePrice;// 销售价

    @JSONField(name = "q")
    private String goodIntroduce = "";//商品简介

    @JSONField(name = "r")
    private Integer isPayAfterArrival;// 是否货到付款

    @JSONField(name = "s")
    private Integer isExpressToHome;// 是否快递到家

    @JSONField(name = "t")
    private BigDecimal marketPrice;// 市场价

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getGoodInfoId() {
        return goodInfoId;
    }

    public void setGoodInfoId(Long goodInfoId) {
        this.goodInfoId = goodInfoId;
    }

    public Long getGoodSkuInfoId() {
        return goodSkuInfoId;
    }

    public void setGoodSkuInfoId(Long goodSkuInfoId) {
        this.goodSkuInfoId = goodSkuInfoId;
    }

    public Integer getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(Integer activityFlag) {
        this.activityFlag = activityFlag;
    }

    public Long getFavId() {
        return favId;
    }

    public void setFavId(Long favId) {
        this.favId = favId;
    }

    public String[] getVirtualCatNames() {
        return virtualCatNames;
    }

    public void setVirtualCatNames(String[] virtualCatNames) {
        this.virtualCatNames = (virtualCatNames == null ? new String[0] : virtualCatNames);
    }

    public String getLastVirtualCatId() {
        return lastVirtualCatId;
    }

    public void setLastVirtualCatId(String lastVirtualCatId) {
        this.lastVirtualCatId = (lastVirtualCatId == null ? "" : lastVirtualCatId);
    }

    public String[] getVirtualCatIds() {
        return virtualCatIds;
    }

    public void setVirtualCatIds(String[] virtualCatIds) {
        this.virtualCatIds = (virtualCatIds == null ? new String[0] : virtualCatIds);
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = (promotionPrice == null ? new BigDecimal(0) : promotionPrice);
    }

    public Long getO2oid() {
        return o2oid;
    }

    public void setO2oid(Long o2oid) {
        this.o2oid = o2oid;
    }

    public ProductDetailedInformation getProductDetailedInformation() {
        return productDetailedInformation;
    }

    public void setProductDetailedInformation(ProductDetailedInformation productDetailedInformation) {
        this.productDetailedInformation = (productDetailedInformation == null ? new ProductDetailedInformation() : productDetailedInformation);
    }

    public Integer[] getVirtualCatSort() {
        return virtualCatSort;
    }

    public void setVirtualCatSort(Integer[] virtualCatSort) {
        this.virtualCatSort = virtualCatSort;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = (goodName == null) ? null : goodName.trim();
    }

    public String getGoodBigPic() {
        return goodBigPic;
    }

    public void setGoodBigPic(String goodBigPic) {
        this.goodBigPic = goodBigPic;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getGoodIntroduce() {
        return goodIntroduce;
    }

    public void setGoodIntroduce(String goodIntroduce) {
        this.goodIntroduce = (goodIntroduce == null) ? null : goodIntroduce.trim();
    }

    public Integer getIsPayAfterArrival() {
        return isPayAfterArrival;
    }

    public void setIsPayAfterArrival(Integer isPayAfterArrival) {
        this.isPayAfterArrival = isPayAfterArrival;
    }

    public Integer getIsExpressToHome() {
        return isExpressToHome;
    }

    public void setIsExpressToHome(Integer isExpressToHome) {
        this.isExpressToHome = isExpressToHome;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else {
            if (obj instanceof ProductDetail) {
                ProductDetail productDetail = (ProductDetail) obj;
                if (productDetail.goodSkuInfoId == this.goodSkuInfoId) {
                    return true;
                }
            }
        }
        return false;
    }
}
