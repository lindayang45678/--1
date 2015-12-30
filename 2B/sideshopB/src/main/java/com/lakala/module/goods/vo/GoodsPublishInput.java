package com.lakala.module.goods.vo;

import java.util.List;

import com.lakala.module.comm.ObjectInput;

public class GoodsPublishInput extends ObjectInput{
	
	/**商品ID集合*/
	private List<Integer> goodsIdList;
	
	/**操作  上架：208  下架：209  */
	private Integer opt;
	
	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

	public List<Integer> getGoodsIdList() {
		return goodsIdList;
	}

	public void setGoodsIdList(List<Integer> goodsIdList) {
		this.goodsIdList = goodsIdList;
	}
}
