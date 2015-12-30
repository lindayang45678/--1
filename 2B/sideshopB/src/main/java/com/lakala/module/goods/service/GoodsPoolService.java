package com.lakala.module.goods.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.vo.GoodsInput;
import com.lakala.module.goods.vo.GoodsPoolBaseInfoVO;
import com.lakala.module.goods.vo.GoodsPoolInfoVO;

/**
 * 
 * @ClassName: GoodsPoolService
 * @Description: 平台商品池服务接口
 * @author zhiziwei
 * @date 2015-3-6 上午11:28:48
 * 
 */
public interface GoodsPoolService {

	/**
     * @Description 根据结算分类，获取平台商品池商品列表
     * @author zhiziwei
     */
	public ObjectOutput<List<GoodsPoolBaseInfoVO>> getGoodsPoolList(GoodsInput input)
			throws LakalaException;

	/**
	 * @Description 获取平台商品详情
	 * @author zhiziwei
	 */
	public ObjectOutput<GoodsPoolInfoVO> getGoodsDetail(GoodsInput input, HttpServletRequest req) 
			throws LakalaException;

	/**
	 * @Description 根据psam id，获取近期下单商品关联的商品池商品（本店近期批发）
	 * @author zhiziwei
	 */
	public ObjectOutput<List<GoodsPoolBaseInfoVO>> getGoodsListByPsam(GoodsInput input) 
			throws LakalaException;

	/**
	 * @Description 根据商品名称，搜索模板商品列表
	 * @author zhiziwei
	 */
	public ObjectOutput<List<GoodsPoolBaseInfoVO>> seachByGoodsName(GoodsInput input)
			throws LakalaException;

}
