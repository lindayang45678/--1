package com.lakala.module.user.vo;

import com.lakala.module.comm.ObjectInput;



/**
 * 用户登录输入
 * @author ph.li
 *
 */
public class UserLoginInput extends ObjectInput{

	/** 用户密码 */
	private String pwd;
	
	private String terminalCode;
	
	private String terminalType;
	
	private String json;

	private String encFlag = ""; //加密类型
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getEncFlag() {
		return encFlag;
	}

	public void setEncFlag(String encFlag) {
		this.encFlag = encFlag;
	}
	
}
