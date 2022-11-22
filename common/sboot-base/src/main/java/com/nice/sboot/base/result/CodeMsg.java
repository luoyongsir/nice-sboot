package com.nice.sboot.base.result;

/**
 * @author 罗勇
 * @date 2019/6/19
 */
public interface CodeMsg {

	/**
	 * 异常code
	 *
	 * @return
	 */
	int getCode();

	/**
	 * 异常msg
	 *
	 * @return
	 */
	String getMsg();
}
