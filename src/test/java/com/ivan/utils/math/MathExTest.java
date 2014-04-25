package com.ivan.utils.math;

public class MathExTest {
	public static void main(final String[] args) {
		final double base = 20;
		final double value = MathEx.invdr(0.94835, base);
		System.out.println("base = " + base);
		System.out.println("value = " + value);
		System.out.println("dr = " + MathEx.dr(value, base));
	}
}
