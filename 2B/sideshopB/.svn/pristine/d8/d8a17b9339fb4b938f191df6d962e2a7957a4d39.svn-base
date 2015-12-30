package com.lakala.module.goods.service;

import java.util.List;

import com.lakala.exception.LakalaException;
import com.lakala.model.ProductDetailedInformation;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.vo.SelfGoodsDetailInput;
import com.lakala.module.goods.vo.SideShopGoodsListInput;


/**
 * Created by HOT.LIU on 2015/2/28.
 */
public interface SideshopGoodsService {

	ObjectOutput getGoodsByPsamAndVirtualCatId(SideShopGoodsListInput inputModel) throws LakalaException ;

	ObjectOutput searchGoods(SideShopGoodsListInput inputModel) throws LakalaException ;

	ObjectOutput getPageProductDetile(List<ProductDetailedInformation> list,
			String psam, String type) throws LakalaException ;

	ObjectOutput getSideShopGoodsByPsamAndVirtualCatId(
			SideShopGoodsListInput inputModel) throws LakalaException ;

	ObjectOutput getselfgoodsdetail(SelfGoodsDetailInput inputModel) throws LakalaException ;
	
	
}
