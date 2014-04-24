package com.ivan.utils.math.bits;

public final class BitMath {
	private BitMath() {
	}

	public static byte setBit(final byte b, final int pos) {
		return (byte) (b | 1 << (pos & 0x7));
	}

	public static short setBit(final short b, final int pos) {
		return (short) (b | 1 << (pos & 0xf));
	}

	public static int setBit(final int b, final int pos) {
		return b | 1 << (pos & 0x1f);
	}

	public static long setBit(final long b, final int pos) {
		return b | 1 << (pos & 0x3f);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte clearBit(final byte b, final int pos) {
		return (byte) (b & ~(1 << (pos & 0x7)));
	}

	public static short clearBit(final short b, final int pos) {
		return (short) (b & ~(1 << (pos & 0xf)));
	}

	public static int clearBit(final int b, final int pos) {
		return b & ~(1 << (pos & 0x1f));
	}

	public static long clearBit(final long b, final int pos) {
		return b & ~(1 << (pos & 0x3f));
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte toggleBit(final byte b, final int pos) {
		return (byte) (b ^ 1 << (pos & 0x7));
	}

	public static short toggleBit(final short b, final int pos) {
		return (short) (b ^ 1 << (pos & 0xf));
	}

	public static int toggleBit(final int b, final int pos) {
		return b ^ 1 << (pos & 0x1f);
	}

	public static long toggleBit(final long b, final int pos) {
		return b ^ 1 << (pos & 0x3f);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte nextPowerOfTwo(byte x) {
		x--;
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x++;
		return x;
	}

	public static short nextPowerOfTwo(short x) {
		x--;
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x |= x >>> 8;
		x++;
		return x;
	}

	public static int nextPowerOfTwo(int x) {
		x--;
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x |= x >>> 8;
		x |= x >>> 16;
		return x + 1;
	}

	public static long nextPowerOfTwo(long x) {
		x--;
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x |= x >>> 8;
		x |= x >>> 16;
		x |= x >>> 32;
		return x + 1;
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte prevPowerOfTwo(byte x) {
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		return (byte) (x - (x >>> 1));
	}

	public static short prevPowerOfTwo(short x) {
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x |= x >>> 8;
		return (short) (x - (x >>> 1));
	}

	public static int prevPowerOfTwo(int x) {
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x |= x >>> 8;
		x |= x >>> 16;
		return x - (x >>> 1);
	}

	public static long prevPowerOfTwo(long x) {
		x |= x >>> 1;
		x |= x >>> 2;
		x |= x >>> 4;
		x |= x >>> 8;
		x |= x >>> 16;
		x |= x >>> 32;
		return x - (x >>> 1);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte nextWithSameNumberOfOneBits(final byte x) {
		byte smallest, ripple, ones;
		final int ntz = numberOfTrailingZeros(x);
		//                                           v = xxx0 1111 0000
		smallest = (byte) (x & -x); //                   0000 0001 0000
		ripple = (byte) (x + smallest); //               xxx1 0000 0000
		ones = (byte) (x ^ ripple); //                   0001 1111 0000
		ones = (byte) ((ones & 0xff) >>> 2 + ntz); //    0000 0000 0111
		return (byte) (ripple | ones); //                xxx1 0000 0111
	}

	public static short nextWithSameNumberOfOneBits(final short x) {
		short smallest, ripple, ones;
		final int ntz = numberOfTrailingZeros(x);
		//                                           v = xxx0 1111 0000
		smallest = (short) (x & -x); //                  0000 0001 0000
		ripple = (short) (x + smallest); //              xxx1 0000 0000
		ones = (short) (x ^ ripple); //                  0001 1111 0000
		ones = (short) ((ones & 0xffff) >>> 2 + ntz); // 0000 0000 0111
		return (short) (ripple | ones); //               xxx1 0000 0111
	}

	public static int nextWithSameNumberOfOneBits(final int x) {
		int smallest, ripple, ones;
		//                                           v = xxx0 1111 0000
		smallest = x & -x; //                            0000 0001 0000
		ripple = x + smallest; //                        xxx1 0000 0000
		ones = x ^ ripple; //                            0001 1111 0000
		ones = ones >>> 2 + numberOfTrailingZeros(x); // 0000 0000 0111
		return ripple | ones; //                         xxx1 0000 0111
	}

	public static long nextWithSameNumberOfOneBits(final long x) {
		long smallest, ripple, ones;
		//                                           v = xxx0 1111 0000
		smallest = x & -x; //                            0000 0001 0000
		ripple = x + smallest; //                        xxx1 0000 0000
		ones = x ^ ripple; //                            0001 1111 0000
		ones = ones >>> 2 + numberOfTrailingZeros(x); // 0000 0000 0111
		return ripple | ones; //                         xxx1 0000 0111
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte highestOneBit(byte x) {
		x |= x >> 1;
		x |= x >> 2;
		x |= x >> 4;
		return (byte) (x - ((x & 0xff) >>> 1));
	}

	public static short highestOneBit(short x) {
		x |= x >> 1;
		x |= x >> 2;
		x |= x >> 4;
		x |= x >> 8;
		return (short) (x - ((x & 0xffff) >>> 1));
	}

	public static int highestOneBit(final int x) {
		return Integer.highestOneBit(x);
	}

	public static long highestOneBit(final long x) {
		return Long.highestOneBit(x);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte lowestOneBit(final byte x) {
		return (byte) (x & -x);
	}

	public static short lowestOneBit(final short x) {
		return (short) (x & -x);
	}

	public static int lowestOneBit(final int x) {
		return Integer.lowestOneBit(x);
	}

	public static long lowestOneBit(final long x) {
		return Long.lowestOneBit(x);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte numberOfLeadingZeros(byte x) {
		if (x == 0) {
			return 8;
		}
		int n = 1;
		if ((x & 0xff) >>> 4 == 0) {
			n += 4;
			x <<= 4;
		}
		if ((x & 0xff) >>> 6 == 0) {
			n += 2;
			x <<= 2;
		}
		n -= (x & 0xff) >>> 7;
		return (byte) (n & 0xff);
	}

	public static short numberOfLeadingZeros(short x) {
		if (x == 0) {
			return 16;
		}
		int n = 1;
		if ((x & 0xffff) >>> 8 == 0) {
			n += 8;
			x <<= 8;
		}
		if ((x & 0xffff) >>> 12 == 0) {
			n += 4;
			x <<= 4;
		}
		if ((x & 0xffff) >>> 14 == 0) {
			n += 2;
			x <<= 2;
		}
		n -= (x & 0xffff) >>> 15;
		return (short) (n & 0xffff);
	}

	public static int numberOfLeadingZeros(final int x) {
		return Integer.numberOfLeadingZeros(x);
	}

	public static long numberOfLeadingZeros(final long x) {
		return Long.numberOfLeadingZeros(x);
	}

	////////////////////////////////////////////////////////////////////////////

	public static int numberOfTrailingZeros(byte x) {
		byte y;
		if (x == 0) {
			return 8;
		}
		int n = 7;
		y = (byte) (x << 4);
		if (y != 0) {
			n = n - 4;
			x = y;
		}
		y = (byte) (x << 2);
		if (y != 0) {
			n = n - 2;
			x = y;
		}
		return n - ((x << 1 & 0xff) >>> 31);
	}

	public static int numberOfTrailingZeros(short x) {
		short y;
		if (x == 0) {
			return 16;
		}
		int n = 15;
		y = (short) (x << 8);
		if (y != 0) {
			n = n - 8;
			x = y;
		}
		y = (short) (x << 4);
		if (y != 0) {
			n = n - 4;
			x = y;
		}
		y = (short) (x << 2);
		if (y != 0) {
			n = n - 2;
			x = y;
		}
		return n - ((x << 1 & 0xffff) >>> 31);
	}

	public static int numberOfTrailingZeros(final int x) {
		return Integer.numberOfTrailingZeros(x);
	}

	public static int numberOfTrailingZeros(final long x) {
		return Long.numberOfTrailingZeros(x);
	}

	////////////////////////////////////////////////////////////////////////////

	private static final byte lmzbTableInt16[] = {
			2, 1, 0, 0 };

	public static int leftMostZeroByte(final short x) {
		int y;
		//             Original byte: 00 80 other
		y = (x & 0x7F7F) + 0x7F7F; // 7F 7F 1xxxxxxx
		y = ~(y | x | 0x7F7F); //     80 00 00000000
		return lmzbTableInt16[(y * 0x0081 & 0xffff) >>> 14];
	}

	private static final byte lmzbTableInt32[] = {
			4, 3, 2, 2, 1, 1, 1, 1,
			0, 0, 0, 0, 0, 0, 0, 0 };

	public static int leftMostZeroByte(final int x) {
		int y;
		//                     Original byte: 00 80 other
		y = (x & 0x7F7F7F7F) + 0x7F7F7F7F; // 7F 7F 1xxxxxxx
		y = ~(y | x | 0x7F7F7F7F); //         80 00 00000000
		return lmzbTableInt32[y * 0x00204081 >>> 28];
	}

	private static final byte lmzbTableInt64[] = {
			8, 7, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public static int leftMostZeroByte(final long x) {
		long y;
		//                                       Original byte: 00 80 other
		y = (x & 0x7F7F7F7F7F7F7F7FL) + 0x7F7F7F7F7F7F7F7FL; // 7F 7F 1xxxxxxx
		y = ~(y | x | 0x7F7F7F7F7F7F7F7FL); //                  80 00 00000000
		return lmzbTableInt64[(int) (y * 0x0002040810204081L >>> 56)];
	}

	////////////////////////////////////////////////////////////////////////////

	private static final byte rmzbTableInt16[] = {
			2, 0, 1, 1 };

	public static int rightMostZeroByte(final short x) {
		int y;
		//             Original byte: 00 80 other
		y = (x & 0x7F7F) + 0x7F7F; // 7F 7F 1xxxxxxx
		y = ~(y | x | 0x7F7F); //     80 00 00000000
		y &= -y; // lowest one bit
		return rmzbTableInt16[(y * 0x0081 & 0xffff) >>> 14];
	}

	private static final byte rmzbTableInt32[] = {
			4, 0, 1, 1, 2, 2, 2, 2,
			3, 3, 3, 3, 3, 3, 3, 3 };

	public static int rightMostZeroByte(final int x) {
		int y;
		//                     Original byte: 00 80 other
		y = (x & 0x7F7F7F7F) + 0x7F7F7F7F; // 7F 7F 1xxxxxxx
		y = ~(y | x | 0x7F7F7F7F); //         80 00 00000000
		y &= -y; // lowest one bit
		return rmzbTableInt32[y * 0x00204081 >>> 28];
	}

	private static final byte rmzbTableInt64[] = {
			8, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3,
			4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 };

	public static int rightMostZeroByte(final long x) {
		long y;
		//                                       Original byte: 00 80 other
		y = (x & 0x7F7F7F7F7F7F7F7FL) + 0x7F7F7F7F7F7F7F7FL; // 7F 7F 1xxxxxxx
		y = ~(y | x | 0x7F7F7F7F7F7F7F7FL); //                  80 00 00000000
		y &= -y; // lowest one bit
		return rmzbTableInt64[(int) (y * 0x0002040810204081L >>> 56)];
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte bitCount(byte x) {
		x = (byte) (x - ((x & 0xff) >>> 1 & 0x55));
		x = (byte) ((x & 0x33) + ((x & 0xff) >>> 2 & 0x33));
		x = (byte) (x + ((x & 0xff) >>> 4) & 0x0f);
		return (byte) (x & 0xf);
	}

	public static short bitCount(short x) {
		x = (short) (x - ((x & 0xffff) >>> 1 & 0x5555));
		x = (short) ((x & 0x3333) + ((x & 0xffff) >>> 2 & 0x3333));
		x = (short) (x + ((x & 0xffff) >>> 4) & 0x0f0f);
		x = (short) (x + ((x & 0xffff) >>> 8));
		return (short) (x & 0x1f);
	}

	public static int bitCount(final int x) {
		return Integer.bitCount(x);
	}

	public static long bitCount(final long x) {
		return Long.bitCount(x);
	}

	////////////////////////////////////////////////////////////////////////////

	public static int bitCountDiff(byte x, byte y) {
		x = (byte) (x - (x >>> 1 & 0x55));
		x = (byte) ((x & 0x33) + (x >>> 2 & 0x33));
		y = (byte) ~y;
		y = (byte) (y - (y >>> 1 & 0x55));
		y = (byte) ((y & 0x33) + (y >>> 2 & 0x33));
		x = (byte) (x + y);
		x = (byte) ((x & 0x0F) + (x >>> 4 & 0x0F));
		return (x & 0x1F) - 8;
	}

	public static int bitCountDiff(short x, short y) {
		x = (short) (x - (x >>> 1 & 0x5555));
		x = (short) ((x & 0x3333) + (x >>> 2 & 0x3333));
		y = (short) ~y;
		y = (short) (y - (y >>> 1 & 0x5555));
		y = (short) ((y & 0x3333) + (y >>> 2 & 0x3333));
		x = (short) (x + y);
		x = (short) ((x & 0x0F0F) + (x >>> 4 & 0x0F0F));
		x = (short) (x + (x >>> 8));
		return (x & 0x3F) - 16;
	}

	public static int bitCountDiff(int x, int y) {
		x = x - (x >>> 1 & 0x55555555);
		x = (x & 0x33333333) + (x >>> 2 & 0x33333333);
		y = ~y;
		y = y - (y >>> 1 & 0x55555555);
		y = (y & 0x33333333) + (y >>> 2 & 0x33333333);
		x = x + y;
		x = (x & 0x0F0F0F0F) + (x >>> 4 & 0x0F0F0F0F);
		x = x + (x >>> 8);
		x = x + (x >>> 16);
		return (x & 0x7F) - 32;
	}

	public static int bitCountDiff(long x, long y) {
		x = x - (x >>> 1 & 0x5555555555555555L);
		x = (x & 0x3333333333333333L) + (x >>> 2 & 0x3333333333333333L);
		y = ~y;
		y = y - (y >>> 1 & 0x5555555555555555L);
		y = (y & 0x3333333333333333L) + (y >>> 2 & 0x3333333333333333L);
		x = x + y;
		x = (x & 0x0F0F0F0F0F0F0F0FL) + (x >>> 4 & 0x0F0F0F0F0F0F0F0FL);
		x = x + (x >>> 8);
		x = x + (x >>> 16);
		x = x + (x >>> 32);
		return (int) ((x & 0xFFL) - 64);
	}

	////////////////////////////////////////////////////////////////////////////

	public static int compareBitCount(final byte xp, final byte yp) {
		byte x, y;
		x = (byte) (xp & ~yp); // Clear bits where
		y = (byte) (yp & ~xp); // both are 1.
		while (true) {
			if (x == 0) {
				return y | -y;
			}
			if (y == 0) {
				return 1;
			}
			x = (byte) (x & x - 1); // Clear one bit
			y = (byte) (y & y - 1); // from each.
		}
	}

	public static int compareBitCount(final short xp, final short yp) {
		short x, y;
		x = (short) (xp & ~yp); // Clear bits where
		y = (short) (yp & ~xp); // both are 1.
		while (true) {
			if (x == 0) {
				return y | -y;
			}
			if (y == 0) {
				return 1;
			}
			x = (short) (x & x - 1); // Clear one bit
			y = (short) (y & y - 1); // from each.
		}
	}

	public static int compareBitCount(final int xp, final int yp) {
		int x, y;
		x = xp & ~yp; // Clear bits where
		y = yp & ~xp; // both are 1.
		while (true) {
			if (x == 0) {
				return y | -y;
			}
			if (y == 0) {
				return 1;
			}
			x = x & x - 1; // Clear one bit
			y = y & y - 1; // from each.
		}
	}

	public static int compareBitCount(final long xp, final long yp) {
		long x, y;
		x = xp & ~yp; // Clear bits where
		y = yp & ~xp; // both are 1.
		while (true) {
			if (x == 0) {
				return (int) (y | -y);
			}
			if (y == 0) {
				return 1;
			}
			x = x & x - 1; // Clear one bit
			y = y & y - 1; // from each.
		}
	}

	////////////////////////////////////////////////////////////////////////////

	public static int parity(final byte x) {
		int y;

		y = x ^ x >> 4;
		y = 0x6996 >> (y & 0xF); // Falk Hueffner's trick.
		return y & 1;
	}

	public static int parity(final short x) {
		int y;

		y = x ^ x >> 8;
		y = y ^ y >> 4;
		y = 0x6996 >> (y & 0xF); // Falk Hueffner's trick.
		return y & 1;
	}

	public static int parity(final int x) {
		int y;

		y = x ^ x >> 16;
		y = y ^ y >> 8;
		y = y ^ y >> 4;
		y = 0x6996 >> (y & 0xF); // Falk Hueffner's trick.
		return y & 1;
	}

	public static int parity(final long x) {
		int y;

		y = (int) (x ^ x >> 32);
		y = y ^ y >> 16;
		y = y ^ y >> 8;
		y = y ^ y >> 4;
		y = 0x6996 >> (y & 0xF); // Falk Hueffner's trick.
		return y & 1;
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte rotateLeft(final byte x, final int distance) {
		return (byte) (x << distance | (x & 0xff) >>> -distance - 24 & 0xff);
	}

	public static short rotateLeft(final short x, final int distance) {
		return (short) (x << distance | (x & 0xffff) >>> -distance - 16 & 0xffff);
	}

	public static int rotateLeft(final int x, final int distance) {
		return Integer.rotateLeft(x, distance);
	}

	public static long rotateLeft(final long x, final int distance) {
		return Long.rotateLeft(x, distance);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte rotateRight(final byte x, final int distance) {
		return (byte) ((x & 0xff) >>> distance | x << -distance - 24 & 0xff);
	}

	public static short rotateRight(final short x, final int distance) {
		return (short) ((x & 0xffff) >>> distance | x << -distance - 16 & 0xffff);
	}

	public static int rotateRight(final int x, final int distance) {
		return Integer.rotateRight(x, distance);
	}

	public static long rotateRight(final long x, final int distance) {
		return Long.rotateRight(x, distance);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte reverse(byte x) {
		x = (byte) ((x & 0x55) << 1 | x >>> 1 & 0x55);
		x = (byte) ((x & 0x33) << 2 | x >>> 2 & 0x33);
		x = (byte) ((x & 0x0f) << 4 | x >>> 4 & 0x0f);
		return x;
	}

	public static short reverse(short x) {
		x = (short) ((x & 0x5555) << 1 | x >>> 1 & 0x5555);
		x = (short) ((x & 0x3333) << 2 | x >>> 2 & 0x3333);
		x = (short) ((x & 0x0f0f) << 4 | x >>> 4 & 0x0f0f);
		x = (short) ((x & 0x00ff) << 8 | x >>> 8 & 0x00ff);
		return x;
	}

	public static int reverse(final int x) {
		return Integer.reverse(x);
	}

	public static long reverse(final long x) {
		return Long.reverse(x);
	}

	////////////////////////////////////////////////////////////////////////////

	public static short reverseBytes(final short x) {
		return (short) (x >>> 8 & 0xff | x << 8 & 0xff00);
	}

	public static int reverseBytes(final int x) {
		return Integer.reverseBytes(x);
	}

	public static long reverseBytes(final long x) {
		return Long.reverseBytes(x);
	}

	////////////////////////////////////////////////////////////////////////////

	public static byte signum(final byte x) {
		return (byte) (x >> 7 | (-x & 0xff) >>> 7);
	}

	public static short signum(final short x) {
		return (short) (x >> 15 | (-x & 0xffff) >>> 15);
	}

	public static int signum(final int x) {
		return Integer.signum(x);
	}

	public static long signum(final long x) {
		return Long.signum(x);
	}
}
