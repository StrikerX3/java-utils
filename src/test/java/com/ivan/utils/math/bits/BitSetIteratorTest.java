package com.ivan.utils.math.bits;

import java.util.BitSet;

public class BitSetIteratorTest {
	public static void main(final String[] args) {
		final BitSet set = new BitSet();
		set.set(2);
		set.set(3);
		set.set(5);
		set.set(8, 12);
		set.set(25);

		final BitSetIterator itS = BitSetIterator.setIterator(set);
		final BitSetIterator itC = BitSetIterator.clearIterator(set);

		System.out.print("set bits: ");
		String sep = "";
		while (itS.hasNext()) {
			final Integer n = itS.next();
			System.out.print(sep + n);
			sep = ", ";
		}

		System.out.println();
		System.out.print("first 10 clear bits: ");
		sep = "";
		for (int i = 0; i < 10; i++) {
			final Integer n = itC.next();
			System.out.print(sep + n);
			sep = ", ";
		}
		System.out.println();

		itC.set();
		System.out.println("setting bit " + itC.getPos() + ": " + set);
		itC.clear();
		System.out.println("clearing bit " + itC.getPos() + ": " + set);
		itC.flip();
		System.out.println("flipping bit " + itC.getPos() + ": " + set);
		itC.flip();
		System.out.println("flipping bit " + itC.getPos() + ": " + set);

		System.out.println("  set bits iterator isFinite(): " + itS.isFinite());
		System.out.println("clear bits iterator isFinite(): " + itC.isFinite());
	}
}
