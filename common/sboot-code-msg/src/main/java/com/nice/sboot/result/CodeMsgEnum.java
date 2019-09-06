package com.nice.sboot.result;
// @formatter:off
import java.util.HashMap;
import java.util.Map;

/**
 * 该类在 maven clean 阶段根据 code-msg.properties 自动生成，禁止手动修改
 * 如果有修改 code-msg.properties 请执行 maven clean
 * @author luoyong
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
	HTTP_500(500, "服务器内部错误");

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
