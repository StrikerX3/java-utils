package com.ivan.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Additional mathematical functions to complement <code>java.lang.Math</code>.
 */
public final class MathEx {
	private MathEx() {
	}

	public static final double HALF_PI = Math.PI / 2.0;

	public static final double TAU = 2 * Math.PI;

	// the golden ratio
	public static final double PHI = 1.618033988749895;

	//////
	// wave functions
	//

	public static double sineWave(final double x) {
		return Math.sin(x * TAU);
	}

	public static double squareWave(final double x) {
		return x % 1 < 0.5 ? 1 : -1;
	}

	public static double sawWave(final double x) {
		return 1 - (x + 0.5) % 1 * 2;
	}

	public static double triangleWave(final double x) {
		return 1 - Math.abs((x * 2 + 0.5) % 2 - 1) * 2;
	}

	public static double noiseWave() {
		return Math.random() * 2 - 1;
	}

	//////
	// random between
	//

	/**
	 * Generates a random number between min and max.
	 *
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return a random number between min and max
	 */
	public static double randomBetween(final double min, final double max) {
		return Math.random() * (max - min) + min;
	}

	/**
	 * Generates a random number between min and max using the given random
	 * number generator.
	 *
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param r the random number generator
	 * @return a random number between min and max
	 */
	public static double randomBetween(final double min, final double max, final Random r) {
		return r.nextDouble() * (max - min) + min;
	}

	//////
	// diminishing returns
	//

	/**
	 * Calculates a percentage number with diminishing returns.
	 * The inverse operation is {@link #invdr(double, double)}.
	 *
	 * @param value the value
	 * @param base the base value. dr(X, X) will always return 0.5.
	 * @return the percentage with diminishing returns
	 */
	public static double dr(final double value, final double base) {
		return value / (value + base);
	}

	/**
	 * Calculates the original value with diminishing returns.
	 * The inverse operation is {@link #dr(double, double)}.
	 *
	 * @param pct the percentage. 0.5 will return <code>base</code>
	 * @param base the base value
	 * @return the original value with diminishing returns
	 */
	public static double invdr(final double pct, final double base) {
		return pct * base / (1.0 - pct);
	}

	//////
	// value clamping
	//

	public static int clamp(final int value, final int min, final int max) {
		return value < min ? min : value > max ? max : value;
	}

	public static long clamp(final long value, final long min, final long max) {
		return value < min ? min : value > max ? max : value;
	}

	public static float clamp(final float value, final float min, final float max) {
		return value < min ? min : value > max ? max : value;
	}

	public static double clamp(final double value, final double min, final double max) {
		return value < min ? min : value > max ? max : value;
	}

	//////
	// mininum and maximum for multiple values
	//

	public static int min(final int x1, final int... xn) {
		int min = x1;
		for (final int x : xn) {
			min = Math.min(min, x);
		}
		return min;
	}

	public static long min(final long x1, final long... xn) {
		long min = x1;
		for (final long x : xn) {
			min = Math.min(min, x);
		}
		return min;
	}

	public static float min(final float x1, final float... xn) {
		float min = x1;
		for (final float x : xn) {
			min = Math.min(min, x);
		}
		return min;
	}

	public static double min(final double x1, final double... xn) {
		double min = x1;
		for (final double x : xn) {
			min = Math.min(min, x);
		}
		return min;
	}

	public static int max(final int x1, final int... xn) {
		int max = x1;
		for (final int x : xn) {
			max = Math.max(max, x);
		}
		return max;
	}

	public static long max(final long x1, final long... xn) {
		long max = x1;
		for (final long x : xn) {
			max = Math.max(max, x);
		}
		return max;
	}

	public static float max(final float x1, final float... xn) {
		float max = x1;
		for (final float x : xn) {
			max = Math.max(max, x);
		}
		return max;
	}

	public static double max(final double x1, final double... xn) {
		double max = x1;
		for (final double x : xn) {
			max = Math.max(max, x);
		}
		return max;
	}

