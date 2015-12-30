package com.lakala.mapper.r.sdbmediastatistics;

import java.util.List;
import java.util.Map;

import com.lakala.model.user.TShopService;

public interface TShopServiceMapper {

	public List<TShopService> queryShopServiceByShopId(Integer shopId);
	
	public List<TShopService> queryShopServiceByOrdereMap(Map<String,Object> map);
	
}
