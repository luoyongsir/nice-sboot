package com.nice.sboot.base.result;
// @formatter:off
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 罗勇
 * @date: 2022-08-17 11:16
 */
public enum CodeMsgEnum {
	// 枚举
	FAIL(0, "失败"),
	SUCCESS(1, "成功"),
	HTTP_200(200, "成功"),
	HTTP_400(400, "请求出错"),
	HTTP_401(401, "未授权"),
	HTTP_403(403, "禁止访问"),
	HTTP_404(404, "访问地址不存在"),
	HTTP_412(412, "请求参数错误"),
	HTTP_500(500, "服务器内部错误"),

	USER_NOT_EXIST(10201, "用户不存在"),
	USER_ACCOUNT_LOCKED(10202, "用户账户被冻结"),
	USER_ACCOUNT_INVALID(10203, "用户账户已作废"),

	USERNAME_OR_PASSWORD_ERROR(10210, "用户名或密码错误"),
	PASSWORD_ENTER_EXCEED_LIMIT(10211, "用户输入密码次数超限"),
	CLIENT_AUTHENTICATION_FAILED(10212, "客户端认证失败"),
	TOKEN_INVALID_OR_EXPIRED(10230, "token无效或已过期"),
	TOKEN_ACCESS_FORBIDDEN(10231, "token已被禁止访问"),

	SERVER_BUSY(10001, "服务端忙，请稍后重试"),
	PARAM_ERROR(10021, "参数校验失败【{0}】"),
	// 业务告警消息
	MSG_ALARM(10031, "{0}"),
	LINK_FAILURE(10032, "链接已失效，请联系管理人员！");

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

	private static Map<Integer, String> msgMap;
	static {
		msgMap = new HashMap<>((int) (CodeMsgEnum.values().length / 0.75) + 1);
		for (CodeMsgEnum codeMsgEnum : CodeMsgEnum.values()) {
			msgMap.put(codeMsgEnum.getCode(), codeMsgEnum.getMsg());
		}
	}

	/**
	 * 根据 code 获取 msg
	 */
	public static String getMsg(int code) {
		return msgMap.get(code);
	}
}// @formatter:on
