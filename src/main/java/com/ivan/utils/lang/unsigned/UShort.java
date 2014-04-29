package com.ivan.utils.lang.unsigned;

public final class UShort extends Unsigned {
	private static final long serialVersionUID = 2884129969381769121L;

	private final int n;

	public static final long MIN_VALUE = 0;
	public static final long MAX_VALUE = 0xffff;

	private static final int MASK = 0xffff;

	public UShort(final short b) {
		n = b & MASK;
	}

	public UShort(final long i) {
		this(i, true);
	}

	private UShort(final long i, final boolean checkRange) {
		if (checkRange && (i < MIN_VALUE || i > MAX_VALUE)) {
			throw new NumberFormatException("Value out of range [" + MIN_VALUE + ", " + MAX_VALUE + "]: " + i);
		}
		n = (int) (i & MASK);
	}

	@Override
	public int intValue() {
		return n;
	}

	@Override
	public long longValue() {
		return intValue();
	}

	@Override
	public float floatValue() {
		return intValue();
	}

	@Override
	public double doubleValue() {
		return intValue();
	}

	@Override
	public Unsigned add(final Number num) {
		return new UShort(n + num.intValue(), false);
	}

	@Override
	public Unsigned sub(final Number num) {
		return new UShort(n - num.intValue(), false);
	}

	@Override
	public Unsigned mult(final Number num) {
		return new UShort(n * num.intValue(), false);
	}

	@Override
	public Unsigned div(final Number num) {
		return new UShort(n / num.intValue(), false);
	}

	@Override
	public Unsigned rem(final Number num) {
		return new UShort(n % num.intValue(), false);
	}

	@Override
	public boolean lt(final Number num) {
		return n < num.intValue();
	}

	@Override
	public boolean lte(final Number num) {
		return n <= num.intValue();
	}

	@Override
	public boolean gt(final Number num) {
		return n > num.intValue();
	}

	@Override
	public boolean gte(final Number num) {
		return n >= num.intValue();
	}

	@Override
	public boolean eq(final Number num) {
		return n == num.intValue();
	}

	@Override
	public boolean neq(final Number num) {
		return n != num.intValue();
	}

	@Override
	public String toString() {
		return Integer.toString(n);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof UShort)) {
			return false;
		}
		return ((UShort) obj).n == n;
	}

	@Override
	public int hashCode() {
		return n & MASK;
	}

	public static final short div(final short n1, final short n2) {
		final int i1 = n1 & MASK;
		final int i2 = n2 & MASK;
		return (short) (i1 / i2 & MASK);
	}

	public static final short rem(final short n1, final short n2) {
		final int i1 = n1 & MASK;
		final int i2 = n2 & MASK;
		return (short) (i1 % i2 & MASK);
	}

	public static final boolean lt(final short n1, final short n2) {
		final int i1 = n1 & MASK;
		final int i2 = n2 & MASK;
		return i1 < i2;
	}

	public static final boolean lte(final short n1, final short n2) {
		final int i1 = n1 & MASK;
		final int i2 = n2 & MASK;
		return i1 <= i2;
	}

	public static final boolean gt(final short n1, final short n2) {
		final int i1 = n1 & MASK;
		final int i2 = n2 & MASK;
		return i1 > i2;
	}

	public static final boolean gte(final short n1, final short n2) {
		final int i1 = n1 & MASK;
		final int i2 = n2 & MASK;
		return i1 >= i2;
	}

	public static String toString(final short n) {
		return Integer.toString(n & MASK);
	}

	public static String toString(final short n, final int radix) {
		return Integer.toString(n & MASK, radix);
	}
}
