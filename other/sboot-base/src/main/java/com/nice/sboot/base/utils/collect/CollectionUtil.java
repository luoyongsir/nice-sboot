package com.nice.sboot.base.utils.collect;

import java.util.Collection;

/**
 * @author luoyong-014
 * @date 2019/6/20 11:04
 */
public final class CollectionUtil {

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

	private CollectionUtil() {
	}
}
