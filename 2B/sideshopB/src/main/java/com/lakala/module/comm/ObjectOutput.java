package com.lakala.module.comm;


/**
 * 输出父类
 * @author ph.li
 * @param <K>
 * @param <V>
 *
 */
public class ObjectOutput<T>{

	
	/** 处理返回值  */
	public String _ReturnCode;
	/** 失败的返回信息 */
	public String _ReturnMsg;
	/** 数据区 */
	public  T _ReturnData;
	

	public T get_ReturnData() {
		return _ReturnData;
	}
	public void set_ReturnData(T _ReturnData) {
		this._ReturnData = _ReturnData;
	}
	public String get_ReturnCode() {
		return _ReturnCode;
	}
	public void set_ReturnCode(String _ReturnCode) {
		this._ReturnCode = _ReturnCode;
	}
	public String get_ReturnMsg() {
		return _ReturnMsg;
	}
	public void set_ReturnMsg(String _ReturnMsg) {
		this._ReturnMsg = _ReturnMsg;
	}


}
