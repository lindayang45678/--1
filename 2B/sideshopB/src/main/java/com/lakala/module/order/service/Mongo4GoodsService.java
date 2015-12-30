package com.lakala.module.order.service;

import java.util.List;

import com.lakala.base.model.Tgoodinfo;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.base.model.TgoodskuinfoWithBLOBs;
import com.lakala.exception.LakalaException;
import com.lakala.module.goods.vo.GoodsCol;
import com.lakala.module.goods.vo.in.GoodsPoolFindInput;
import com.lakala.module.goods.vo.in.SideShopBListInPut;
import com.lakala.module.goods.vo.in.StoreChangeInPut;
import com.lakala.module.goods.vo.out.GoodsPoolFindOutput;
import com.lakala.module.goods.vo.out.SideShopBDetailOutPut;

/**
 * 
 * @author yhg
 *
 */
public interface Mongo4GoodsService {
	
	/**
	 * Mongo接口调用：收集待更新数据（sku库存管理）。该方法来自从lakalaAdmin工程的移植
	 * @param input
	 * @param sku
	 * @throws Exception
	 * @author: yhg 
	 * @time: 2015年6月1日 上午10:16:01
	 * @todo: TODO
	 */
	public void setGoodsStock(StoreChangeInPut input, TgoodskuinfoWithBLOBs sku) throws Exception;
	
	/**
	 * Mongo接口调用：调接口，更新库存（sku库存管理）.该方法来自从lakalaAdmin工程的移植
	 * @param input 待更新数据
	 * @param userid 用户ID
	 * @param username 用户名
	 * @throws Exception
	 * @author: yhg 
	 * @time: 2015年6月1日 上午10:16:04
	 * @todo: TODO
	 */
	
	public void writeStockToMongo(StoreChangeInPut input, String userid, String username, String desc) throws Exception;
	
	public void setGoodsCol(GoodsCol gc, Tgoodinfo good, Tgoodskuinfo sku) throws LakalaException;
	
	public void writeGoodsToMongo(GoodsCol col, String vc, String userid, String username, String ip, String flag) throws LakalaException;

	public void writeSkusToMongo(Tgoodskuinfo sku, String userid, String username, String ip, String flag) throws LakalaException;

	public void downPublish(Integer goodsId, String userid, String username, String ip) throws LakalaException;
	
	public List<GoodsPoolFindOutput> queryGoodsPool(GoodsPoolFindInput input) throws LakalaException;
	
	public void upPublish(GoodsCol col,String vc ,String userid, String username, String ip) throws LakalaException;
	
	public List<SideShopBDetailOutPut> queryPFJH(SideShopBListInPut input) throws LakalaException;	
}
