package com.nice.sboot.base.utils.num;

import com.nice.sboot.base.exception.RunException;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/**
 * BigDecimal 的处理
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class NumUtil {

	private NumUtil() {
	}

	/**
	 * Entry points of {@link NumUtil} <br/>
	 * <br/>
	 * Example usage:
	 *
	 * <pre>
	 * <code>
	 *      is(three).eq(four); //Equal
	 * 		is(two).gt(two);    //Greater than
	 * 		is(two).gte(one);   //Greater than equal
	 * 		is(three).lt(two);  //Less than
	 * 		is(three).lte(two); //Less than equal
	 *
	 *      is(three).notEq(four); //Not Equal
	 * 		is(two).notGt(two);    //Not Greater than
	 * 		is(two).notGte(one);   //Not Greater than equal
	 * 		is(three).notLt(two);  //Not Less than
	 * 		is(three).notLte(two); //Not Less than equal
	 *
	 *      is(three).isZero();
	 *      is(three).notZero();
	 *      is(three).isPositive(); // greater than zero
	 *      is(three).isNegative(); // less than zero
	 *      is(three).isNonPositive(); //less than or equal zero
	 *      is(three).isNonNegative(); //greater than or equal zero
	 * </code>
	 * </pre>
	 *
	 * @param decimal
	 *        	{@link BigDecimal}
	 *
	 * @return {@link BigDecimalWrapper}
	 *
	 */
	public static BigDecimalWrapper is(BigDecimal decimal) {
		return new BigDecimalWrapper(decimal);
	}

	/**
	 * Entry points of {@link NumUtil} <br/>
	 * <br/>
	 * Example usage:
	 *
	 * <pre>
	 * <code>
	 *      is(three).eq(four); //Equal
	 * 		is(two).gt(two);    //Greater than
	 * 		is(two).gte(one);   //Greater than equal
	 * 		is(three).lt(two);  //Less than
	 * 		is(three).lte(two); //Less than equal
	 *
	 *      is(three).notEq(four); //Not Equal
	 * 		is(two).notGt(two);    //Not Greater than
	 * 		is(two).notGte(one);   //Not Greater than equal
	 * 		is(three).notLt(two);  //Not Less than
	 * 		is(three).notLte(two); //Not Less than equal
	 *
	 *      is(three).isZero();
	 *      is(three).notZero();
	 *      is(three).isPositive(); // greater than zero
	 *      is(three).isNegative(); // less than zero
	 *      is(three).isNonPositive(); //less than or equal zero
	 *      is(three).isNonNegative(); //greater than or equal zero
	 *
	 *      is(three).isNullOrZero(); //is null or zero
	 *      is(three).notNullOrZero(); //not null or zero
	 * </code>
	 * </pre>
	 *
	 * @param val
	 *        	{@link double}
	 *
	 * @return {@link BigDecimalWrapper}
	 *
	 */
	public static BigDecimalWrapper is(double val) {
		return is(BigDecimal.valueOf(val));
	}

	//////// 创建 BigDecimal 对象 ////////

	public static BigDecimal of(long val) {
		return BigDecimal.valueOf(val);
	}

	public static BigDecimal of(double val) {
		return BigDecimal.valueOf(val);
	}

	public static BigDecimal of(String val) {
		if (NumberUtils.isCreatable(val)) {
			return new BigDecimal(val);
		}
		throw new RunException("字符串" + val + "无法转换成BigDecimal");
	}

	/**
	 * 分转元
	 */
	public static String toYuan(int val) {
		return of(val).divide(of(100), 2, RoundingMode.HALF_UP).toString();
	}

	/**
	 * 元转分
	 */
	public static int toFen(String val) {
		return of(val).multiply(of(100)).intValue();
	}

	/**
	 * 四舍五入保留两位小数
	 */
	public static <T extends Number> String format(T t) {
		return String.format(Locale.ROOT, "%.2f", t.doubleValue());
	}

	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点相乘，精度计算后的数据.
	 * @Desc 对double数据进行相乘并取精度.
	 */
	public static double mul(double d1, double d2) {
		try {
			BigDecimal bd1 = of(d1);
			BigDecimal bd2 = of(d2);
			return bd1.multiply(bd2).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param d1
	 *            double数据1
	 * @param d2
	 *            double数据2
	 * @return 浮点相除，精度计算后的数据.
	 * @Desc 对double数据进行相除并取精度.
	 */
	public static double div(double d1, double d2, int scale) {
		try {
			BigDecimal bd1 = of(d1);
			BigDecimal bd2 = of(d2);
			return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
}
