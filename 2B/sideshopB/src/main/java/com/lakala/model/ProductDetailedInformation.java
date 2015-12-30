package com.lakala.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by HOT.LIU on 2015/2/13.
 */
public class ProductDetailedInformation implements Serializable {

    private static final long serialVersionUID = 7243147394130773216L;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String id;//o2oid

    private String saleprice_o2o;

    private String tgoodskuinfoid;

    private String tgoodinfoid;

    private String goodname;

    private String marketprice;

    private String goodbigpic;

    private BigDecimal promotionPrice;

    private String goodintroduce;

    private Double store;

    private Double soldstore;

    private Integer isforeigngoods;

    private Integer tempstoregoodsflag = 0;

    private String clientservicetel;

    private String clientservicephone;

    private Integer sort = 0;

    private Date onsaletime;

    private Date downsaletime;

    private int platorself;

    private BigDecimal yqsy;

    private Integer distributionFlag;

    private Integer tGoodInfoPoolId;

    private Integer isfreshfood;

    private Integer saleflag = 1;//0-在售；1-下架

    private Integer issoldout = 0;//1没有售罄，0为售罄

    private Integer ismoresku = 0;//1为多sku，0为单sku

    private Integer onekeyupload = 0;//0-非一键上传，1-一键上传

    private Integer freshgoodsflag = 0;//0-非生鲜。1-生鲜

    private Integer goodsStatus;//208-已上架，209-已下架

    private String minPrice;//排序价格

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

    /**
     * 扣点
     */
    private Double deductPercent;
    /**
     * 分润基数
     */
    private Double distributeProfitBase;
    /**
     * 结算价
     */
    private Double balancePrice;
    /**
     * 实际销售价（有活动价的时候用）
     */
    private String factsaleprice;

    public Integer gettGoodInfoPoolId() {
        return null != tGoodInfoPoolId && tGoodInfoPoolId.compareTo(0) > 0 ? tGoodInfoPoolId : null;
    }

    public void settGoodInfoPoolId(Integer tGoodInfoPoolId) {
        this.tGoodInfoPoolId = tGoodInfoPoolId;
    }

    public Integer getIsfreshfood() {
        return isfreshfood;
    }

    public void setIsfreshfood(Integer isfreshfood) {
        this.isfreshfood = isfreshfood;
    }

    public Integer getFreshgoodsflag() {
        return freshgoodsflag;
    }

