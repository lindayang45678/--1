package com.lakala.mapper.w.order;


import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.lakala.base.model.Torderitems;

public interface TorderitemsMapper {

    int updateByPrimaryKeySelective(Torderitems record);
    
    //订单取消重置释放库存
  	void updateBatchRestoreByCancelOrder(Map map);
  	
  	int updateTorderItemsCancelByTorderItemsId(Integer torderitemsid);
  	
  	void updateTorderitemsWithLogisID(Map m);
  	
  	int updateTorderItemsCancelByTorderItemsIdGrops(@Param(value="torderitemsids") String torderitemsids);

  	void updateTorderitemsPayChanel(Map<String, Object> map);
  	
  	int confirmOrderByTorderItemsId(Map<String, Object> map);
}