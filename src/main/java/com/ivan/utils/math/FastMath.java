package com.ivan.utils.math;

/**
 * Fast mathematical functions, based on lookup tables. The size of each of the
 * internal lookup tables is 2<sup>n</sup>, where <i>n</i> is the value of the
 * system property <code>fastmath.trig.granularity</code>. If this property is
 * not provided, the default value is 12, producing tables that have 4096
 * elements each.
 * <p>
 * The <code>FastMath</code> provides lookup table-based functions for sine,
 * cosine and tangent, for floats and doubles, separately. Each table is
 * initialized on demand, using 4*2<sup>n</sup> bytes (for floats) or
 * 8*2<sup>n</sup> bytes (for doubles). In total, the FastMath class may use up
 * to (4*3+8*3)*2<sup>n</sup> bytes of memory if all tables are allocated. For
 * the default granularity of 12, the tables will use 144 KB of memory.
 */
public final class FastMath {
	private FastMath() {
	}

	// increase to improve accuracy at the cost of extra memory usage

	// 8 gives very good results for float with slerp
	// 10 gives maximum accuracy for slerp float

	// 12 is a good compromise of quality vs. size

	// 16 is enough for very high accuracy for slerp double
	// 17 gives maximum accuracy for slerp double
	public static final int GRANULARITY_BITS = Integer.getInteger("fastmath.trig.granularity", 12);

	public static final int GRANULARITY = 1 << GRANULARITY_BITS;

	static final int ANGLE_MASK = GRANULARITY - 1;

	////////////////////////////////////////////////////////////////////////////

	public static float sin(final float ang) {
		return Float.sin(ang);
	}

	public static float cos(final float ang) {
		return Float.cos(ang);
	}

	public static float tan(final float ang) {
		return Float.tan(ang);
	}

	////////////////////////////////////////////////////////////////////////////

	public static float sinLerp(final float ang) {
		return Float.sinLerp(ang);
	}

	public static float cosLerp(final float ang) {
		return Float.cosLerp(ang);
	}

	public static float tanLerp(final float ang) {
		return Float.tanLerp(ang);
	}

	////////////////////////////////////////////////////////////////////////////

	public static float sinSlerp(final float ang) {
		return Float.sinSlerp(ang);
	}

	public static float cosSlerp(final float ang) {
		return Float.cosSlerp(ang);
	}

	public static float tanSlerp(final float ang) {
		return Float.tanSlerp(ang);
	}

	////////////////////////////////////////////////////////////////////////////

	public static double sin(final double ang) {
		return Double.sin(ang);
	}

	public static double cos(final double ang) {
		return Double.cos(ang);
	}

	public static double tan(final double ang) {
		return Double.tan(ang);
	}

	////////////////////////////////////////////////////////////////////////////

	public static double sinLerp(final double ang) {
		return Double.sinLerp(ang);
	}

	public static double cosLerp(final double ang) {
		return Double.cosLerp(ang);
	}

	public static double tanLerp(final double ang) {
		return Double.tanLerp(ang);
	}

	////////////////////////////////////////////////////////////////////////////

	public static double sinSlerp(final double ang) {
		return Double.sinSlerp(ang);
	}

	public static double cosSlerp(final double ang) {
		return Double.cosSlerp(ang);
	}

	public static double tanSlerp(final double ang) {
		return Double.tanSlerp(ang);
	}

	////////////////////////////////////////////////////////////////////////////

	private static final class Float {
		private static final float ANGLE_MULTIPLIER = (float) (GRANULARITY / Math.PI / 2.0);

		private static final class Sine {
			public static final float[] TABLE = new float[GRANULARITY];
			static {
				for (int i = 0; i < GRANULARITY; i++) {
					TABLE[i] = (float) Math.sin(i / (float) GRANULARITY * Math.PI * 2.0);
				}
			}
		}

		private static final class Cosine {
			public static final float[] TABLE = new float[GRANULARITY];
			static {
				for (int i = 0; i < GRANULARITY; i++) {
					TABLE[i] = (float) Math.cos(i / (float) GRANULARITY * Math.PI * 2.0);
				}
			}
		}

		private static final class Tangent {
			public static final float[] TABLE = new float[GRANULARITY];
			static {
				for (int i = 0; i < GRANULARITY; i++) {
					TABLE[i] = (float) Math.tan(i / (float) GRANULARITY * Math.PI * 2.0);
				}
			}
		}

		////////////////////////////////////////////////////////////////////////

		public static float sin(final float ang) {
			return nearest(Sine.TABLE, ang);
		}

		public static float cos(final float ang) {
			return nearest(Cosine.TABLE, ang);
		}

		public static float tan(final float ang) {
			return nearest(Tangent.TABLE, ang);
		}

		////////////////////////////////////////////////////////////////////////

		public static float sinLerp(final float ang) {
			return lerp(Sine.TABLE, ang);
		}

		public static float cosLerp(final float ang) {
			return lerp(Cosine.TABLE, ang);
		}

