package com.lakala.util;
/**
 * 业务返回编码
 * @author ph.li
 *
 */
public class ReturnMsg {


	/** 返回成功编码 */
	public final static String CODE_SUCCESS = "000000";
	/** 内部错误  */
	public final static String CODE_ERR_DEFAULT = "000001";
	/** 数据检验不通过 */
	public final static String CODE_ERR_000002 = "000002";

    /** 批发进货验证及数据提示 */
    public final static String CODE_ERR_000003 = "000003";
    
    /** 更新版本异常 **/
    public final static String CODE_ERR_000007 = "000007";

	/** 系统出错缺省消息提示 */
	public final static String MSG_ERR_DEFAULT = "系统忙，请稍后再试";

	public final static String MSG_SUCCESS_DEFAULT="SUCCESS";
	
	public final static String MSG_PARAMS_ERROR="参数不合法";
	public final static String MSG_COUPON_ACTIVE_OK="激活成功";
	public final static String MSG_OUPON_ACTIVE_ERROR="激活失败";
	
	
	public final static String MSG_ORDERCANCE_000001="该订单的父级订单已参加营销活动不允许取消，请联系400客服!";
	
	public final static String MSG_ORDERCANCE_000002="操作失败!";
	
	
	/** 用户中心-返回Code */
	/** 很抱歉，该手机号未开通通身边小店，请先开通！ */
	public final static String CODE_USER_100001 = "100001";
	/**  */
	public final static String CODE_USER_100002 = "100002";
	/**  */
	public final static String CODE_USER_100003 = "100003";
	/** 很抱歉，您的手机号或密码错误，请重新输入！ */
	public final static String CODE_USER_100004 = "100004";
	/** 该手机号已经登录！ */
	public final static String CODE_USER_100005 = "100005";
	/** 密码必须由数字或者字母或者数字字母的组合，且长度必须大于等于6小于等于20! */
	public final static String CODE_USER_100006 = "100006";
	/** 很抱歉，您输入的验证码错误，请确认后重新输入！ */
	public final static String CODE_USER_100007 = "100007";
	/** 登录成功！ */
	public final static String CODE_USER_100008 = "100008";
	/** 激活成功！ */
	public final static String CODE_USER_100009 = "100009";
	/** 登出成功！ */
	public final static String CODE_USER_100010 = "100010";
	/** webservice服务出错了！ */
	public final static String CODE_USER_100011 = "100011";
	/** 更新密码成功 */
	public final static String CODE_USER_100012 = "100012";
	/** 允许激活-激活成功，并可设置登录密码 */
	public final static String CODE_USER_100013 = "100013";
	/** 该手机号已激活，无需重复激活 */
	public final static String CODE_USER_100014 = "100014";
	/** 恭喜您，激活成功！     您可用拉卡拉的账号密码登录！ */
	public final static String CODE_USER_100015 = "100015";
	/** 返回密码成功 */
	public final static String CODE_USER_100016 = "100016";
	/** 拒绝访问 */
	public final static String CODE_USER_100017 = "100017";
	/** 开通业务-集团未注册 */
	public final static String CODE_USER_100018 = "100018";
	/** 开通编码输入错误，请重新输入*/
	public final static String CODE_USER_100019 = "100019";
	/** 该手机号已开通，请到登陆页登陆！*/
	public final static String CODE_USER_100020 = "100020";
	/** 开通业务-集团已经注册 */
	public final static String CODE_USER_100021 = "100021";
	/** 很抱歉，该地址正在维护，暂不能开通，请1天后再开通！*/
	public final static String CODE_USER_100022 = "100022";
}
