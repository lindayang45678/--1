package com.lakala.mapper.r.order;

import java.util.List;
import java.util.Map;

import com.lakala.base.model.Torderprovider;

/**
 * Created by HOT.LIU on 2015/3/3.
 */
public interface TorderproviderMapper {

    public Torderprovider selectByPrimaryKey(String torderproviderid);
	
	public List<Torderprovider> selectOrderProviderByMap(Map map);
	
    /**
    * 判断订单torder表字段 fav 营销活动是否为空，0标识该订单下所有营销活动为空，大于0标识存在不为空的情况
    * @param map  map中传过来的参数为torderid不为空，表示从母表主键判断;
	 * 传过来的参数为torderproviderid不为空，表示从订单供应商表主键判断;传过来的参数为torderitemsid不为空，表示从商品明细表主键判断;
    * @return
    */
    public int checkOrderFavIsNull(Map map);
}