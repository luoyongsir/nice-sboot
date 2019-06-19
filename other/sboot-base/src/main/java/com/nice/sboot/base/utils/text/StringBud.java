package com.nice.sboot.base.utils.text;

import com.nice.sboot.base.comm.Const;

/**
 * 可重用的StringBuilder, 节约StringBuilder内部的char[] <br/>
 * 对象较大时建议使用
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class StringBud {

	private static ThreadLocal<StringBuilder> localBud = ThreadLocal.withInitial(() -> new StringBuilder(512));

	/**
	 * 重置StringBuilder内部的writerIndex, 而char[]保留不动.
	 */
	public static StringBuilder current() {
		final StringBuilder bud = localBud.get();
		bud.setLength(0);
		return bud;
	}

	/**
	 * 字符串拼接
	 *
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> String join(final T... elements) {
		final StringBuilder buf = current();
		for (T t : elements) {
			if (t != null) {
				buf.append(t);
			}
		}
		return buf.toString();
	}

	/**
	 * 区别StringUtils的join方法，这里skipNulls
	 *
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(final Object[] array, final String separator) {
		if (array == null) {
			return null;
		}
		if (array.length == 0) {
			return Const.EMPTY;
		}
		String sep = separator;
		if (sep == null) {
			sep = Const.EMPTY;
		}
		final StringBuilder bud = current();
		// 是否拼接分隔符的标记
		boolean flag = false;
		for (Object o : array) {
			if (o != null) {
				if (flag) {
					bud.append(sep).append(o);
				} else {
					bud.append(o);
					flag = true;
				}
			}
		}
		return bud.toString();
	}

	private StringBud() {
	}
}
