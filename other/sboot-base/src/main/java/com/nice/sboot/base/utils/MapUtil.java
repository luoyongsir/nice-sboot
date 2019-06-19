package com.nice.sboot.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class MapUtil {

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(final Map<?, ?> map) {
		return (map == null) || map.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(final Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 根据等号左边的类型, 构造类型正确的HashMap.
	 *
	 */
	public static <K, V> Map<K, V> newHashMap() {
		return new HashMap<>();
	}

	/**
	 * 根据等号左边的类型和key的预期size, 构造类型正确的HashMap.
	 *
	 */
	public static <K, V> Map<K, V> newHashMap(int expectedSize) {
		return newHashMap(expectedSize, 0.75F);
	}

	/**
	 * 根据等号左边的类型和key的预期size, 构造类型正确的HashMap.
	 *
	 * 注意HashMap中有0.75的加载因子的影响, 需要进行运算后才能正确初始化HashMap的大小.
	 *
	 * 加载因子也是HashMap中减少Hash冲突的重要一环，如果读写频繁，总记录数不多的Map，可以比默认值0.75进一步降低，建议0.5
	 *
	 */
	public static <K, V> Map<K, V> newHashMap(int expectedSize, float loadFactor) {
		int initialCapacity = (int) ((double) expectedSize / loadFactor + 1.0F);
		return new HashMap<>(initialCapacity, loadFactor);
	}

	private MapUtil() {
	}
}
