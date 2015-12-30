package com.lakala.module.goods.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @ClassName: GoodsPoolInfoVO
 * @Description: 平台商品池商品信息实体
 * @author zhiziwei
 * @date 2015-3-6 下午12:02:10
 * 
 */
public class GoodsPoolInfoVO {
	private Integer tGoodInfoPoolId;// 商品池商品id

	private Integer tRealCateId;// 末级分类ID

	private String tRealCateDisc;// 末级分类描述

	private String tRealCateTreeDisc;// 分类信息完整描述
	
	private Integer tBrandId;// 品牌ID
	
	private String brandName;// 品牌描述
	
	private String goodBarCode;// 国标码

	private String goodName;// 商品名称

	private String goodBigPic;// 商品主图，格式：文件名1;文件名2;文件名3....

	private Integer distributionFlag;// 配送标识： 一小时送达：362； 三到五小时送达：363；三到五天送达：364；五天以上送达 365

	private String goodIntroduce; //商品简介
	
	private Integer balanceWay;//结算方式：按扣点结算：1      按结算价结算：2
	
	private Integer isExpressToHome;//只允许到店自提：是：1      否：0
	
	private List<GoodsPoolSkuInfoVO> skus;// 商品池sku数据

	private List<ImageInfoVO> images;// 商品池商品图片数据
	
	private Integer tGoodInfoId; //商品id: 已存在的自营商品的ID，用于修改自营商品
	
	private BigDecimal salePrice; //统一售价
	
	private Integer isfreshfood; //生鲜标记

	public Integer gettGoodInfoPoolId() {
		return tGoodInfoPoolId;
	}

	public void settGoodInfoPoolId(Integer tGoodInfoPoolId) {
		this.tGoodInfoPoolId = tGoodInfoPoolId;
	}

	public Integer gettRealCateId() {
		return tRealCateId;
	}

	public void settRealCateId(Integer tRealCateId) {
		this.tRealCateId = tRealCateId;
	}

	public String gettRealCateDisc() {
		return tRealCateDisc;
	}

	public void settRealCateDisc(String tRealCateDisc) {
		this.tRealCateDisc = tRealCateDisc;
	}

	public String gettRealCateTreeDisc() {
		return tRealCateTreeDisc;
	}

	public void settRealCateTreeDisc(String tRealCateTreeDisc) {
		this.tRealCateTreeDisc = tRealCateTreeDisc;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodBigPic() {
		return goodBigPic;
	}

	public void setGoodBigPic(String goodBigPic) {
		this.goodBigPic = goodBigPic;
	}

	public List<GoodsPoolSkuInfoVO> getSkus() {
		return skus;
	}

	public void setSkus(List<GoodsPoolSkuInfoVO> skus) {
		this.skus = skus;
	}

	public List<ImageInfoVO> getImages() {
		return images;
	}

	public void setImages(List<ImageInfoVO> images) {
		this.images = images;
	}

	public Integer getDistributionFlag() {
		return distributionFlag;
	}

	public void setDistributionFlag(Integer distributionFlag) {
		this.distributionFlag = distributionFlag;
	}

	public String getGoodIntroduce() {
		return goodIntroduce;
	}

	public void setGoodIntroduce(String goodIntroduce) {
		this.goodIntroduce = goodIntroduce;
	}

	public Integer getBalanceWay() {
		return balanceWay;
	}

	public void setBalanceWay(Integer balanceWay) {
		this.balanceWay = balanceWay;
	}

	public Integer getIsExpressToHome() {
		return isExpressToHome;
	}

	public void setIsExpressToHome(Integer isExpressToHome) {
		this.isExpressToHome = isExpressToHome;
	}

	public Integer gettBrandId() {
		return tBrandId;
	}

	public void settBrandId(Integer tBrandId) {
		this.tBrandId = tBrandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGoodBarCode() {
		return goodBarCode;
	}

	public void setGoodBarCode(String goodBarCode) {
		this.goodBarCode = goodBarCode;
	}

	public Integer gettGoodInfoId() {
		return tGoodInfoId;
	}

	public void settGoodInfoId(Integer tGoodInfoId) {
		this.tGoodInfoId = tGoodInfoId;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getIsfreshfood() {
		return isfreshfood;
	}

	public void setIsfreshfood(Integer isfreshfood) {
		this.isfreshfood = isfreshfood;
	}
}
