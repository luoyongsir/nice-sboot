package com.nice.sboot.base.exception;

import com.nice.sboot.base.result.CodeMsgEnum;

/**
 * 异常处理公用类
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public class RunException extends RuntimeException {

	private static final long serialVersionUID = -5170682330334671069L;

	private int code;
	private String msg;

	public RunException() {
		super();
	}

	public RunException(Throwable cause) {
		super(cause);
	}

	public RunException(CodeMsgEnum codeMsgEnum) {
		super();
		this.code = codeMsgEnum.getCode();
		this.msg = codeMsgEnum.getMsg();
	}

	public RunException(int code, String message) {
		super(message);
		this.code = code;
		this.msg = message;
	}

	public RunException(String message) {
		super(message);
		this.code = CodeMsgEnum.FAIL.getCode();
		this.msg = CodeMsgEnum.FAIL.getMsg();
	}

	public RunException(String message, Throwable cause) {
		super(message, cause);
		this.code = CodeMsgEnum.FAIL.getCode();
		this.msg = CodeMsgEnum.FAIL.getMsg();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
