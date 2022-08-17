package com.nice.sboot.base.result;

/**
 * Result 封装
 * @author luoyong
 * @date 2019/6/4 20:53
 */
public final class ResultUtil {

	/**
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> fail() {
		return result(CodeMsgEnum.FAIL, null);
	}

	/**
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> fail(String msg) {
		return result(CodeMsgEnum.FAIL.getCode(), msg, null);
	}

	/**
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> success() {
		return result(CodeMsgEnum.SUCCESS);
	}

	/**
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> success(T data) {
		return result(CodeMsgEnum.SUCCESS, data);
	}

	/**
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(int code) {
		return result(code, null, null);
	}

	/**
	 * @param code
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(int code, String msg) {
		return result(code, msg, null);
	}

	/**
	 * @param codeMsgEnum
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(CodeMsgEnum codeMsgEnum) {
		return result(codeMsgEnum, null);
	}

	/**
	 * @param codeMsgEnum
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(CodeMsgEnum codeMsgEnum, T data) {
		return result(codeMsgEnum.getCode(), codeMsgEnum.getMsg(), data);
	}

	/**
	 * @param code
	 * @param msg
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(int code, String msg, T data) {
		if (msg == null) {
			return new Result<>(code, CodeMsgEnum.getMsg(code), data);
		} else {
			return new Result<>(code, msg, data);
		}
	}

	/**
	 * @param status
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> judge(boolean status) {
		if (status) {
			return success();
		} else {
			return fail();
		}
	}
}