		public static float tanLerp(final float ang) {
			return lerp(Tangent.TABLE, ang);
		}

		////////////////////////////////////////////////////////////////////////

		public static float sinSlerp(final float ang) {
			return slerp(Sine.TABLE, ang);
		}

		public static float cosSlerp(final float ang) {
			return slerp(Cosine.TABLE, ang);
		}

		public static float tanSlerp(final float ang) {
			return slerp(Tangent.TABLE, ang);
		}

		////////////////////////////////////////////////////////////////////////

		static int radiansToTable(final double ang) {
			return (int) (ang * ANGLE_MULTIPLIER) & ANGLE_MASK;
		}

		private static float nearest(final float[] tbl, final float ang) {
			return tbl[radiansToTable(ang)];
		}

		private static float lerp(final float[] tbl, final float ang) {
			final int idx0 = radiansToTable(ang);
			final int idx1 = idx0 + 1 & ANGLE_MASK;
			final float delta = (ang * ANGLE_MULTIPLIER - idx0) % GRANULARITY;
			return MathEx.lerp(tbl[idx0], tbl[idx1], delta);
		}

		private static float slerp(final float[] tbl, final float ang) {
			final int idx1 = radiansToTable(ang);
			final int idx0 = idx1 - 1 & ANGLE_MASK;
			final int idx2 = idx1 + 1 & ANGLE_MASK;
			final int idx3 = idx1 + 2 & ANGLE_MASK;
			final float delta = (ang * ANGLE_MULTIPLIER - idx1) % GRANULARITY;
			return MathEx.slerp(tbl[idx0], tbl[idx1], tbl[idx2], tbl[idx3], delta);
		}
	}

	////////////////////////////////////////////////////////////////////////////

	private static final class Double {
		private static final double ANGLE_MULTIPLIER = GRANULARITY / Math.PI / 2.0;

		private static final class Sine {
			public static final double[] TABLE = new double[GRANULARITY];
			static {
				for (int i = 0; i < GRANULARITY; i++) {
					TABLE[i] = Math.sin(i / (double) GRANULARITY * Math.PI * 2.0);
				}
			}
		}

		private static final class Cosine {
			public static final double[] TABLE = new double[GRANULARITY];
			static {
				for (int i = 0; i < GRANULARITY; i++) {
					TABLE[i] = Math.cos(i / (double) GRANULARITY * Math.PI * 2.0);
				}
			}
		}

		private static final class Tangent {
			public static final double[] TABLE = new double[GRANULARITY];
			static {
				for (int i = 0; i < GRANULARITY; i++) {
					TABLE[i] = Math.tan(i / (double) GRANULARITY * Math.PI * 2.0);
				}
			}
		}

		////////////////////////////////////////////////////////////////////////

		public static double sin(final double ang) {
			return nearest(Sine.TABLE, ang);
		}

		public static double cos(final double ang) {
			return nearest(Cosine.TABLE, ang);
		}

		public static double tan(final double ang) {
			return nearest(Tangent.TABLE, ang);
		}

		////////////////////////////////////////////////////////////////////////

		public static double sinLerp(final double ang) {
			return lerp(Sine.TABLE, ang);
		}

		public static double cosLerp(final double ang) {
			return lerp(Cosine.TABLE, ang);
		}

		public static double tanLerp(final double ang) {
			return lerp(Tangent.TABLE, ang);
		}

		////////////////////////////////////////////////////////////////////////

		public static double sinSlerp(final double ang) {
			return slerp(Sine.TABLE, ang);
		}

		public static double cosSlerp(final double ang) {
			return slerp(Cosine.TABLE, ang);
		}

		public static double tanSlerp(final double ang) {
			return slerp(Tangent.TABLE, ang);
		}

		////////////////////////////////////////////////////////////////////////

		static int radiansToTable(final double ang) {
			return (int) (ang * ANGLE_MULTIPLIER) & ANGLE_MASK;
		}

		private static double nearest(final double[] tbl, final double ang) {
			return tbl[radiansToTable(ang)];
		}

		private static double lerp(final double[] tbl, final double ang) {
			final int idx0 = radiansToTable(ang);
			final int idx1 = idx0 + 1 & ANGLE_MASK;
			final double delta = (ang * ANGLE_MULTIPLIER - idx0) % GRANULARITY;
			return MathEx.lerp(tbl[idx0], tbl[idx1], delta);
		}

		private static double slerp(final double[] tbl, final double ang) {
			final int idx1 = radiansToTable(ang);
			final int idx0 = idx1 - 1 & ANGLE_MASK;
			final int idx2 = idx1 + 1 & ANGLE_MASK;
			final int idx3 = idx1 + 2 & ANGLE_MASK;
			final double delta = (ang * ANGLE_MULTIPLIER - idx1) % GRANULARITY;
			return MathEx.slerp(tbl[idx0], tbl[idx1], tbl[idx2], tbl[idx3], delta);
		}
	}
}
