package com.nice.sboot.base.utils.collect;

import com.google.common.collect.ObjectArrays;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @see com.google.common.collect.ObjectArrays
 * @author luoyong
 * @date 2019/6/20 11:12
 */
public final class ArrayUtil {

	/**
	 * 从collection转为Array 建议用如下方式，性能最佳
	 * String[] array = list.toArray(new String[0]);
	 *
	 * 本函数等价于list.toArray(new String[0])
	 */
	public static <T> T[] toArray(Collection<T> col, Class<T> type) {
		return col.toArray((T[]) Array.newInstance(type, 0));
	}

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
