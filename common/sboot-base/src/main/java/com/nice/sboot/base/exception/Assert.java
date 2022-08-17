package com.nice.sboot.base.exception;

import com.nice.sboot.base.result.CodeMsgEnum;

/**
 * @author: 罗勇
 * @date: 2022-04-17 20:35
 */
public abstract class Assert {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw ExceptionFactory.create(CodeMsgEnum.FAIL.getCode(), message);
		}
	}
}
