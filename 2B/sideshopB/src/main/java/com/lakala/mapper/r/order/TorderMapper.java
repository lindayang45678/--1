package com.lakala.mapper.r.order;

import java.util.List;
import java.util.Map;

import com.lakala.exception.LakalaException;
import com.lakala.model.Torder;
import com.lakala.model.order.OrderInfo;
import com.lakala.model.order.OrderProviderQueryInfo;
import com.lakala.model.order.OrderProviderV2Info;
import com.lakala.model.order.OrderProviderV2QueryInfo;
import com.lakala.model.order.OrderV2Info;
import com.lakala.module.order.vo.OrderDetailInput;


public interface TorderMapper {

	Torder selectByPrimaryKey(String torderid);
	
	/**
	 * 查询订单列表-用于身边店主订单多条件查询-用于大订单
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年3月4日 下午6:17:21
	 * @todo: TODO
	 */
	List<OrderInfo> queryCommonOrderByMap(Map<String,Object> map);
	
	/**
	 * 查询订单总数-用于身边店主订单多条件查询-用于大订单
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年3月4日 下午6:18:03
	 * @todo: TODO
	 */
	Integer countCommonOrderByMap(Map<String,Object> map);

	/**
	 * 查询订单详情-用于身边店主订单多条件查询 返回json数据，--用于大订单详情的查询
	 * @param orderinput
	 * @return
	 * @throws LakalaException
	 * @author: yhg 
	 * @time: 2015年3月5日 上午11:21:10
	 * @todo: TODO
	 */
	OrderInfo viewOrderByMap(OrderDetailInput orderdetailinput);
	
	/**
	 * 查询订单列表-用于身边店主订单多条件查询-用于供应商订单
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年3月21日 下午2:04:04
	 * @todo: TODO
	 */
	List<OrderProviderQueryInfo> queryCommonOrderProviderByMap(Map<String,Object> map);
	
	/**
	 * 查询订单总数-用于身边店主订单多条件查询-用于供应商订单
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年3月21日 下午2:04:11
	 * @todo: TODO
	 */
	Integer countCommonOrderProviderByMap(Map<String,Object> map);
	
	/**
	 * 查询订单详情-用于身边店主订单多条件查询 返回json数据，--用于供应商订单详情的查询
	 * @param orderdetailinput
	 * @return
	 * @author: yhg 
	 * @time: 2015年3月21日 下午2:19:31
	 * @todo: TODO
	 */
	OrderProviderV2QueryInfo viewCommonOrderProviderByMap(OrderDetailInput orderdetailinput);
	
	
	
	/**
	 * 查询订单列表-用于身边店主订单多条件查询-用于供应商订单 --用于20150417V2版
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年4月17日 下午3:45:45
	 * @todo: TODO
	 */
	List<OrderProviderV2Info> queryCommonOrderProviderByMapV2(Map<String,Object> map);
	
	
	
	/**
	 * 查询订单列表-用于身边店主订单多条件查询-用于大订单 --用于20150417V2版
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年3月4日 下午6:17:21
	 * @todo: TODO
	 */
	List<OrderV2Info> queryCommonOrderByMapV2(Map<String,Object> map);
	
	/**
	 * 查询订单实体 --主要用于短信发送
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年4月27日 下午2:37:23
	 * @todo: TODO
	 */
	List<Torder> selectOrderListByMap(Map map);
	
    /**
     * 根据tallorderid查总的运费
     * @param tallorderid
     * @return
     * @author: yhg 
     * @time: 2015年5月14日 下午4:01:03
     * @todo: TODO
     */
    String selectAllOrderLogisfeeById(Integer tallorderid);

}