package com.lakala.util;

import java.math.BigDecimal;

public class BussConst {
	/**
	 * 程序异常提示信息
	 * */
	public final static String SYSTEM_ERROR = "系统出现异常！";
	
	public final static String PATH_SEPARATOR = "/";

	/**
	 * 操作结果
	 */
	public final static String OPERATION_STATUS_SUCEESS = "SUCEESS";//成功
	public final static String OPERATION_STATUS_FAIL = "FAIL";//失败
	
	/**
	 * 图片用途
	 */
	public final static int IMAGE_TARGETTYPE_SUPPLIER_GOODS = 1;//供应商商品
	public final static int IMAGE_TARGETTYPE_PROPERTY = 2;//属性模块
	public final static int IMAGE_TARGETTYPE_POOL_GOODS = 3;//商品池商品
	public final static int IMAGE_TARGETTYPE_ORDER_QSD = 4;//已发货订单签收单
	public final static int IMAGE_TARGETTYPE_AD = 5;//广告图片
	public final static int IMAGE_TARGETTYPE_USER = 6;//用户相关图片
	public final static int IMAGE_TARGETTYPE_SHOP_HEADER = 7;//小店门头
	public final static int IMAGE_TARGETTYPE_SHOP = 8;//店铺图片
	public final static int IMAGE_TARGETTYPE_MSG = 9;//消息图片
	
	/**
	 * 图片服务器信息
	 * */
	public final static String HOST = "10.1.80.53";
	public final static int PORT = 22;
	public final static String USERNAME = "lklsysrun";
	public final static String PASSWORD = "xtyxb^&*123";
	public final static String PATH = "/home/lklsysrun/workspace/tomcat6/webapps/img/";//父级目录
	public final static String SFTP_URL = "sftp://lklsysrun:xtyxb^&*123@10.1.80.53:22/home/lklsysrun/workspace/tomcat6/webapps/img";
	public final static String HTTP_URL = "http://wxser.lakalaec.com:8080/img/";
	
	
	/**
	 * 商品性质
	 */
	public final static int GOODS_TYPE_GOODS = 202;//正常商品
	public final static int GOODS_TYPE_GIFT = 204;//赠品
	
	/**
	 * 商品日志：商品数据来源
	 */
	public static final int GOODS_LOG_OPREATESRC_PROVIDERADD = 443;
	public static final int GOODS_LOG_OPREATESRC_PROVIDERMOD = 444;
	public static final int GOODS_LOG_OPREATESRC_ADMINMOD = 445;
	public static final int GOODS_LOG_OPREATESRC_PROVIDERDEL = 450;
	
	/**
	 * 商品日志：操作类型
	 */
	public static final int GOODS_LOG_ACTIONTYPE_NEW = 447;
	public static final int GOODS_LOG_ACTIONTYPE_MOD = 448;
	public static final int GOODS_LOG_ACTIONTYPE_DEL = 449;


	/**
	 * 自营商品：删除标记
	 */
	public static final int GOODS_DELFLAG_VALID = 455;
	public static final int GOODS_DELFLAG_INVALID = 456;
	
	/**
	 * 自营商品：上架状态
	 */
	public static final int GOODS_GOODSSTATUS_ONSALE = 208;
	public static final int GOODS_GOODSSTATUS_DOWNSALE = 209;
	
	/**
	 * 商品自营平台标识
	 */
	public static final int GOODS_PLATORSELF_PLAT = 453;
	public static final int GOODS_PLATORSELF_SELF  = 452;
	
	/**
	 * APP 渠道ID
	 */
	public static final int APP_CHANNELID = 11;
	
	/**
	 * APP 2B\2C 频道ID
	 */
	public static final int APP_2B_FREQUENCYID = 43;
	public static final int APP_2C_FREQUENCYID = 45;
	
	/**
	 * 操作系统类型
	 */
	public static final String OS_TYPE_IOS = "ios";
	public static final String OS_TYPE_ANDROID = "android";
	
	/**
	 * app商品审批发起人
	 */
	public static final String APP2B_GOODS_PROPOSER = "1103";
	
	/**
	 * Mongo 接口地址
	 */
	public static final String MOD_GOODS = "/admin/goods/modify";
	public static final String MOD_STOCK = "/admin/goods/store/modify";
	public static final String MOD_SKU = "/admin/goods/modify/sku";
	
	/**
	 * 默认库存：1000000
	 */
	public static final BigDecimal DEFAULT_STOCK = new BigDecimal(1000000);
	
}
	