package com.nice.sboot.result;

import java.io.Serializable;

/**
 * 支付返回结果
 * @author luoyong
 * @date 2019/6/4 20:52
 * @param <T>
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -5362334624604799724L;
	/** 结果编码 */
	private int code;
	/** code对应的文字描述 */
	private String msg;
	/** 传输数据对象 */
	private T data;

	public Result() {
	}

	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(int code, String msg, T res) {
		this.code = code;
		this.msg = msg;
		this.data = res;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
