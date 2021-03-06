package com.lakala.module.poster.service;

import java.util.List;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.poster.model.TerminalChannel;
import com.lakala.module.poster.vo.PosterInput;

/**
 * 
 * @ClassName: PosterService
 * @Description: 广告模块服务层接口
 * @author zhiziwei
 * @date 2015-3-4 下午2:30:46
 * 
 */
public interface PosterService {
	
	/**
	 * @Description: 根据终端编号、频道号，获取广告列表
	 * @param psam号
	 * @param channelid 终端编号
	 * @author zhiziwei
	 */
	public ObjectOutput<List<TerminalChannel>> getPosterList(PosterInput input) 
			throws LakalaException;

}
