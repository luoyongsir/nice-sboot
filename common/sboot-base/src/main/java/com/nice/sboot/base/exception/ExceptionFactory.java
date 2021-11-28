package com.nice.sboot.base.exception;

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

	public static RunException create(String message) {
		return new RunException(message);
	}

	public static RunException create(String message, Throwable cause) {
		return new RunException(message, cause);
	}
}
