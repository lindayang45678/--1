package com.lakala.module.goods.cache.ehcache;

import org.springframework.stereotype.Component;

import com.lakala.module.goods.cache.GoodsExt;
/**
 * 使用EHCACHE作为缓存
 * @author ph.li
 *
 */
@Component
public class GoodsExtEhcache implements GoodsExt{

	@GoodsCache
	@Override
	public String findGoodsByPsam(String psam) {
		System.out.println("-------------get psam :"+psam);
		return null;
	}

	
}