	//////
	// linear interpolation
	//

	public static float lerp(final int value1, final int value2, final float weight) {
		return (value2 - value1) * weight + value1;
	}

	public static double lerp(final int value1, final int value2, final double weight) {
		return (value2 - value1) * weight + value1;
	}

	public static float lerp(final long value1, final long value2, final float weight) {
		return (value2 - value1) * weight + value1;
	}

	public static double lerp(final long value1, final long value2, final double weight) {
		return (value2 - value1) * weight + value1;
	}

	public static float lerp(final float value1, final float value2, final float weight) {
		return (value2 - value1) * weight + value1;
	}

	public static double lerp(final double value1, final double value2, final double weight) {
		return (value2 - value1) * weight + value1;
	}

	//////
	// spline interpolation
	//

	public static float slerpDelta(final float v0, final float v1, final float v2, final float v3, final float t) {
		return 0.5f * (v2 - v0 + 2 * t * (2 * v0 - 5 * v1 + 4 * v2 - v3) + 3 * t * t * (3 * v1 + v3 - v0 - 3 * v2));
	}

	public static float slerp(final float v0, final float v1, final float v2, final float v3, final float t) {
		final float tSq = t * t;
		final float tCube = tSq * t;
		return 0.5f * (2 * v1 + (v2 - v0) * t + (2 * v0 - 5 * v1 + 4 * v2 - v3) * tSq + (3 * v1 + v3 - v0 - 3 * v2) * tCube);
	}

	public static double slerpDelta(final double v0, final double v1, final double v2, final double v3, final double t) {
		return 0.5f * (v2 - v0 + 2 * t * (2 * v0 - 5 * v1 + 4 * v2 - v3) + 3 * t * t * (3 * v1 + v3 - v0 - 3 * v2));
	}

	public static double slerp(final double v0, final double v1, final double v2, final double v3, final double t) {
		final double tSq = t * t;
		final double tCube = tSq * t;
		return 0.5f * (2 * v1 + (v2 - v0) * t + (2 * v0 - 5 * v1 + 4 * v2 - v3) * tSq + (3 * v1 + v3 - v0 - 3 * v2) * tCube);
	}

	//////
	// sqrt for BigDecimals
	//

	public static BigDecimal sqrt(final BigDecimal num) {
		return sqrt(num, 16);
	}

	public static BigDecimal sqrt(final BigDecimal num, final int minScale) {
		if (num.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("number cannot be negative");
		}
		if (num.equals(BigDecimal.ZERO)) {
			return BigDecimal.ZERO;
		}
		// http://en.wikipedia.org/wiki/Methods_of_computing_square_roots
		// x0 ~= sqrt(S)
		// x(n+1) = 1/2(xn + S/xn)
		// sqrt(S) = lim(n -> inf, xn)
		final int scale = Math.max(num.scale(), minScale);
		final BigDecimal two = BigDecimal.valueOf(2);
		BigDecimal xn = num.divide(two);
		BigDecimal oldSqrtS;
		final BigDecimal S = num;
		do {
			// S/xn
			BigDecimal xn1 = S.divide(xn, scale, RoundingMode.HALF_UP);
			// xn + S/xn
			xn1 = xn1.add(xn);
			// 1/2(xn + S/xn)
			oldSqrtS = xn;
			xn = xn1.divide(two, scale, RoundingMode.HALF_UP);
		} while (!oldSqrtS.equals(xn));
		return xn;
	}

	//////
	// LCM, GCD for BigDecimals
	//

	public static BigDecimal lcm(final BigDecimal num1, final BigDecimal num2) {
		return new BigDecimal(lcm(num1.toBigInteger(), num2.toBigInteger()));
	}

	public static BigDecimal lcm(final BigDecimal num1, final BigDecimal num2, final BigDecimal num3, final BigDecimal... moreNums) {
		final BigInteger[] moreInts = new BigInteger[moreNums.length];
		for (int i = 0; i < moreInts.length; i++) {
			moreInts[i] = moreNums[i].toBigInteger();
		}
		return new BigDecimal(lcm(num1.toBigInteger(), num2.toBigInteger(), num3.toBigInteger(), moreInts));
	}

