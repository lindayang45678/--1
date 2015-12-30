package com.lakala.module.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis获取数据
 * 其它需要获取redis数据的服务继承此类
 *
 * @author ph.li
 */
@Component
public class RedisServiceImpl {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 根据key，获取json字符串
     */
    protected String getRedisValueByKey(final String key) {
        String value = this.redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] value =
                        connection.get(redisTemplate.getStringSerializer().serialize(key));
                String v = redisTemplate.getStringSerializer().deserialize(value);
                return v;
            }

        });
        return value;
    }


    /**
     * 从redis读取所有商品信息.
     * add by HOT.LIU on 2015/3/2.
     *
     * @param key the key
     * @param k   the k
     * @return the object
     */
    public Object selectObjectByKey(String key, final Object k) {
        return redisTemplate.opsForHash().get(key, k);
    }
}
