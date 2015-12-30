package com.lakala.module.user.vo;


/**
 * 用户信息
 * @author ph.li
 *
 */
public class UserInfoOutput{

	/** 验证令牌 */
	private String token;
	/** 用户名 */
	private String userName;
	/** 用户类型 */
	private Integer userType;
	/** 商户类型 */
	private Integer bizType;
	/** 电商网点编号 */
	private String ecNetNo;
	/** PSAM */
	private String psam;
	/** 支付验证令牌 */
	private String payToken;
	/** 用户密码 */
	private String pwd;
	/** JS加密参数 */
	private String modulus;
	private String exponent;
	
	/** 客户经理渠道ID  */
	private String agentId;
	
	/** 渠道类型  */
	private Integer channelType;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getEcNetNo() {
		return ecNetNo;
	}

	public void setEcNetNo(String ecNetNo) {
		this.ecNetNo = ecNetNo;
	}

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}

	public String getPayToken() {
		return payToken;
	}

	public void setPayToken(String payToken) {
		this.payToken = payToken;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

}
