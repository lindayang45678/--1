package com.lakala.module.comm.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisInterface {

	public String selectByKey(final String k);
	
	public void batchInsertMap(Map<String, String> param);
	
	public int deleteByKey(final String k);
	
	public List<Object> selectListByPattern(String pattern);
	
	public void batchInsertMapForHash(String key,Map<Object, Object> param);

	public List<Object> selectHashListByKeys(String key,List<Object> psams);

	public Set<Object> selectHashKeysByKey(String key);

	public Object selectObjectByKey(String key, final Object k);
	
	public boolean insert(String key,String value);

}