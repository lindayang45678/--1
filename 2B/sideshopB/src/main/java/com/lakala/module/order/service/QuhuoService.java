package com.lakala.module.order.service;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.vo.QuhuoInput;
import com.lakala.module.order.vo.QuhuoOutput;

/**
 * 物流服务端
 * @author ls
 *
 */
public interface QuhuoService {
	
	//查询物流
	public ObjectOutput<QuhuoOutput> getQuhuoInfo(QuhuoInput qi);
	//确认取货
	public ObjectOutput<QuhuoOutput> updateQuhuo2Sign(QuhuoInput qi);
	//顾客拒收
	public ObjectOutput<QuhuoOutput> reject(QuhuoInput qi);
}
