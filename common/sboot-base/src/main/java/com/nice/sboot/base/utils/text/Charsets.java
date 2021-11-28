package com.nice.sboot.base.utils.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 尽量使用Charsets.UTF_8而不是"UTF-8"，减少JDK里的Charset查找消耗.
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class Charsets {

	public static final String GBK_NAME = "GBK";
	public static final String UTF_8_NAME = "UTF-8";

	public static final Charset GBK = Charset.forName("GBK");
	public static final Charset UTF_8 = StandardCharsets.UTF_8;
	public static final Charset UTF_16 = StandardCharsets.UTF_16;
	public static final Charset US_ASCII = StandardCharsets.US_ASCII;
	public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

	private Charsets() {
	}
}
