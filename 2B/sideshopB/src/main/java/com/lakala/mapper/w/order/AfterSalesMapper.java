package com.lakala.mapper.w.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lakala.base.model.Afterapprovalrecord;
import com.lakala.base.model.Approval;
import com.lakala.base.model.Return;
import com.lakala.base.model.ReturnItems;

public interface AfterSalesMapper {
	void updateReturnRemarkByTorderProviderId(Map<String,Object> map);
	void updateBySkuAndNetno(Map<String,Object> map);
	void updateStore(Map<String,Object> map);
	List<HashMap<String, Object>> findApprovalDefinitions(Map<String, Object> params);
	void insertApprovalSelective(Approval approval);
	void insertAfterapprovalrecord(Afterapprovalrecord afterapprovalrecord);
	
	void insertReturn(Return returns);
	void insertReturnItems(ReturnItems returnItems);
}
