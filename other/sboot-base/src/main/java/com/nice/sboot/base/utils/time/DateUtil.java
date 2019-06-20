package com.nice.sboot.base.utils.time;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期公用类
 *
 * org.apache.commons.lang3.time.DateFormatUtils
 * org.apache.commons.lang3.time.DateUtils
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class DateUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class.getName());

	public static final String DATE = "yyyy-MM-dd";
	public static final String DATEMIN = "yyyy-MM-dd HH:mm";
	public static final String DATENUM = "yyyyMMddHHmmss";
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * LocalDate 转 Date
	 * @param localDate
	 * @return
	 */
	public static Date from(final LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * LocalDateTime 转 Date
	 * @param localDateTime
	 * @return
	 */
	public static Date from(final LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 日期字符串多种格式兼容性转换日期对象<br />
	 * 注：不建议使用，性能不好，只有当不知道日期格式时，可以尝试使用
	 * @param dateStr
	 * @return
	 */
	public static Date parse(final String dateStr) {
		try {
			return DateUtils.parseDate(dateStr, PARSE_PATTERNS);
		} catch (ParseException e) {
			LOG.error("日期parse出错：", e);
		}
		return null;
	}

	/**
	 * 日期字符串转换日期对象
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parse(final String dateStr, final String pattern) {
		try {
			return FastDateFormat.getInstance(pattern).parse(dateStr);
		} catch (ParseException e) {
			return parse(dateStr);
		}
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(final Date date, final String pattern) {
		return FastDateFormat.getInstance(pattern).format(date);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(final long date, final String pattern) {
		return FastDateFormat.getInstance(pattern).format(date);
	}

	/** 多种格式兼容解析日期*/
	private static final String[] PARSE_PATTERNS = new String[]{
			// 中划线分隔符
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss",
			// 斜杠分隔符
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss",
			// 中文风格
			"yyyy年MM月dd日", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时mm分ss秒",
			// 时分秒
			"HH", "HH:mm", "HH:mm:ss",
			// 无分隔符
			"yyyyMMdd", "yyyyMMddHHmm", "yyyyMMddHHmmss"};

	private DateUtil() {
	}
}
