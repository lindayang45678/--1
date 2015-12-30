package com.lakala.module.order.service;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.vo.ExpressInput;
import com.lakala.module.order.vo.ExpressOutput;

/**
 * 物流服务端
 * @author ls
 *
 */
public interface ExpressService {
	
	//查询物流
	public ObjectOutput<ExpressOutput> getExpressInfo(ExpressInput ei);
}
