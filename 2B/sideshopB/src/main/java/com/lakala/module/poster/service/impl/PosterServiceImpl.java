package com.lakala.module.poster.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.poster.model.TerminalChannel;
import com.lakala.module.poster.service.PosterService;
import com.lakala.module.poster.vo.PosterInput;
import com.lakala.util.ReturnMsg;
import com.lakala.util.StringUtil;

/**
 * 
 * @ClassName: PosterService
 * @Description: 广告模块服务层接口实现
 * @author zhiziwei
 * @date 2015-3-4 下午2:30:46
 * 
 */

@Service
public class PosterServiceImpl implements PosterService {
	private Logger logger = LoggerFactory.getLogger(PosterServiceImpl.class);


	/**
	 * @Description: 根据终端编号、频道号，获取广告列表
	 * @param psam号
	 * @param channelid
	 *            终端编号
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<TerminalChannel>> getPosterList(PosterInput input) throws LakalaException {
		logger.info("根据终端编号，获取广告列表：input = " + input.toString());
		
		String prefix = "adlist_psam_";
		
		// 取出请求参数
		String psam = input.getPsam();
		String channelcode = input.getChannelcode();
		
		// 定义返回值
		ObjectOutput<List<TerminalChannel>> result = new ObjectOutput<List<TerminalChannel>>();
		result._ReturnData = new ArrayList<TerminalChannel>();
		
		// 异常判断
		if (!StringUtil.verdict(psam) || !StringUtil.verdict(channelcode)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}
		try {
			//读取redis广告数据
			String str = "";//redis.selectByKey(prefix + psam);
			System.err.println(str);
			
			if (StringUtil.verdict(str)) {
				List<TerminalChannel> temp = JSONArray.parseArray(str,TerminalChannel.class);
				//过滤指定渠道广告
				for (TerminalChannel t : temp) {
					if (t.getNetChannelId().equals(channelcode)) {
						result._ReturnData.add(t);
					}
				}
				
				result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
			} else {
				result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
				result.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
			}
			
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return result;
	}

}
