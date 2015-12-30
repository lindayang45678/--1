package com.lakala.mapper.w.order;

import java.util.Map;

import com.lakala.base.model.Torderprovider;


public interface TorderproviderMapper {

	void updateTorderProvider(Torderprovider opNew);
	
	void updateTorderProviderCancelByTorderProviderId(String torderproviderid);
	
	void confirmOrderByTorderProviderId(Map<String, Object> map);
	
	void updateTorderproviderPayChanel(Map<String, Object> map);

}