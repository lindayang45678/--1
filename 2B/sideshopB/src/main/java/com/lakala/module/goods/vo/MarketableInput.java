package com.lakala.module.goods.vo;

import com.lakala.module.comm.ObjectInput;



/**
 * 虚分类参数
 * @author liuguojie
 *
 */
public class MarketableInput extends ObjectInput{

  private String phone;
  public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public int getGoodsId() {
	return goodsId;
}
public void setGoodsId(int goodsId) {
	this.goodsId = goodsId;
}
private int goodsId;
	
}
