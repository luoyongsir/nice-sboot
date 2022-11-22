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
		return result(CodeMsgEnum.FAIL);
	}

	/**
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> fail(String msg) {
		return result(CodeMsgEnum.FAIL.getCode(), msg);
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
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(int code, String msg) {
		return result(code, msg, null);
	}

	/**
	 * @param codeMsg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(CodeMsg codeMsg) {
		return result(codeMsg, null);
	}

	/**
	 * @param codeMsg
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(CodeMsg codeMsg, T data) {
		return result(codeMsg.getCode(), codeMsg.getMsg(), data);
	}

	/**
	 * @param code
	 * @param msg
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> result(int code, String msg, T data) {
		return new Result<>(code, msg, data);
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

	private ResultUtil() {
	}
}
