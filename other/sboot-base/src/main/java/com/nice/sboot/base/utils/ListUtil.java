package com.nice.sboot.base.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class ListUtil {

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 根据等号左边的类型, 构造类型正确的ArrayList.
	 */
	public static <E> List<E> newArrayList() {
		return new ArrayList<>();
	}

	/**
	 * 根据等号左边的类型和预期的size, 构造类型正确的ArrayList.
	 */
	public static <E> List<E> newArrayList(int initialCapacity) {
		return new ArrayList<>(initialCapacity);
	}

	private ListUtil() {
	}
}
