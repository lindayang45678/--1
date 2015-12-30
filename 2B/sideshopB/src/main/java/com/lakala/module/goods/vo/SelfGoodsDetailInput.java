package com.lakala.module.goods.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 
 * @ClassName: GoodsInput
 * @Description: 自营商品模块接口参数类
 * @author zuoyb
 * @date 2015-3-10 上午11:17:03
 * 
 */
public class SelfGoodsDetailInput extends ObjectInput {
	
	private String goodsid; // 商品ID
	
	private String psam;//psam id

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}
	

	
}
