package com.lakala.module.goods.vo;

/**
 * 
 * @ClassName: GoodsPoolBaseInfoVO
 * @Description:平台商品列表里商品信息实体
 * @author zhiziwei
 * @date 2015-3-10 上午10:03:52
 * 
 */
public class GoodsPoolBaseInfoVO {
	private Integer tGoodInfoPoolId;// 商品池商品id

	private String goodName;// 商品名称

	private String goodBigPic;// 商品主图的第一张图片

	private Integer distributionFlag;// 配送标识： 一小时送达：362； 三到五小时送达：363；三到五天送达：364；五天以上送达 365
	
	private Integer sort;// 排序
	
	private Integer issaleflag = 0;//判断产品池产品是否已上架到小店0-未上架。1-已上架
	
	

	public Integer gettGoodInfoPoolId() {
		return tGoodInfoPoolId;
	}

	public void settGoodInfoPoolId(Integer tGoodInfoPoolId) {
		this.tGoodInfoPoolId = tGoodInfoPoolId;
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

	public Integer getDistributionFlag() {
		return distributionFlag;
	}

	public void setDistributionFlag(Integer distributionFlag) {
		this.distributionFlag = distributionFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIssaleflag() {
		return issaleflag;
	}

	public void setIssaleflag(Integer issaleflag) {
		this.issaleflag = issaleflag;
	}

}
