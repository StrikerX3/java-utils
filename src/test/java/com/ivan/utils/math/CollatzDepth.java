package com.ivan.utils.math;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.ivan.utils.collections.map.IntMap;
import com.ivan.utils.collections.map.LongMap;

@SuppressWarnings("unused")
public class CollatzDepth {
	static BigInteger two = BigInteger.valueOf(2);

	static BigInteger three = BigInteger.valueOf(3);

	static IntMap<Integer> intCache = new IntMap<Integer>();

	static LongMap<Integer> longCache = new LongMap<Integer>();
	static Map<Long, Integer> weakLongCache = new WeakHashMap<Long, Integer>();

	static Map<BigInteger, Long> bigintCache = new HashMap<BigInteger, Long>();

	private static long collatzDepth(final BigInteger nn) throws IllegalArgumentException {
		BigInteger n = nn;
		if (n.compareTo(BigInteger.ONE) < 0) {
			throw new IllegalArgumentException("n must be >= 1");
		}
		if (bigintCache.get(n) != null) {
			return bigintCache.get(n);
		}
		long depth = 1;
		while (n.compareTo(BigInteger.ONE) != 0) {
			if (n.remainder(two).compareTo(BigInteger.ONE) == 0) {
				n = n.multiply(three).add(BigInteger.ONE);
			} else {
				n = n.divide(two);
			}
			if (bigintCache.get(n) != null) {
				return depth + bigintCache.get(n);
			}
			depth++;
		}
		bigintCache.put(n, depth);
		return depth;
	}

	private static int collatzDepth(final long n) throws IllegalArgumentException {
		long nn = n;
		if (nn < 1) {
			throw new IllegalArgumentException("n must be >= 1");
		}
		{
			final Integer cachedDepth = weakLongCache.get(n);
			if (cachedDepth != null) {
				return cachedDepth;
			}
		}
		int depth = 1;
		while (nn != 1) {
			if ((nn & 1) == 1) {
				nn = 3 * nn + 1;
			} else {
				nn = nn >> 1;
			}
			if (nn < 0) {
				throw new ArithmeticException("Overflow");
			}
			final Integer cachedDepth = weakLongCache.get(nn);
			if (cachedDepth != null) {
				depth += cachedDepth;
				weakLongCache.put(n, depth);
				return depth;
			}
			depth++;
		}
		weakLongCache.put(n, depth);
		return depth;
	}

	private static int collatzDepth(final int nn) throws IllegalArgumentException {
		int n = nn;
		if (n < 1) {
			throw new IllegalArgumentException("n must be >= 1");
		}
		if (intCache.get(n) != null) {
			return intCache.get(n);
		}
		int depth = 1;
		while (n != 1) {
			if ((n & 1) == 1) {
				n = n + (n << 1) + 1; // 3*n + 1
			} else {
				n = n >> 1;
			}
			if (n < 0) {
				throw new ArithmeticException("Overflow");
			}
			if (intCache.get(n) != null) {
				return depth + intCache.get(n);
			}
			depth++;
		}
		intCache.put(n, depth);
		return depth;
	}

	public static void main(final String[] args) {
		int max = 0;
		for (long i = 1; i <= 100000; i++) {
			collatzDepth(i);
		}
		for (long i = 100000000000L; i <= 100010000000L; i++) {
			final int depth = collatzDepth(i);
			if (depth > max) {
				max = depth;
				System.out.println(i + " : " + depth);
			}
		}
	}
}
