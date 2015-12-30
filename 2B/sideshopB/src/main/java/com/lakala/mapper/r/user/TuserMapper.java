package com.lakala.mapper.r.user;

import com.lakala.base.model.Tuser;

public interface TuserMapper {

    Tuser selectByPrimaryKey(Integer tuserid);
    
    Tuser selectByGysId(Integer gysid);

}