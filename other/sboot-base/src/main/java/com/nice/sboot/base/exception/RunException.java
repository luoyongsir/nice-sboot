package com.nice.sboot.base.exception;

/**
 * 异常处理公用类
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public class RunException extends RuntimeException {

	private static final long serialVersionUID = -5170682330334671069L;

	public RunException() {
		super();
	}

	public RunException(Throwable cause) {
		super(cause);
	}

	public RunException(String message) {
		super(message);
	}

	public RunException(String message, Throwable cause) {
		super(message, cause);
	}

}
