package com.lakala.module.goods.cache;

import java.util.List;

import com.lakala.base.model.Realcate;
import com.lakala.exception.LakalaException;

/**
 * 虚分类对应实分类查询
 * 
 * @author ph.li
 *
 */
public interface CategoryExt {

	/**
	 * 根据虚分类获取所有子分类对应的实分类，用于模板商品列表，点击分类时，查询商品
	 * @param virtualcatid
	 * @return
	 * @throws LakalaException
	 */
	List<Realcate> getRealCats(String virtualcatid) throws LakalaException;
	
	/**
	 * 获取有产品的所有的B端营销分类，用于店主APP，模板商品列表，商品搜索
	 * @param virtualcatid
	 * @return
	 * @throws LakalaException
	 */
	public List<Realcate> getAllRealCats() throws LakalaException;
}
