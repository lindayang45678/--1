package com.lakala.mapper.r.skunetinfo;

import com.lakala.base.model.Skunetinfo;

import java.util.Map;

public interface SkunetinfoMapper {
    Skunetinfo findSkunetinfoByMap(Map<String, Object> map);
}