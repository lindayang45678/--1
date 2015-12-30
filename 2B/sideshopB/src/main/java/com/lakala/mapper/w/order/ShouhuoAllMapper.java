package com.lakala.mapper.w.order;


import java.util.Map;



public interface ShouhuoAllMapper {
	void updateorderstate(Map<String,Object> map);
	void updateproviderstate(Map<String,Object> map);
	void updateitemstate(Map<String,Object> map);//更新单条
	void updatelogisticitemstate(Map<String,Object> map);//更新单条
	void updatelogisticinfostate(Map<String,Object> map);//更新单条
}
