package com.lakala.mapper.r.order;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.Torderitems;

public interface TorderitemsMapper {
    com.lakala.base.model.Torderitems selectByPrimaryKey(String torderitemsid);
    
    //查询指定psam，近一个月内，下过单的商品  zhiziwei
    List<Torderitems> getGoodsListByPsam(String psam);
    
	/**
     * 校验某torder级或供应商级的下的子订单是否已全部取消
	 * @param map 参数orderid标识torder表主键,参数 orderproviderid标识供应商级订单主键
     * @return
     * @author: yhg 
     * @time: 2015年4月25日 下午4:00:30
     * @todo: TODO
     */
	int checkOrderIsAllCancel(Map<String,Object> map);
	
	/**
	 * 根据map查询商品订单list
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年4月25日 下午4:05:27
	 * @todo: TODO
	 */
	List<Torderitems> selectOrderItemsList(Map map);
	
	
	/**
	 * 校验某torder级或供应商级的下的子订单是否已全部发货
	 * @param map
	 * @return
	 * @author: yhg 
	 * @time: 2015年4月27日 上午11:04:14
	 * @todo: TODO
	 */
	int checkOrderIsAllComfirm(Map<String,Object> map);
    
    Integer checkOrderIsDoAll(Map map);
    
    /**
     * 返回值为 0 标识已是最后一个，全部取消。99999标识存在已发货的商品订单，运费不取消，其他大于1的值标识不是最后一个需取消的订单
     * @param map
     * @return
     * @author: yhg 
     * @time: 2015年6月2日 下午11:37:36
     * @todo: TODO
     */
    int selectIsLastOrderitems(Map map);
    
    /**
     * 查该该数据是不是最后一条需取消的数据，若是最后一条，说明整单都取消了，需释放优惠券
     * 返回值 0标识没有数据了，是最后的数据。大于0标识该整单下还有其他订单未取消
     * @param map
     * @return
     * @author: yhg 
     * @time: 2015年5月18日 下午4:52:51
     * @todo: TODO
     */
    int selectIsLastOrderitemsWhenFavCheck(Map map);
    String selectgoodsnames(int tallorderid);
}