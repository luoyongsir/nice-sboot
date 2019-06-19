
package com.nice.sboot.base.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具集.
 *
 * 1. 获取无锁的ThreadLocalRandom, 性能较佳的SecureRandom
 *
 * 2. 保证没有负数陷阱，也能更精确设定范围的nextInt/nextLong/nextDouble (copy from Common Lang
 * RandomUtils，但默认使用性能较优的ThreadLocalRandom，并可配置其他的Random)
 *
 * 3. 随机字符串
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class RandomUtil {

	/////////////////// 获取Random实例//////////////
	/**
	 * 返回无锁的ThreadLocalRandom
	 */
	public static ThreadLocalRandom threadLocalRandom() {
		return ThreadLocalRandom.current();
	}

	/**
	 * 使用性能更好的SHA1PRNG, Tomcat的sessionId生成也用此算法.
	 *
	 * 但JDK7中，需要在启动参数加入 -Djava.security=file:/dev/./urandom （中间那个点很重要）
	 *
	 * 详见：《SecureRandom的江湖偏方与真实效果》http://calvin1978.blogcn.com/articles/securerandom.html
	 */
	public static SecureRandom secureRandom() {
		try {
			return SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			return new SecureRandom();
		}
	}

	////////////////// nextInt 相关/////////
	/**
	 * 返回0到Intger.MAX_VALUE的随机Int, 使用内置全局普通ThreadLocalRandom.
	 */
	public static int nextInt() {
		int n = threadLocalRandom().nextInt();
		if (n == Integer.MIN_VALUE) {
			n = 0;
		} else {
			n = Math.abs(n);
		}
		return n;
	}
	
	/**
	 * 返回0到max的随机Int, 使用内置全局普通Random.
	 */
	public static int nextInt(final int max) {
		return nextInt(0, max);
	}

	/**
	 * 返回min到max的随机Int, 使用内置全局普通ThreadLocalRandom.
	 *
	 * min必须大于0.
	 */
	public static int nextInt(final int min, final int max) {
		return nextInt(threadLocalRandom(), min, max);
	}

	/**
	 * 返回min到max的随机Int,可传入SecureRandom或ThreadLocalRandom.
	 *
	 * min必须大于0.
	 *
	 * JDK本身不具有控制两端范围的nextInt，因此参考Commons Lang RandomUtils的实现, 不直接复用是因为要传入Random实例
	 *
	 * @see org.apache.commons.lang3.RandomUtils#nextInt(int, int)
	 */
	public static int nextInt(final Random random, final int min, final int max) {
		Validate.isTrue(max >= min, "Start value must be smaller or equal to end value.");
		Validate.isTrue(min >= 0, "Both range values must be non-negative.");

		if (min == max) {
			return min;
		}
		return min + random.nextInt(max - min);
	}

	////////////////// long 相关/////////
	/**
	 * 返回0－Long.MAX_VALUE间的随机Long, 使用内置全局普通Random.
	 */
	public static long nextLong() {
		long n = threadLocalRandom().nextLong();
		if (n == Long.MIN_VALUE) {
			// corner case
			n = 0;
		} else {
			n = Math.abs(n);
		}
		return n;
	}

	/**
	 * 返回0－max间的随机Long, 使用内置全局普通Random.
	 */
	public static long nextLong(final long max) {
		return nextLong(0, max);
	}

	/**
	 * 返回min－max间的随机Long, 使用内置全局普通Random.
	 *
	 * min必须大于0.
	 */
	public static long nextLong(final long min, final long max) {
		return nextLong(threadLocalRandom(), min, max);
	}

	/**
	 * 返回min-max间的随机Long,可传入SecureRandom或ThreadLocalRandom.
	 *
	 * min必须大于0.
	 *
	 * JDK本身不具有控制两端范围的nextLong，因此参考Commons Lang RandomUtils的实现, 不直接复用是因为要传入Random实例
	 *
	 * @see org.apache.commons.lang3.RandomUtils#nextLong(long, long)
	 */
	public static long nextLong(final Random random, final long min, final long max) {
		Validate.isTrue(max >= min, "Start value must be smaller or equal to end value.");
		Validate.isTrue(min >= 0, "Both range values must be non-negative.");

		if (min == max) {
			return min;
		}
		return (long) (min + ((max - min) * random.nextDouble()));
	}

	///////// Double //////
	/**
	 * 返回0-Double.MAX_VALUE之间的double
	 */
	public static double nextDouble() {
		return nextDouble(threadLocalRandom(), 0, Double.MAX_VALUE);
	}

	/**
	 * 返回0-max之间的double
	 *
	 * 注意：与JDK默认返回0-1的行为不一致.
	 */
	public static double nextDouble(final double max) {
		return nextDouble(0, max);
	}

	/**
	 * 返回min-max之间的double
	 */
	public static double nextDouble(final double min, final double max) {
		return nextDouble(threadLocalRandom(), min, max);
	}

	/**
	 * 返回min-max之间的double
	 */
	public static double nextDouble(final Random random, final double min, final double max) {
		Validate.isTrue(max >= min, "Start value must be smaller or equal to end value.");
		Validate.isTrue(min >= 0, "Both range values must be non-negative.");

		if (min == max) {
			return min;
		}
		return min + ((max - min) * random.nextDouble());
	}

	//////////////////// String/////////
	/**
	 * 随机字母或数字，固定长度
	 */
	public static String randomStringFixLength(final int length) {
		return RandomStringUtils.random(length, 0, 0, true, true, null, threadLocalRandom());
	}

	/**
	 * 随机字母或数字，随机长度
	 */
	public static String randomStringRandomLength(final int minLength, final int maxLength) {
		return RandomStringUtils.random(nextInt(minLength, maxLength), 0, 0, true, true, null, threadLocalRandom());
	}

	/**
	 * 随机字母，固定长度
	 */
	public static String randomLetterFixLength(final int length) {
		return RandomStringUtils.random(length, 0, 0, true, false, null, threadLocalRandom());
	}

	/**
	 * 随机字母，随机长度
	 */
	public static String randomLetterRandomLength(final int minLength, final int maxLength) {
		return RandomStringUtils.random(nextInt(minLength, maxLength), 0, 0, true, false, null, threadLocalRandom());
	}

	/**
	 * 随机数字，固定长度
	 */
	public static String randomNumberFixLength(final int length) {
		return RandomStringUtils.random(length, 0, 0, false, true, null, threadLocalRandom());
	}

	/**
	 * 随机数字，随机长度
	 */
	public static String randomNumberRandomLength(final int minLength, final int maxLength) {
		return RandomStringUtils.random(nextInt(minLength, maxLength), 0, 0, false, true, null, threadLocalRandom());
	}

	/**
	 * 随机ASCII字符(含字母，数字及其他符号)，固定长度
	 */
	public static String randomAsciiFixLength(final int length) {
		return RandomStringUtils.random(length, 32, 127, false, false, null, threadLocalRandom());
	}

	/**
	 * 随机ASCII字符(含字母，数字及其他符号)，随机长度
	 */
	public static String randomAsciiRandomLength(final int minLength, final int maxLength) {
		return RandomStringUtils.random(nextInt(minLength, maxLength), 32, 127, false, false, null,
				threadLocalRandom());
	}

	private RandomUtil() {
	}
}
