package com.lakala.mapper.r.returns;

import com.lakala.base.model.Return;
import com.lakala.module.sreturn.model.ReturnMoble;

import java.util.List;
import java.util.Map;

public interface ReturnMapper {

    Return selectByPrimaryKey(Integer treturnid);

    List<ReturnMoble> findAllByMobile(Map<String, Object> params);
    
    Integer countAllByMobile(Map<String,Object> map);

}