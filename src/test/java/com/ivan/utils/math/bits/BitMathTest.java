package com.ivan.utils.math.bits;

public class BitMathTest {
	public static void main(final String[] args) {
		int i = 7;
		printBin(i);
		for (int k = 0; k < 10; k++) {
			i = BitMath.nextWithSameNumberOfOneBits(i);
			printBin(i);
		}

		System.out.println();
		System.out.println(BitMath.nextPowerOfTwo((byte) 17));
		System.out.println(BitMath.prevPowerOfTwo((byte) 17));

		System.out.println();
		printCmp(1, 2);
		printCmp(1, 3);
		printCmp(1, 7);
		printCmp(2, 1);
		printCmp(3, 1);
		printCmp(7, 1);

		System.out.println();
		printDiff(0, -1);
		printDiff(1, 2);
		printDiff(1, 3);
		printDiff(1, 7);
		printDiff(2, 1);
		printDiff(3, 1);
		printDiff(7, 1);
		printDiff(-1, 0);

		System.out.println();
		printParity(1);
		printParity(2);
		printParity(3);
		printParity(4);
		printParity(5);
		printParity(6);
		printParity(7);

		System.out.println();
		printLeftMostZeroByte(0x00000000);
		printLeftMostZeroByte(0x00000125);
		printLeftMostZeroByte(0xff170000);
		printLeftMostZeroByte(0x9f00116f);
		printLeftMostZeroByte(0x80140013);
		printLeftMostZeroByte(0x1acbde00);
		printLeftMostZeroByte(0x00794f8c);
		printLeftMostZeroByte(0xdeadbeef);

		System.out.println();
		printRightMostZeroByte(0x00000000);
		printRightMostZeroByte(0x00000125);
		printRightMostZeroByte(0xff170000);
		printRightMostZeroByte(0x9f00116f);
		printRightMostZeroByte(0x80140013);
		printRightMostZeroByte(0x1acbde00);
		printRightMostZeroByte(0x00794f8c);
		printRightMostZeroByte(0xdeadbeef);

		System.out.println();
		System.out.println(BitMath.nextWithSameNumberOfOneBits((byte) 0x80));

		System.out.println();
		System.out.println(BitMath.numberOfLeadingZeros((short) 0x3000));

		System.out.println();
		System.out.println(BitMath.bitCount((byte) 0x8f));

		System.out.println();
		System.out.println(BitMath.signum((byte) 0x00));

		System.out.println();
		System.out.println(hex(BitMath.reverseBytes((short) 0x8040)));

		System.out.println();
		System.out.println(bin(0x8040) + " -> " + bin(BitMath.reverse((short) 0x8040) & 0xffff));
		System.out.println(bin(0x75) + " -> " + bin(BitMath.reverse((byte) 0x75) & 0xff));

		System.out.println();
		System.out.println(bin(0x003f) + " -> " + bin(BitMath.rotateRight((short) 0x003f, 5) & 0xffff));
		System.out.println(bin(0x3f) + " -> " + bin(BitMath.rotateRight((byte) 0x3f, 5) & 0xff));

		System.out.println();
		System.out.println(bin(0xfc00) + " -> " + bin(BitMath.rotateLeft((short) 0xfc00, 5) & 0xffff));
		System.out.println(bin(0xfc) + " -> " + bin(BitMath.rotateLeft((byte) 0xfc, 5) & 0xff));

		System.out.println();
		System.out.println(BitMath.parity(1L));
		System.out.println(BitMath.parity(0xffffffffffffffefL));
		System.out.println(BitMath.parity((short) 0xfffeL));
		System.out.println(BitMath.parity((short) 0xffffL));
		System.out.println(BitMath.parity((byte) 0xfeL));
		System.out.println(BitMath.parity((byte) 0xffL));

		System.out.println();
		System.out.println(BitMath.compareBitCount((byte) 1, (byte) 2));
		System.out.println(BitMath.compareBitCount((byte) 1, (byte) 3));
		System.out.println(BitMath.compareBitCount((byte) 1, (byte) 7));
		System.out.println(BitMath.compareBitCount((byte) 2, (byte) 1));
		System.out.println(BitMath.compareBitCount((byte) 3, (byte) 1));
		System.out.println(BitMath.compareBitCount((byte) 7, (byte) 1));

		System.out.println();
		System.out.println(BitMath.compareBitCount((short) 1, (short) 2));
		System.out.println(BitMath.compareBitCount((short) 1, (short) 3));
		System.out.println(BitMath.compareBitCount((short) 1, (short) 7));
		System.out.println(BitMath.compareBitCount((short) 2, (short) 1));
		System.out.println(BitMath.compareBitCount((short) 3, (short) 1));
		System.out.println(BitMath.compareBitCount((short) 7, (short) 1));

		System.out.println();
		System.out.println(BitMath.bitCountDiff(1L, 2L));
		System.out.println(BitMath.bitCountDiff(1L, 3L));
		System.out.println(BitMath.bitCountDiff(1L, 7L));
		System.out.println(BitMath.bitCountDiff(1L, 0xFFFFFFFFFFFFFFFFL));
		System.out.println(BitMath.bitCountDiff(0L, 0xFFFFFFFFFFFFFFFFL));
		System.out.println(BitMath.bitCountDiff(2L, 1L));
		System.out.println(BitMath.bitCountDiff(3L, 1L));
		System.out.println(BitMath.bitCountDiff(7L, 1L));
		System.out.println(BitMath.bitCountDiff(0xFFFFFFFFFFFFFFFFL, 1L));
		System.out.println(BitMath.bitCountDiff(0xFFFFFFFFFFFFFFFFL, 0L));

		System.out.println();
		System.out.println(BitMath.bitCountDiff((short) 1, (short) 2));
		System.out.println(BitMath.bitCountDiff((short) 1, (short) 3));
		System.out.println(BitMath.bitCountDiff((short) 1, (short) 7));
		System.out.println(BitMath.bitCountDiff((short) 1, (short) 0xFFFF));
		System.out.println(BitMath.bitCountDiff((short) 0, (short) 0xFFFF));
		System.out.println(BitMath.bitCountDiff((short) 2, (short) 1));
		System.out.println(BitMath.bitCountDiff((short) 3, (short) 1));
		System.out.println(BitMath.bitCountDiff((short) 7, (short) 1));
		System.out.println(BitMath.bitCountDiff((short) 0xFFFF, (short) 1));
		System.out.println(BitMath.bitCountDiff((short) 0xFFFF, (short) 0));

		System.out.println();
		System.out.println(BitMath.bitCountDiff((byte) 1, (byte) 2));
		System.out.println(BitMath.bitCountDiff((byte) 1, (byte) 3));
		System.out.println(BitMath.bitCountDiff((byte) 1, (byte) 7));
		System.out.println(BitMath.bitCountDiff((byte) 1, (byte) 0xFF));
		System.out.println(BitMath.bitCountDiff((byte) 0, (byte) 0xFF));
		System.out.println(BitMath.bitCountDiff((byte) 2, (byte) 1));
		System.out.println(BitMath.bitCountDiff((byte) 3, (byte) 1));
		System.out.println(BitMath.bitCountDiff((byte) 7, (byte) 1));
		System.out.println(BitMath.bitCountDiff((byte) 0xFF, (byte) 1));
		System.out.println(BitMath.bitCountDiff((byte) 0xFF, (byte) 0));

		System.out.println();
		System.out.println(BitMath.rightMostZeroByte(0x0000000000000000L));
		System.out.println(BitMath.rightMostZeroByte(0x00000000000000ffL));
		System.out.println(BitMath.rightMostZeroByte(0x000000000000ffffL));
		System.out.println(BitMath.rightMostZeroByte(0x0000000000ffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x00000000ffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x000000ffffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x0000ffffffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x00ffffffffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0xffffffffffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x00000000000000ffL));
		System.out.println(BitMath.rightMostZeroByte(0x000000000000ff00L));
		System.out.println(BitMath.rightMostZeroByte(0x0000000000ff00ffL));
		System.out.println(BitMath.rightMostZeroByte(0x00000000ff00ffffL));
		System.out.println(BitMath.rightMostZeroByte(0x000000ff00ffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x0000ff00ffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0x00ff00ffffffffffL));
		System.out.println(BitMath.rightMostZeroByte(0xff00ffffffffffffL));

		System.out.println();
		System.out.println(BitMath.rightMostZeroByte((short) 0x0000));
		System.out.println(BitMath.rightMostZeroByte((short) 0xff00));
		System.out.println(BitMath.rightMostZeroByte((short) 0x00ff));
		System.out.println(BitMath.rightMostZeroByte((short) 0xffff));

		System.out.println();
		System.out.println(BitMath.leftMostZeroByte(0x0000000000000000L));
		System.out.println(BitMath.leftMostZeroByte(0xff00000000000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffff000000000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffff0000000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffff00000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffffff000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffffffff0000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffffffffff00L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffffffffffffL));
		System.out.println(BitMath.leftMostZeroByte(0xff00000000000000L));
		System.out.println(BitMath.leftMostZeroByte(0x00ff000000000000L));
		System.out.println(BitMath.leftMostZeroByte(0xff00ff0000000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffff00ff00000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffff00ff000000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffff00ff0000L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffffff00ff00L));
		System.out.println(BitMath.leftMostZeroByte(0xffffffffffff00ffL));

		System.out.println();
		System.out.println(BitMath.leftMostZeroByte((short) 0x0000));
		System.out.println(BitMath.leftMostZeroByte((short) 0x00ff));
		System.out.println(BitMath.leftMostZeroByte((short) 0xff00));
		System.out.println(BitMath.leftMostZeroByte((short) 0xffff));

		System.out.println();
		System.out.println(bin(25) + " -> " + bin(BitMath.setBit(25, 9)));
		System.out.println(bin(25) + " -> " + bin(BitMath.setBit(25, 39)));
		System.out.println(bin(25) + " -> " + bin(BitMath.clearBit(25, 3)));
		System.out.println(bin(25) + " -> " + bin(BitMath.clearBit(25, 32)));
		System.out.println(bin(25) + " -> " + bin(BitMath.toggleBit(25, 3)));
		System.out.println(bin(25) + " -> " + bin(BitMath.toggleBit(25, 5)));
		System.out.println(bin(25) + " -> " + bin(BitMath.toggleBit(25, 32)));
		System.out.println(bin(25) + " -> " + bin(BitMath.toggleBit(25, 33)));
	}

