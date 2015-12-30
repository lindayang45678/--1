package com.lakala.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {

	/**
	 * BigDecimal对象为NULL时返回 0
	 * 
	 * @param val
	 * @return
	 */
	public static BigDecimal IsNullToZero(BigDecimal val) {
		if (val == null) {
			return BigDecimal.valueOf(0);
		} else {
			return val;
		}
	}

	public static DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * BigDecimal对象 保留二位有效位数 为空返回0.00
	 * 
	 * @param val
	 * @return
	 */
	public static String decimalTwoPoint(BigDecimal val) {
		if (val == null) {
			return df.format(0);
		} else {
			return df.format(val);
		}
	}

	/**
	 * Integer对象为NULL时返回 0
	 * 
	 * @param val
	 * @return
	 */
	public static Integer IsNullToZero(Integer val) {
		if (val == null) {
			return 0;
		} else {
			return val;
		}
	}

	/**
	 * Integer对象为NULL时返回 0
	 * 
	 * @param val
	 * @return
	 */
	public static Long IsNullToZero(Long val) {
		if (val == null) {
			return 0L;
		} else {
			return val;
		}
	}
}