    public void setFreshgoodsflag(Integer freshgoodsflag) {
        this.freshgoodsflag = freshgoodsflag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getSaleflag() {
        return saleflag;
    }

    public void setSaleflag(Integer saleflag) {
        this.saleflag = saleflag;
    }

    public Integer getDistributionFlag() {
        return distributionFlag;
    }

    public void setDistributionFlag(Integer distributionFlag) {
        this.distributionFlag = distributionFlag;
    }

    public BigDecimal getYqsy() {
        return yqsy;
    }

    public void setYqsy(BigDecimal yqsy) {
        this.yqsy = yqsy;
    }

    public int getPlatorself() {
        return platorself;
    }

    public void setPlatorself(int platorself) {
        this.platorself = platorself;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleprice_o2o() {
        return (saleprice_o2o == null ? df.format(new BigDecimal("0")) : df.format(new BigDecimal(saleprice_o2o)));
    }

    public void setSaleprice_o2o(String saleprice_o2o) {
        this.saleprice_o2o = (saleprice_o2o == null ? df.format(new BigDecimal("0")) : df.format(new BigDecimal(saleprice_o2o)));
    }

    public String getTgoodskuinfoid() {
        return tgoodskuinfoid;
    }

    public void setTgoodskuinfoid(String tgoodskuinfoid) {
        this.tgoodskuinfoid = (tgoodskuinfoid == null ? "" : tgoodskuinfoid);
    }

    public String getTgoodinfoid() {
        return tgoodinfoid;
    }

    public void setTgoodinfoid(String tgoodinfoid) {
        this.tgoodinfoid = (tgoodinfoid == null ? "" : tgoodinfoid);
    }

    public String getGoodname() {
        return (goodname == null ? "" : goodname.trim());
    }

    public void setGoodname(String goodname) {
        this.goodname = (goodname == null ? "" : goodname);
    }

    public String getMarketprice() {

        this.marketprice = (marketprice == null ? df.format(new BigDecimal("0")) : df.format(new BigDecimal(marketprice)));
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = ((marketprice == null || marketprice == "null") ? df.format(String.valueOf(new BigDecimal("0"))) : df.format(new BigDecimal(marketprice)));
    }

    public String getGoodbigpic() {
        return goodbigpic;
    }

    public void setGoodbigpic(String goodbigpic) {
        this.goodbigpic = (goodbigpic == null ? "" : goodbigpic);
    }

    public BigDecimal getPromotionPrice() {
        return (promotionPrice == null ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : promotionPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = (promotionPrice == null ? new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP) : promotionPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public String getGoodintroduce() {
        return (goodintroduce == null ? "" : goodintroduce.trim());
    }

    public void setGoodintroduce(String goodintroduce) {
        this.goodintroduce = (goodintroduce == null ? "" : goodintroduce);
    }

    public Double getStore() {
        return store;
    }

    public void setStore(Double store) {
        this.store = (store == null ? Double.parseDouble("0") : store);
    }

    public Double getSoldstore() {
        return soldstore;
    }

    public void setSoldstore(Double soldstore) {
        this.soldstore = (soldstore == null ? Double.parseDouble("0") : soldstore);
    }

    public Integer getIsforeigngoods() {
        return isforeigngoods;
    }

    public void setIsforeigngoods(Integer isforeigngoods) {
        this.isforeigngoods = (isforeigngoods == null ? 0 : isforeigngoods);
    }

    public Integer getTempstoregoodsflag() {
        return tempstoregoodsflag;
    }

    public void setTempstoregoodsflag(Integer tempstoregoodsflag) {
        this.tempstoregoodsflag = tempstoregoodsflag == null ? 0 : tempstoregoodsflag;
    }

    public String getClientservicetel() {
        return clientservicetel;
    }

    public void setClientservicetel(String clientservicetel) {
        this.clientservicetel = clientservicetel;
    }

    public String getClientservicephone() {
        return clientservicephone;
    }

    public void setClientservicephone(String clientservicephone) {
        this.clientservicephone = clientservicephone;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getOnsaletime() {
        return onsaletime;
    }

    public void setOnsaletime(Date onsaletime) {
        this.onsaletime = onsaletime;
    }

    public Date getDownsaletime() {
        return downsaletime;
    }

    public void setDownsaletime(Date downsaletime) {
        this.downsaletime = downsaletime;
    }

    public Integer getIssoldout() {
        return issoldout;
    }

    public void setIssoldout(Integer issoldout) {
        this.issoldout = issoldout;
    }

    public Integer getIsmoresku() {
        return ismoresku;
    }

    public void setIsmoresku(Integer ismoresku) {
        this.ismoresku = ismoresku;
    }

    public Integer getOnekeyupload() {
        return onekeyupload;
    }

    public void setOnekeyupload(Integer onekeyupload) {
        this.onekeyupload = onekeyupload;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public Double getDeductPercent() {
        return deductPercent;
    }

    public void setDeductPercent(Double deductPercent) {
        this.deductPercent = deductPercent;
    }

    public Double getDistributeProfitBase() {
        return distributeProfitBase;
    }

    public void setDistributeProfitBase(Double distributeProfitBase) {
        this.distributeProfitBase = distributeProfitBase;
    }

    public Double getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(Double balancePrice) {
        this.balancePrice = balancePrice;
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

	public String getFactsaleprice() {
		return factsaleprice;
	}

	public void setFactsaleprice(String factsaleprice) {
		this.factsaleprice = factsaleprice;
	}

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
}
