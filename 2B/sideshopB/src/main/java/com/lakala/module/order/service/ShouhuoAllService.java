package com.lakala.module.order.service;

import javax.servlet.http.HttpServletRequest;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.vo.ShouhuoAllInput;

public interface ShouhuoAllService {
	ObjectOutput<String> updateshall(ShouhuoAllInput sai, HttpServletRequest req);
}
