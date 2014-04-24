package com.ivan.utils.math;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import com.ivan.utils.system.MemoryTrack;
import com.ivan.utils.system.MemoryTrackVisitor;

public class FastMathTest {
	private static final NumberFormat fmt = NumberFormat.getInstance(Locale.ENGLISH);
	private static final NumberFormat pctFmt = NumberFormat.getPercentInstance(Locale.ENGLISH);
	static {
		fmt.setMaximumFractionDigits(2);
		pctFmt.setMaximumFractionDigits(2);
	}

	public static void main(final String[] args) {
		final MemoryTrack memTrack = new MemoryTrack("FastMath class", true);

		System.out.println("Initializing FastMath class...");
		System.out.println("  Granularity: " + FastMath.GRANULARITY_BITS + " bits");
		System.out.println("  Table size : " + FastMath.GRANULARITY + " entries");
		memTrack.record("Initialization");

		final MemoryTrack memTables = memTrack.start("Tables");
		final MemoryTrack memFloatTables = memTables.start("Float tables");
		FastMath.sin(0f);
		memFloatTables.record("Sine");
		FastMath.cos(0f);
		memFloatTables.record("Cosine");
		FastMath.tan(0f);
		memFloatTables.record("Tangent");
		memFloatTables.done();

		final MemoryTrack memDoubleTables = memTables.start("Double tables");
		FastMath.sin(0d);
		memDoubleTables.record("Sine");
		FastMath.cos(0d);
		memDoubleTables.record("Cosine");
		FastMath.tan(0d);
		memDoubleTables.record("Tangent");
		memDoubleTables.done();

		memTrack.done();

		final int step = 5;
		System.out.println();
		calcFloatErrors(step);
		System.out.println();
		calcDoubleErrors(step);
		System.out.println();
		testPerformance(2000000);
		System.out.println();

		System.out.println();
		System.out.println("Memory usage");
		System.out.println("------------");
		final SummaryVisitor summary = new SummaryVisitor("  ");
		memTrack.visit(summary);

		System.out.println();
		System.out.println("FastMath settings:");
		System.out.println("  Granularity: " + FastMath.GRANULARITY_BITS + " bits");
		System.out.println("  Table size : " + FastMath.GRANULARITY + " entries");
	}

	private static void calcFloatErrors(final int step) {
		double absErrorTot;
		int numSamples;

		System.out.println("Float errors:");
		System.out.println("       FastMath");
		System.out.println("  i     sin(i)     Math.sin(i)     Error");
		absErrorTot = 0.0;
		numSamples = 0;
		for (int i = 0; i <= 720; i++) {
			final float sin1 = FastMath.sin((float) Math.toRadians(i));
			final float sin2 = (float) Math.sin(Math.toRadians(i));
			final float error = sin1 - sin2;
			absErrorTot += Math.abs(error);
			numSamples++;
			if (i % step == 0) {
				System.out.format("%3d  % .08f  % .08f  % .08f\n", i, sin1, sin2, error);
			}
		}
		System.out.format("Average absolute error: % .08f\n", absErrorTot / numSamples);

		System.out.println();
		System.out.println("       FastMath");
		System.out.println("  i   sinLerp(i)   Math.sin(i)     Error");
		absErrorTot = 0.0;
		numSamples = 0;
		for (int i = 0; i <= 720; i++) {
			final float sin1 = FastMath.sinLerp((float) Math.toRadians(i));
			final float sin2 = (float) Math.sin(Math.toRadians(i));
			final float error = sin1 - sin2;
			absErrorTot += Math.abs(error);
			numSamples++;
			if (i % step == 0) {
				System.out.format("%3d  % .08f  % .08f  % .08f\n", i, sin1, sin2, error);
			}
		}
		System.out.format("Average absolute error: % .08f\n", absErrorTot / numSamples);

		System.out.println();
		System.out.println("       FastMath");
		System.out.println("  i   sinSlerp(i)  Math.sin(i)     Error");
		absErrorTot = 0.0;
		numSamples = 0;
		for (int i = 0; i <= 720; i++) {
			final float sin1 = FastMath.sinSlerp((float) Math.toRadians(i));
			final float sin2 = (float) Math.sin(Math.toRadians(i));
			final float error = sin1 - sin2;
			absErrorTot += Math.abs(error);
			numSamples++;
			if (i % step == 0) {
				System.out.format("%3d  % .08f  % .08f  % .08f\n", i, sin1, sin2, error);
			}
		}
		System.out.format("Average absolute error: % .08f\n", absErrorTot / numSamples);
	}

