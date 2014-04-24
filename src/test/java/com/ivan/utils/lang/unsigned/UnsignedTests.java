package com.ivan.utils.lang.unsigned;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class UnsignedTests {
	public static void main(final String[] args) {
		testUInt8();
		testUInt16();
		testUInt32();
		testUInt64();
	}

	private static void title(final String name) {
		System.out.println("=========[ " + name + " ]=========");
	}

	private static void newline() {
		System.out.println();
	}

	private static void doComparisons(final Unsigned n, final long num) {
		final long ant = num - 1;
		final long suc = num + 1;
		final String antStr = ULong.toString(ant);
		final String numStr = ULong.toString(num);
		final String sucStr = ULong.toString(suc);

		System.out.println("n == " + antStr + " ? " + n.eq(ant)); // false
		System.out.println("n == " + sucStr + " ? " + n.eq(suc)); // false
		System.out.println("n != " + numStr + " ? " + n.neq(num)); // false
		System.out.println("n <  " + antStr + " ? " + n.lt(ant)); // false
		System.out.println("n <  " + numStr + " ? " + n.lt(num)); // false
		System.out.println("n <= " + antStr + " ? " + n.lte(ant)); // false
		System.out.println("n >  " + numStr + " ? " + n.gt(num)); // false
		System.out.println("n >  " + sucStr + " ? " + n.gt(suc)); // false
		System.out.println("n >= " + sucStr + " ? " + n.gte(suc)); // false
		newline();
		System.out.println("n == " + numStr + " ? " + n.eq(num)); // true
		System.out.println("n != " + antStr + " ? " + n.neq(ant)); // true
		System.out.println("n != " + sucStr + " ? " + n.neq(suc)); // true
		System.out.println("n <  " + sucStr + " ? " + n.lt(suc)); // true
		System.out.println("n <= " + numStr + " ? " + n.lte(num)); // true
		System.out.println("n <= " + sucStr + " ? " + n.lte(suc)); // true
		System.out.println("n >  " + antStr + " ? " + n.gt(ant)); // true
		System.out.println("n >= " + antStr + " ? " + n.gte(ant)); // true
		System.out.println("n >= " + numStr + " ? " + n.gte(num)); // true
	}

	private static Unsigned add(Unsigned n, final long amt) {
		n = n.add(amt);
		System.out.println("n + " + amt + " = " + n);
		return n;
	}

	private static Unsigned sub(Unsigned n, final long amt) {
		n = n.sub(amt);
		System.out.println("n - " + amt + " = " + n);
		return n;
	}

	private static Unsigned mult(Unsigned n, final long amt) {
		n = n.mult(amt);
		System.out.println("n * " + amt + " = " + n);
		return n;
	}

	private static Unsigned div(Unsigned n, final long amt) {
		n = n.div(amt);
		System.out.println("n / " + amt + " = " + n);
		return n;
	}

	private static void testUInt(final Class<? extends Unsigned> cls) throws Exception {
		title(cls.getSimpleName());

		final Field minVal = cls.getDeclaredField("MIN_VALUE");
		final Field maxVal = cls.getDeclaredField("MAX_VALUE");
		final long min = minVal.getLong(null);
		final long max = maxVal.getLong(null);
		System.out.println(min + ".." + max);
		final long baseVal = max;

		final Constructor<? extends Unsigned> ctor = cls.getConstructor(long.class);
		Unsigned n = ctor.newInstance(baseVal / 4);
		long tot = baseVal / 4;

		System.out.println("n = " + n);
		n = add(n, baseVal / 2);
		tot += baseVal / 2;
		n = sub(n, baseVal / 4);
		tot -= baseVal / 4;
		n = mult(n, 2);
		tot *= 2;
		n = div(n, 4);
		tot /= 4;

		newline();
		doComparisons(n, tot);

		newline();
		n = mult(n, 3);
		tot *= 3;
		doComparisons(n, tot);

		newline();
		n = add(n, baseVal * 2 / 5);
		n = sub(n, baseVal * 2 / 10);
	}

	private static void testUInt8() {
		try {
			testUInt(UByte.class);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void testUInt16() {
		try {
			testUInt(UShort.class);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void testUInt32() {
		try {
			testUInt(UInteger.class);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void testUInt64() {
		title(ULong.class.getSimpleName());

		final long min = 0;
		final long max = 0xffffffffffffffffL;
		System.out.println(ULong.toString(min) + ".." + ULong.toString(max));
		final long baseVal = max;
		final long baseValDiv2 = ULong.div(baseVal, 2);
		final long baseValDiv4 = ULong.div(baseVal, 4);

		Unsigned n = new ULong(baseValDiv4);
		long tot = baseValDiv4;

		System.out.println("n = " + n);
		n = add(n, baseValDiv2);
		tot += baseValDiv2;
		n = sub(n, baseValDiv4);
		tot -= baseValDiv4;
		n = mult(n, 2);
		tot *= 2;
		n = div(n, 4);
		tot = ULong.div(tot, 4);

		newline();
		doComparisons(n, tot);

		newline();
		n = mult(n, 3);
		tot *= 3;
		doComparisons(n, tot);

		newline();
		n = add(n, ULong.div(baseVal * 2, 5));
		n = sub(n, ULong.div(baseVal * 2, 10));
	}
}
