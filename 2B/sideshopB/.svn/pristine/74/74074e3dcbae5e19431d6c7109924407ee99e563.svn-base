package com.lakala.module.comm.redis;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisImpl implements RedisInterface{

	@Autowired
	private RedisTemplate<Object, Object> template;
	
	@Override
	public void batchInsertMap(Map<String, String> param) {
		Object[] array = param.keySet().toArray();
		final Map<byte[], byte[]> data = new HashMap<byte[], byte[]>();
		for (int i = 0; i < array.length; i++) {
			String key = array[i].toString();
			try {
				data.put(key.getBytes(), param.get(key).getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		template.executePipelined(new RedisCallback<List<byte[]>>() {
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.mSet(data);
				return null;
			}
		});
	}
	
	
	@Override
	public int deleteByKey(final String k) {
		return template.execute(new RedisCallback<Integer>() {
			@Override
			public Integer doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize(k);
				if (connection.exists(key)) {
					connection.del(key);
					return 1;
				}
				return 0;
			}
		});
	}
	
	
	@Override
	public String selectByKey(final String k) {
		return template.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = template.getStringSerializer().serialize(k);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String name = (String) template.getStringSerializer().deserialize(value);
					return name;
				}
				return null;
			}
		});
	}

	
	
	@Override
	public List<Object> selectListByPattern(String pattern) {
		Set<Object> keys = template.keys(pattern);
		List<Object> list = new ArrayList<Object>();
		list.addAll(keys);
		return list;
	}

	@Override
	public void batchInsertMapForHash(String key,Map<Object, Object> param) {
		template.opsForHash().putAll(key, param);		
	}
	
	@Override
	public List<Object> selectHashListByKeys(String key,List<Object> psams) {
		return template.opsForHash().multiGet(key, psams);
	}
	
	@Override
	public Set<Object> selectHashKeysByKey(String key) {
		return template.opsForHash().keys(key);
	}
	
	@Override
	public Object selectObjectByKey(String key,final Object k){
		return template.opsForHash().get(key, k);
	}


	@Override
	public boolean insert(final String key, final String value) {
		boolean result = template.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = template.getStringSerializer();  
                byte[] _key  = serializer.serialize(key.trim());  
                byte[] _value = serializer.serialize(value.trim());  
                return connection.setNX(_key, _value);  
            }  
        });  
        return result;  
	}


}
