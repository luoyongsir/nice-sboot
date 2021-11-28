package com.nice.sboot.base.utils.text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class UrlUtil {

	private UrlUtil() {
	}

	/**
	 * 默认 UTF-8编码
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		try {
			return URLEncoder.encode(s, Charsets.UTF_8_NAME);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * 默认 UTF-8编码
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		try {
			return URLDecoder.decode(s, Charsets.UTF_8_NAME);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}
}