	public static BigDecimal gcd(final BigDecimal num1, final BigDecimal num2) {
		return new BigDecimal(num1.toBigInteger().gcd(num2.toBigInteger()));
	}

	public static BigDecimal gcd(final BigDecimal num1, final BigDecimal num2, final BigDecimal num3, final BigDecimal... moreNums) {
		final BigInteger[] moreInts = new BigInteger[moreNums.length];
		for (int i = 0; i < moreInts.length; i++) {
			moreInts[i] = moreNums[i].toBigInteger();
		}
		return new BigDecimal(gcd(num1.toBigInteger(), num2.toBigInteger(), num3.toBigInteger(), moreInts));
	}

	//////
	// LCM, GCD for BigIntegers
	//

	public static BigInteger lcm(final BigInteger num1, final BigInteger num2) {
		return num1.multiply(num2).divide(num1.gcd(num2));
	}

	public static BigInteger lcm(final BigInteger num1, final BigInteger num2, final BigInteger num3, final BigInteger... moreNums) {
		BigInteger lcm = lcm(lcm(num1, num2), num3);
		for (int i = 0; i < moreNums.length; i++) {
			lcm = lcm(lcm, moreNums[i]);
		}
		return lcm;
	}

	public static BigInteger gcd(final BigInteger num1, final BigInteger num2, final BigInteger num3, final BigInteger... moreNums) {
		BigInteger gcd = num1.gcd(num2).gcd(num3);
		for (int i = 0; i < moreNums.length; i++) {
			gcd = gcd.gcd(moreNums[i]);
		}
		return gcd;
	}

	//////
	// LCM, GCD for longs
	//

	public static long lcm(final long num1, final long num2) {
		return num1 * num2 / gcd(num1, num2);
	}

	public static long lcm(final long num1, final long num2, final long num3, final long... moreNums) {
		long lcm = lcm(lcm(num1, num2), num3);
		for (int i = 0; i < moreNums.length; i++) {
			lcm = lcm(lcm, moreNums[i]);
		}
		return lcm;
	}

	public static long gcd(final long num1, final long num2) {
		// Euclid's algorithm
		long localNum1 = num1;
		long localNum2 = num2;
		while (num2 > 0) {
			final long n1 = localNum1;
			final long n2 = localNum2;
			localNum1 = n2;
			localNum2 = n1 % n2;
		}
		return num1;
	}

	public static long gcd(final long num1, final long num2, final long num3, final long... moreNums) {
		long gcd = gcd(gcd(num1, num2), num3);
		for (int i = 0; i < moreNums.length; i++) {
			gcd = gcd(gcd, moreNums[i]);
		}
		return gcd;
	}

	//////
	// LCM, GCD for ints
	//

	public static int lcm(final int num1, final int num2) {
		return num1 * num2 / gcd(num1, num2);
	}

	public static int lcm(final int num1, final int num2, final int num3, final int... moreNums) {
		int lcm = lcm(lcm(num1, num2), num3);
		for (int i = 0; i < moreNums.length; i++) {
			lcm = lcm(lcm, moreNums[i]);
		}
		return lcm;
	}

	public static int gcd(final int num1, final int num2) {
		// Euclid's algorithm
		int localNum1 = num1;
		int localNum2 = num2;
		while (num2 > 0) {
			final int n1 = localNum1;
			final int n2 = localNum2;
			localNum1 = n2;
			localNum2 = n1 % n2;
		}
		return num1;
	}

	public static int gcd(final int num1, final int num2, final int num3, final int... moreNums) {
		int gcd = gcd(gcd(num1, num2), num3);
		for (int i = 0; i < moreNums.length; i++) {
			gcd = gcd(gcd, moreNums[i]);
		}
		return gcd;
	}
}
