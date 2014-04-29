package com.ivan.utils.lang.unsigned;

public final class ULong extends Unsigned {
    private static final long serialVersionUID = 2884129969381769121L;

    private final long n;

    public ULong(final long i) {
        n = i;
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
        return new ULong(n + num.longValue());
    }

    @Override
    public Unsigned sub(final Number num) {
        return new ULong(n - num.longValue());
    }

    @Override
    public Unsigned mult(final Number num) {
        return new ULong(n * num.longValue());
    }

    @Override
    public Unsigned div(final Number num) {
        return new ULong(div(n, num.longValue()));
    }

    @Override
    public Unsigned rem(final Number num) {
        return new ULong(rem(n, num.longValue()));
    }

    @Override
    public boolean lt(final Number num) {
        final long n2 = num.longValue();
        return lt(n, n2);
    }

    @Override
    public boolean lte(final Number num) {
        final long n2 = num.longValue();
        return lte(n, n2);
    }

    @Override
    public boolean gt(final Number num) {
        final long n2 = num.longValue();
        return gt(n, n2);
    }

    @Override
    public boolean gte(final Number num) {
        final long n2 = num.longValue();
        return gte(n, n2);
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
        return toString(n);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ULong)) {
            return false;
        }
        return ((ULong) obj).n == n;
    }

    @Override
    public int hashCode() {
        return (int) (n ^ n >>> 32);
    }

    public static long div(final long n1, final long n2) {
        return divRem(n1, n2).quotient;
    }

    public static long rem(final long n1, final long n2) {
        return divRem(n1, n2).remainder;
    }

    public static final boolean lt(final long n1, final long n2) {
        return n1 < n2 ^ n1 < 0 != n2 < 0;
    }

    public static final boolean lte(final long n1, final long n2) {
        return n1 <= n2 ^ n1 < 0 != n2 < 0;
    }

    public static final boolean gt(final long n1, final long n2) {
        return n1 > n2 ^ n1 < 0 != n2 < 0;
    }

    public static final boolean gte(final long n1, final long n2) {
        return n1 >= n2 ^ n1 < 0 != n2 < 0;
    }

    public static String toString(final long n) {
        return toString(n, 10);
    }

    public static String toString(final long n, final int radix) {
        if (radix < 2 || radix > 36) {
            throw new IllegalArgumentException("radix out out range [2..36]: " + radix);
        }
        final long val = n;
        if (val == 0) {
            return "0";
        }
        final DivisionResult div = divRem(val, radix);
        final char dig = digits[(int) div.getRemainder()];
        return Long.toString(div.getQuotient(), radix) + dig;
    }

    private static final char[] digits = new char[36];
    static {
        for (int i = 0; i < 10; i++) {
            digits[i] = (char) ('0' + i);
        }
        for (int i = 0; i <= 'z' - 'a'; i++) {
            digits[i + 10] = (char) ('a' + i);
        }
    }

    public static DivisionResult divRem(final long dividend, final long divisor) {
        return divRem(dividend, divisor, new DivisionResult());
    }

    public static DivisionResult divRem(final long dividend, final long divisor, final DivisionResult result) {
        if (divisor == 1) {
            result.quotient = dividend;
            result.remainder = 0;
            return new DivisionResult(dividend, 0);
        }

        if (dividend >= 0) {
            if (divisor >= 0) {
                // positive integer division
                // no special treament required
                result.quotient = dividend / divisor;
                result.remainder = dividend % divisor;
                return result;
            }

            // dividend smaller than divisor
            result.quotient = 0;
            result.remainder = dividend;
            return result;
        }

        if (divisor >= 0) {
            final long tmpDividend = dividend >>> 1 & 0x7fffffffffffffffL;
            final long tmpRemainder = tmpDividend - tmpDividend / divisor * divisor;

            long quotient = tmpDividend / divisor << 1;
            long remainder = tmpDividend % divisor + tmpRemainder + (dividend & 0x1);
            if (remainder >= divisor) {
                quotient += remainder / divisor;
                remainder %= divisor;
            }

            result.quotient = quotient;
            result.remainder = remainder;
            return result;
        }
        result.quotient = 1;
        result.remainder = dividend - divisor;
        return result;
    }

    public static class DivisionResult {
        long quotient;

        long remainder;

        public DivisionResult() {
            this(0, 0);
        }

        public DivisionResult(final long quotient, final long remainder) {
            this.quotient = quotient;
            this.remainder = remainder;
        }

        public long getQuotient() {
            return quotient;
        }

        public long getRemainder() {
            return remainder;
        }
    }
}
