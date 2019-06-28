package com.nice.sboot.base.utils.collect;

import java.util.ArrayList;
import java.util.List;

/**
 * @see com.google.common.collect.Lists
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class ListUtil {

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(List<?> list) {
		return (list == null) || list.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * 根据等号左边的类型, 构造类型正确的ArrayList.
	 */
	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<>();
	}

	/**
	 * 根据等号左边的类型和预期的size, 构造类型正确的ArrayList.
	 */
	public static <E> ArrayList<E> newArrayList(int initialCapacity) {
		return new ArrayList<>(initialCapacity);
	}

	private ListUtil() {
	}
}
