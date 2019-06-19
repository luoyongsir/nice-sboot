package com.nice.sboot.base.utils;

import com.nice.sboot.base.comm.Const;
import com.nice.sboot.base.utils.text.StringBud;

import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class ThreadUtil {

	/**
	 * sleep等待, 单位为毫秒.
	 */
	public static void sleep(long durationMillis) {
		try {
			Thread.sleep(durationMillis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * sleep等待.
	 */
	public static void sleep(long duration, TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(duration));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 通过StackTrace，获得调用者的类名.
	 */
	public static String getCallerClass() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= 4) {
			StackTraceElement element = stacktrace[3];
			return element.getClassName();
		} else {
			return Const.EMPTY;
		}
	}

	/**
	 * 通过StackTrace，获得调用者的"类名.方法名()"
	 */
	public static String getCallerMethod() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= 4) {
			StackTraceElement element = stacktrace[3];
			return StringBud.join(element.getClassName(), '.', element.getMethodName(), "()");
		} else {
			return Const.EMPTY;
		}
	}

	/**
	 * 通过StackTrace，获得调用者的类名.
	 */
	public static String getCurrentClass() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= 3) {
			StackTraceElement element = stacktrace[2];
			return element.getClassName();
		} else {
			return Const.EMPTY;
		}
	}

	/**
	 * 通过StackTrace，获得当前方法的"类名.方法名()"
	 */
	public static String getCurrentMethod() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= 3) {
			StackTraceElement element = stacktrace[2];
			return StringBud.join(element.getClassName(), '.', element.getMethodName(), "()");
		} else {
			return Const.EMPTY;
		}
	}

	private ThreadUtil() {
	}
}
