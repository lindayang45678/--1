package com.lakala.module.goods.cache.ehcache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.annotation.Cacheable;

/**
 * 商品缓存，以PSAM为KEY
 * @author ph.li
 *
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Cacheable(value="kdbGoodsCache", key="#psam")
public @interface GoodsCache {
	
}
