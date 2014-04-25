package com.ivan.utils.lang.unsigned;

public final class UInteger extends Unsigned {
	private static final long serialVersionUID = 2884129969381769121L;

	private final long n;

	public static final long MIN_VALUE = 0;
	public static final long MAX_VALUE = 0xffffffffL;

	private static final long MASK = 0xffffffffL;

	public UInteger(final int i) {
		this(i, true);
	}

	public UInteger(final long i) {
		this(i, true);
	}

	private UInteger(final long i, final boolean checkRange) {
		if (checkRange && (i < MIN_VALUE || i > MAX_VALUE)) {
			throw new NumberFormatException("Value out of range [" + MIN_VALUE + ", " + MAX_VALUE + "]: " + i);
		}
		n = i & MASK;
	}

	@Override
	public int intValue() {
		return (int) longValue();
	}

	@Override
	public long longValue() {
		return n;
	}

	@Override
	public float floatValue() {
		return longValue();
	}

	@Override
	public double doubleValue() {
		return longValue();
	}

	@Override
	public Unsigned add(final Number num) {
		return new UInteger(n + num.longValue(), false);
	}

	@Override
	public Unsigned sub(final Number num) {
		return new UInteger(n - num.longValue(), false);
	}

	@Override
	public Unsigned mult(final Number num) {
		return new UInteger(n * num.longValue(), false);
	}

	@Override
	public Unsigned div(final Number num) {
		return new UInteger(n / num.longValue(), false);
	}

	@Override
	public Unsigned rem(final Number num) {
		return new UInteger(n % num.longValue(), false);
	}

	@Override
	public boolean lt(final Number num) {
		return n < num.longValue();
	}

	@Override
	public boolean lte(final Number num) {
		return n <= num.longValue();
	}

	@Override
	public boolean gt(final Number num) {
		return n > num.longValue();
	}

	@Override
	public boolean gte(final Number num) {
		return n >= num.longValue();
	}

	@Override
	public boolean eq(final Number num) {
		return n == num.longValue();
	}

	@Override
	public boolean neq(final Number num) {
		return n != num.longValue();
	}

	@Override
	public String toString() {
		return Long.toString(n);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof UInteger)) {
			return false;
		}
		return ((UInteger) obj).n == n;
	}

	@Override
	public int hashCode() {
		return (int) (n & MASK);
	}

	public static final int div(final int n1, final int n2) {
		final long i1 = n1 & MASK;
		final long i2 = n2 & MASK;
		return (int) (i1 / i2 & MASK);
	}

	public static final int rem(final int n1, final int n2) {
		final long i1 = n1 & MASK;
		final long i2 = n2 & MASK;
		return (int) (i1 % i2 & MASK);
	}

	public static final boolean lt(final int n1, final int n2) {
		final long i1 = n1 & MASK;
		final long i2 = n2 & MASK;
		return i1 < i2;
	}

	public static final boolean lte(final int n1, final int n2) {
		final long i1 = n1 & MASK;
		final long i2 = n2 & MASK;
		return i1 <= i2;
	}

	public static final boolean gt(final int n1, final int n2) {
		final long i1 = n1 & MASK;
		final long i2 = n2 & MASK;
		return i1 > i2;
	}

	public static final boolean gte(final int n1, final int n2) {
		final long i1 = n1 & MASK;
		final long i2 = n2 & MASK;
		return i1 >= i2;
	}

	public static String toString(final int n) {
		return Long.toString(n & MASK);
	}

	public static String toString(final int n, final int radix) {
		return Long.toString(n & MASK, radix);
	}
}
