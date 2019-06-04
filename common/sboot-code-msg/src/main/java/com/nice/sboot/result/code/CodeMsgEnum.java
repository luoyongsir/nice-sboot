package com.nice.sboot.result.code;

/**
 * @author luoyong
 */
public enum CodeMsgEnum {
	// 枚举
	FAIL(0, "失败"),
	HTTP_200(200, "成功"),
	HTTP_400(400, "请求出错"),
	HTTP_401(401, "未授权"),
	HTTP_403(403, "禁止访问"),
	HTTP_404(404, "访问地址不存在"),
	HTTP_412(412, "请求参数错误"),
	HTTP_500(500, "服务器内部错误"),
	SUCCESS(1, "成功");

	private int code;
	private String msg;

	CodeMsgEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 根据 code 获取消息
	 */
	public static String getMsg(int code) {
		for (CodeMsgEnum codeMsgEnum : CodeMsgEnum.values()) {
			if (codeMsgEnum.getCode() == code) {
				return codeMsgEnum.getMsg();
			}
		}
		return null;
	}
}