	private static void calcDoubleErrors(final int step) {
		double absErrorTot;
		int numSamples;

		System.out.println("Double errors:");
		System.out.println("  i     FastMath.sin(i)        Math.sin(i)             Error");
		absErrorTot = 0.0;
		numSamples = 0;
		for (int i = 0; i <= 720; i++) {
			final double sin1 = FastMath.sin(Math.toRadians(i));
			final double sin2 = Math.sin(Math.toRadians(i));
			final double error = sin1 - sin2;
			absErrorTot += Math.abs(error);
			numSamples++;
			if (i % step == 0) {
				System.out.format("%3d  % .016f  % .016f  % .016f\n", i, sin1, sin2, error);
			}
		}
		System.out.format("Average absolute error: % .016f\n", absErrorTot / numSamples);

		System.out.println();
		System.out.println("  i   FastMath.sinLerp(i)      Math.sin(i)             Error");
		absErrorTot = 0.0;
		numSamples = 0;
		for (int i = 0; i <= 720; i++) {
			final double sin1 = FastMath.sinLerp(Math.toRadians(i));
			final double sin2 = Math.sin(Math.toRadians(i));
			final double error = sin1 - sin2;
			absErrorTot += Math.abs(error);
			numSamples++;
			if (i % step == 0) {
				System.out.format("%3d  % .016f  % .016f  % .016f\n", i, sin1, sin2, error);
			}
		}
		System.out.format("Average absolute error: % .016f\n", absErrorTot / numSamples);

		System.out.println();
		System.out.println("  i  FastMath.sinSlerp(i)      Math.sin(i)             Error");
		absErrorTot = 0.0;
		numSamples = 0;
		for (int i = 0; i <= 720; i++) {
			final double sin1 = FastMath.sinSlerp(Math.toRadians(i));
			final double sin2 = Math.sin(Math.toRadians(i));
			final double error = sin1 - sin2;
			absErrorTot += Math.abs(error);
			numSamples++;
			if (i % step == 0) {
				System.out.format("%3d  % .016f  % .016f  % .016f\n", i, sin1, sin2, error);
			}
		}
		System.out.format("Average absolute error: % .016f\n", absErrorTot / numSamples);
	}

	private static void testPerformance(final int num) {
		System.out.println("Calculating " + num + " sines...");
		long time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			Math.sin(i);
			Math.cos(i);
			Math.tan(i);
		}
		final long mathTime = System.nanoTime() - time;
		System.out.println("Math time                   : " + mathTime / 1000000 + " ms");

		time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			final double d = i;
			FastMath.sin(d);
			FastMath.cos(d);
			FastMath.tan(d);
		}
		final long fastMathNearestTime = System.nanoTime() - time;
		System.out.println("FastMath double nearest time: " + fastMathNearestTime / 1000000 + " ms (" + format((double) mathTime / fastMathNearestTime - 1.0) + " faster than Math)");

		time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			final double d = i;
			FastMath.sinLerp(d);
			FastMath.cosLerp(d);
			FastMath.tanLerp(d);
		}
		final long fastMathLerpTime = System.nanoTime() - time;
		System.out.println("FastMath double lerp time   : " + fastMathLerpTime / 1000000 + " ms (" + format((double) mathTime / fastMathLerpTime - 1.0) + " faster than Math)");

		time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			final double d = i;
			FastMath.sinSlerp(d);
			FastMath.cosSlerp(d);
			FastMath.tanSlerp(d);
		}
		final long fastMathSlerpTime = System.nanoTime() - time;
		System.out.println("FastMath double slerp time  : " + fastMathSlerpTime / 1000000 + " ms (" + format((double) mathTime / fastMathSlerpTime - 1.0) + " faster than Math)");

		time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			FastMath.sin(i);
			FastMath.cos(i);
			FastMath.tan(i);
		}
		final long fastMathFloatNearestTime = System.nanoTime() - time;
		System.out.println("FastMath float nearest time : " + fastMathFloatNearestTime / 1000000 + " ms (" + format((double) mathTime / fastMathFloatNearestTime - 1.0) + " faster than Math)");

		time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			FastMath.sinLerp(i);
			FastMath.cosLerp(i);
			FastMath.tanLerp(i);
		}
		final long fastMathFloatLerpTime = System.nanoTime() - time;
		System.out.println("FastMath float lerp time    : " + fastMathFloatLerpTime / 1000000 + " ms (" + format((double) mathTime / fastMathFloatLerpTime - 1.0) + " faster than Math)");

		time = System.nanoTime();
		for (int i = 0; i < num; i++) {
			FastMath.sinSlerp(i);
			FastMath.cosSlerp(i);
			FastMath.tanSlerp(i);
		}
		final long fastMathFloatSlerpTime = System.nanoTime() - time;
		System.out.println("FastMath float slerp time   : " + fastMathFloatSlerpTime / 1000000 + " ms (" + format((double) mathTime / fastMathFloatSlerpTime - 1.0) + " faster than Math)");
	}

	private static String format(final double val) {
		if (val < 1.0) {
			return pctFmt.format(val);
		}
		return fmt.format(val) + "x";
	}
}

class SummaryVisitor implements MemoryTrackVisitor {
	private int longest = 0;
	private final String prefix;

	public SummaryVisitor(final String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void visit(final MemoryTrack memory, final int depth) {
		final int len = memory.getName().length() + depth * prefix.length();
		longest = Math.max(longest, len);
	}

	@Override
	public void leave(final MemoryTrack memory, final int depth) {
		if (depth == 0) {
			memory.visit(new SummaryOutputVisitor(prefix, longest));
		}
	}
}

class SummaryOutputVisitor implements MemoryTrackVisitor {
	private final String padding;
	private final String prefix;

	public SummaryOutputVisitor(final String prefix, final int paddingLength) {
		final char[] c = new char[paddingLength];
		Arrays.fill(c, ' ');
		padding = String.copyValueOf(c);
		this.prefix = prefix;
	}

	@Override
	public void visit(final MemoryTrack memory, final int depth) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append(prefix);
		}
		sb.append(memory.getName())
				.append(padding.substring(memory.getName().length() + depth * 2))
				.append(": ")
				.append(memory.getUsed() / 1024)
				.append(" KB");
		System.out.println(sb.toString());
	}

	/**
	 * @param memory unused
	 * @param depth unused
	 */
	@Override
	public void leave(final MemoryTrack memory, final int depth) {
	}
}
