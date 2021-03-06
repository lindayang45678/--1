package com.lakala.module.goods.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 
 * @ClassName: GoodsInput
 * @Description: 商品模块接口参数类
 * @author zhiziwei
 * @date 2015-3-10 上午11:17:03
 * 
 */
public class GoodsInput extends ObjectInput {
	private String tRealCateId;// 结算分类末级分类ID

	private String tGoodsInfoId; // 商品ID
	
	private String psam;//psam id
	
	private String tVirtualCateId;// 虚分类末级分类ID
	
	private String goodsName;// 商品名称

	public String gettRealCateId() {
		return tRealCateId;
	}

	public void settRealCateId(String tRealCateId) {
		this.tRealCateId = tRealCateId;
	}

	public String gettGoodsInfoId() {
		return tGoodsInfoId;
	}

	public void settGoodsInfoId(String tGoodsInfoId) {
		this.tGoodsInfoId = tGoodsInfoId;
	}

	//覆盖toString()
	@Override
	public String toString() {
		return "{\"token\":\"" + this.getToken() 
			 + "\",\"mobile\":\""+ this.getMobile()
			 + "\",\"page\":\"" + this.getPage() 
			 + "\",\"pageSize\":\"" + this.getPageSize() 
			 + "\",\"tRealCateId\":\""  + this.gettRealCateId() 
			 + "\",\"tVirtualCateId\":\""  + this.gettVirtualCateId() 
			 + "\",\"psam\":\""  + this.getPsam() 
			 + "\",\"goodsName\":\""  + this.getGoodsName() 
			 + "\",\"tGoodsInfoId\":\"" + this.gettGoodsInfoId() + "\"}";
	}

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}

	public String gettVirtualCateId() {
		return tVirtualCateId;
	}

	public void settVirtualCateId(String tVirtualCateId) {
		this.tVirtualCateId = tVirtualCateId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
