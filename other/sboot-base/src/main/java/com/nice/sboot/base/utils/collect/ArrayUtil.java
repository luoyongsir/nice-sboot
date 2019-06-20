package com.nice.sboot.base.utils.collect;

import com.google.common.collect.ObjectArrays;

/**
 * @author luoyong-014
 * @date 2019/6/20 11:12
 */
public final class ArrayUtil {

	/**
	 * 添加元素到数组头.
	 */
	public static <T> T[] concat(T element, T[] array) {
		return ObjectArrays.concat(element, array);
	}

	/**
	 * 添加元素到数组末尾.
	 */
	public static <T> T[] concat(T[] array, T element) {
		return ObjectArrays.concat(array, element);
	}

	private ArrayUtil() {
	}
}
