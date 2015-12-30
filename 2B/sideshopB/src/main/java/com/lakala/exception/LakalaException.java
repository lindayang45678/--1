package com.lakala.exception;

/**
 * 
 * 数据层异常
 * 
 * @author PH.LI
 *
 */
public class LakalaException extends Exception {

	private static final long serialVersionUID = 7587143041735651102L;

	public LakalaException() {
		super();
	}

	public LakalaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 把异常信息存放到构造函数里
	 * 
	 * @param arg0
	 */
	public LakalaException(String message) {
		super(message);
	}

	public LakalaException(Throwable cause) {
		super(cause);
	}
}