	private static String bin(final int i) {
		return Integer.toBinaryString(i);
	}

	private static String hex(final int i) {
		final String zeros = "00000000";
		final String hex = Integer.toHexString(i);
		return zeros.substring(hex.length()) + hex;
	}

	private static void printBin(final int i) {
		System.out.println(bin(i) + " - " + BitMath.bitCount(i) + " bits");
	}

	private static void printCmp(final int x, final int y) {
		final int cmp = BitMath.compareBitCount(x, y);
		char cmpChar;
		cmpChar = cmp < 0 ? '<'
				: cmp > 0 ? '>'
						: '=';
		System.out.println("pop(" + bin(x) + ") " + cmpChar + " pop(" + bin(y) + ")");
	}

	private static void printDiff(final int x, final int y) {
		final int diff = BitMath.bitCountDiff(x, y);
		System.out.println("pop(" + bin(x) + ") - pop(" + bin(y) + ") = " + diff);
	}

	private static void printParity(final int x) {
		final int parity = BitMath.parity(x);
		System.out.println("parity(" + bin(x) + ") = " + parity);
	}

	private static void printLeftMostZeroByte(final int x) {
		final int lmzb = BitMath.leftMostZeroByte(x);
		System.out.println("lmzb(" + hex(x) + ") = " + lmzb);
	}

	private static void printRightMostZeroByte(final int x) {
		final int rmzb = BitMath.rightMostZeroByte(x);
		System.out.println("rmzb(" + hex(x) + ") = " + rmzb);
	}
}
