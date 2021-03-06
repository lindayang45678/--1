package com.lakala.module.goods.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.Constant;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.service.GoodsPublishService;
import com.lakala.module.goods.vo.GoodsPublishInput;
import com.lakala.module.goods.vo.GoodsPublishOutput;
import com.lakala.util.ReturnMsg;

/**
 * 商品上下架
 * @author sunjiabo
 */
@Controller
@RequestMapping("/goodspublish")
public class GoodsPublishController {
	
	@Resource
	private GoodsPublishService goodsPublishService;
	
	private static Logger logger = (Logger) LoggerFactory.getLogger(GoodsPublishController.class);
	
	
	@RequestMapping("/upshelf")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public @ResponseBody ObjectOutput goodsUpShelf(GoodsPublishInput input){
		
		ObjectOutput result=new ObjectOutput();
		GoodsPublishOutput output=new GoodsPublishOutput();
		output.setToken(input.getToken());
		
		try {
			goodsPublishService.updateGoods(input);
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			
			Integer opt=input.getOpt();
			if(opt==Constant.GOODS_STATUS_ONSALE){
				result.set_ReturnMsg("上架成功");
			}else if(opt==Constant.GOODS_STATUS_DWONSALE){
				result.set_ReturnMsg("下架成功");
			}
			
		} catch (LakalaException e) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(e.getMessage());
			
			logger.error(e.getMessage());
		}
		
		result.set_ReturnData(output);
		
		return result;
	}
	
	
//	@RequestMapping("/downshelf")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public @ResponseBody ObjectOutput goodsDownShelf(GoodsPublishInput input){
		ObjectOutput result=new ObjectOutput();
		GoodsPublishOutput output=new GoodsPublishOutput();
		output.setToken(input.getToken());
		
		try {
			goodsPublishService.updateGoods(input);
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
			
		} catch (Exception e) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg("操作失败");
			
			logger.error("商品或sku不存在");
		}
		
		result.set_ReturnData(output);
		
		return result;
	}
	
	
	
	
	
	
}
