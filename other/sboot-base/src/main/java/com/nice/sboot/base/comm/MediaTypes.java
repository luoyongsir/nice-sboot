package com.nice.sboot.base.comm;

/**
 * 带UTF-8 charset 定义的MediaType.
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class MediaTypes {

	public static final String XML = "application/xml";
	public static final String XML_UTF_8 = "application/xml; charset=UTF-8";

	public static final String JSON = "application/json";
	public static final String JSON_UTF_8 = "application/json; charset=UTF-8";

	public static final String JAVASCRIPT = "application/javascript";
	public static final String JAVASCRIPT_UTF_8 = "application/javascript; charset=UTF-8";

	public static final String X_GZIP = "application/x-gzip";

	public static final String XHTML_XML = "application/xhtml+xml";
	public static final String XHTML_XML_UTF_8 = "application/xhtml+xml; charset=UTF-8";

	public static final String TEXT_PLAIN = "text/plain";
	public static final String TEXT_PLAIN_UTF_8 = "text/plain; charset=UTF-8";

	public static final String TEXT_XML = "text/xml";
	public static final String TEXT_XML_UTF_8 = "text/xml; charset=UTF-8";

	public static final String TEXT_HTML = "text/html";
	public static final String TEXT_HTML_UTF_8 = "text/html; charset=UTF-8";

	public static final String FORM_URL = "application/x-www-form-urlencoded";
	public static final String FORM_URL_UTF_8 = "application/x-www-form-urlencoded; charset=UTF-8";

	private MediaTypes() {
	}
}
