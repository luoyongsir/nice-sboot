package com.nice.sboot.base.exception;

import com.nice.sboot.base.result.CodeMsg;

/**
 * 异常Factory
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class ExceptionFactory {

	private ExceptionFactory() {
	}

	public static RunException create(Throwable cause) {
		return new RunException(cause);
	}

	public static RunException create(CodeMsg codeMsg) {
		return new RunException(codeMsg);
	}

	public static RunException create(CodeMsg codeMsg, String message) {
		return new RunException(codeMsg, message);
	}

	public static RunException create(String msg) {
		return new RunException(msg);
	}

	public static RunException create(String msg, Throwable cause) {
		return new RunException(msg, cause);
	}
}
