package com.lakala.module.user.service;


import javax.servlet.http.HttpServletRequest;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.user.vo.ShopUpdateInfo;
import com.lakala.module.user.vo.TstoreApprove;


/**
 * 小店升级接口
 * @author yangjunguo
 * @date 2015年5月21日
 */

public interface ShopUpdateInfoService {

	/**
	 * 升级店铺信息
	 * @return
	 */
	public ObjectOutput<TstoreApprove> shopUpdateInfo(HttpServletRequest request, TstoreApprove input) throws LakalaException;
	
	/**
	 * 按照终端类型检测小店是否注册存在
	 * @return
	 */
	public int checkShopExists(TstoreApprove input) throws LakalaException;
	
	
	/**
	 * 检测小店是否注册存在
	 * @return
	 */
	public int checkShopExistsInTmemberb(TstoreApprove input) throws LakalaException;
	
	
	/**
	 * 检测小店是否已经是标准店，可升级为旗舰店
	 * @return
	 */
	public int checkShopType(TstoreApprove input) throws LakalaException;
	
	/**
	 * 检测小店是否已经是标准店，可升级为旗舰店
	 * @return
	 */
	public TstoreApprove getShopInfo(HttpServletRequest request, String mobile) throws LakalaException;
	
}
