package com.lakala.module.user.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.vo.ShopInfoInput;
import com.lakala.module.user.vo.UserRegisterInput;


/**
 * 店铺信息
 * @author zjj
 * @date 2015年2月28日
 */

public interface ShopInfoService {

	/**
	 * 根据店铺主键查询店铺信息
	 * @return
	 */
	public ObjectOutput findShopInfoByPK(ShopInfoInput input) throws LakalaException;
	
	/**
	 * 编辑店铺信息
	 * @param input
	 * @return
	 * @throws LakalaException
	 */
	public ObjectOutput editShopInfo(ShopInfoInput input) throws LakalaException;
	
	/**
	 * 根据店主信息查询店铺信息集合
	 * @param input
	 * @return
	 * @throws LakalaException
	 */
	public ObjectOutput findShopListByShoperTel(ShopInfoInput input) throws LakalaException;
	
	
	/**
	 * 更新修改小店地址信息
	 * @param input
	 * @return
	 * yangjunguo 2015-07-27
	 */
	public ObjectOutput<UserRegisterInput> uptShopAddInfo(UserRegisterInput input)  throws LakalaException;
	
}
