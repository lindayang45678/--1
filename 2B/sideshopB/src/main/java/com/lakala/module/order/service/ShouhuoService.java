package com.lakala.module.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.vo.ShouhuoInput;
import com.lakala.module.order.vo.ShouhuoOutput;

/**
 * 店主收货服务接口
 * @author ls
 *
 */
public interface ShouhuoService {
	
	//查询物流
	public ObjectOutput<ShouhuoOutput> getShouhuoInfo(ShouhuoInput si);
	//确认收货
	public ObjectOutput<List<Integer>> updateShouhuo(ShouhuoInput si, HttpServletRequest req);
	ObjectOutput<ShouhuoOutput> getConfirmInfo(ShouhuoInput si,List<Integer> logidlist);
}
