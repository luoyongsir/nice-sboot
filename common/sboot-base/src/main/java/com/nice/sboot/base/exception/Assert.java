package com.nice.sboot.base.exception;

import com.nice.sboot.base.result.CodeMsgEnum;

/**
 * @author: 罗勇
 * @date: 2022-04-17 20:35
 */
public final class Assert {

	public static void isTrue(boolean expression) {
		isTrue(expression, CodeMsgEnum.FAIL.getMsg());
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw ExceptionFactory.create(CodeMsgEnum.FAIL, message);
		}
	}

	private Assert() {
	}
}
