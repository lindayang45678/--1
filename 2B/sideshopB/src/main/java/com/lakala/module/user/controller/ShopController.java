package com.lakala.module.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.service.ShopInfoService;
import com.lakala.module.user.vo.ShopInfoInput;
import com.lakala.module.user.vo.UserRegisterInput;
import com.lakala.util.ReturnMsg;


@Component
@RequestMapping("/shop")
public class ShopController {
	
	private static Logger logger = Logger.getLogger(ShopController.class);
	
	@Autowired
	private ShopInfoService shopinfoService;

	/**
	 * 查看店铺信息
	 * @param id 店铺ID
	 * @return
	 */
	@RequestMapping(value = "/shopinfo",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput shopInfo(ShopInfoInput input){
		
		ObjectOutput info = null;
		if(null == input && (null == input.getId() || !StringUtils.hasText(input.getPsam()))){
			logger.error("请求参数丢失");
			
			info = new ObjectOutput();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg("请求参数丢失");
			
			return info;
		}
		try {
			return shopinfoService.findShopInfoByPK(input);
		} catch (LakalaException e) {

			logger.error("获取店铺信息失败！");

			info = new ObjectOutput();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg("获取店铺信息失败！");
			
			return info;
		}
	}
	
	/**
	 * 编辑店铺信息
	 * @param id 店铺ID
	 * @return
	 */
	@RequestMapping(value = "/editshopinfo",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput editshopinfo(ShopInfoInput input,HttpServletRequest request){
		ObjectOutput info = null;
		
		input.setReq(request);
		try {
			info = shopinfoService.editShopInfo(input);
		} catch (LakalaException e) {
			logger.error("编辑店铺信息失败!",e);
			info = new ObjectOutput();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		
		return info;
	}
	
	/**
	 * 获取店主下店铺信息集合
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/shoplist",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput shoplist(ShopInfoInput input){
		ObjectOutput info = null;
		
		try {
			info = shopinfoService.findShopListByShoperTel(input);
		} catch (LakalaException e) {
			logger.error("查询店主下店铺信息失败!",e);
			
			info = new ObjectOutput();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		
		return info;
	}
	
	
	/**
	 * 更新修改小店地址信息
	 * @param input
	 * @return
	 * yangjunguo 2015-07-27
	 */
	@RequestMapping(value = "/uptShopAddInfo",method = RequestMethod.POST)
	public @ResponseBody ObjectOutput<UserRegisterInput> uptShopAddInfo(UserRegisterInput input){
		ObjectOutput<UserRegisterInput> info = null;
		try {
			info = shopinfoService.uptShopAddInfo(input);
		} catch (LakalaException e) {
			logger.error("更新店铺地址信息发生异常!",e);
			info = new ObjectOutput<UserRegisterInput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			info.set_ReturnMsg(e.getLocalizedMessage());
		}
		
		return info;
	}
	
	
}
