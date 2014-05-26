package com.ivan.utils.collections.map;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cern.colt.map.OpenIntObjectHashMap;
import cern.colt.map.OpenLongObjectHashMap;

import com.ivan.utils.collections.map.IntMap;
import com.ivan.utils.collections.map.LongMap;
import com.ivan.utils.time.Stopwatch;

public class PrimitiveMapPerformanceTest {
	static final NumberFormat fmt = NumberFormat.getInstance(Locale.ENGLISH);
	private static final NumberFormat decFmt = NumberFormat.getInstance(Locale.ENGLISH);
	private static final NumberFormat pctFmt = NumberFormat.getPercentInstance(Locale.ENGLISH);
	static {
		fmt.setMaximumFractionDigits(0);
		decFmt.setMaximumFractionDigits(2);
		pctFmt.setMaximumFractionDigits(2);
	}

	static final Object obj = new Object();

	public static void main(final String[] args) {
		final TestResults results = new TestResults();
		warmup();
		testMaps(20000000, 3, results);
		testMaps(10000000, 3, results);
		testMaps(1000000, 10, results);
		testMaps(100000, 100, results);
		testMaps(10000, 1000, results);
		testMaps(1000, 10000, results);
		testMaps(100, 100000, results);
		testMaps(10, 1000000, results);
		testMaps(1, 10000000, results);
		results.print();
	}

	private static void warmup() {
		System.out.print("Warming up...");
		final Map<Integer, Object> ihm = new HashMap<Integer, Object>();
		final IntMap<Object> im = new IntMap<Object>();
		final Map<Integer, Object> imv = im.asMap();
		final OpenIntObjectHashMap cim = new OpenIntObjectHashMap();
		final Map<Long, Object> lhm = new HashMap<Long, Object>();
		final LongMap<Object> lm = new LongMap<Object>();
		final Map<Long, Object> lmv = lm.asMap();
		final OpenLongObjectHashMap clm = new OpenLongObjectHashMap();

		for (int i = 0; i < 2000000; i++) {
			ihm.put(i, obj);
			im.put(i, obj);
			imv.put(i, obj);
			cim.put(i, obj);
			lhm.put((long) i, obj);
			lm.put(i, obj);
			lmv.put((long) i, obj);
			clm.put(i, obj);

			ihm.get(i);
			im.get(i);
			imv.get(i);
			cim.get(i);
			lhm.get((long) i);
			lm.get(i);
			lmv.get(i);
			clm.get(i);

			ihm.containsKey(i);
			im.containsKey(i);
			imv.containsKey(i);
			cim.containsKey(i);
			lhm.containsKey(i);
			lm.containsKey(i);
			lmv.containsKey(i);
			clm.containsKey(i);

			ihm.remove(i);
			im.remove(i);
			imv.remove(i);
			cim.removeKey(i);
			lhm.remove((long) i);
			lm.remove(i);
			lmv.remove(i);
			clm.removeKey(i);
		}
		System.out.println(" Done!");
		System.out.println("--------------------");
	}

	private static void testMaps(final int count, final int repeats, final TestResults results) {
		testPut(count, repeats, results);
		testGet(count, repeats, results);
		testRemove(count, repeats, results);
		testContainsKey(count, repeats, results);
		System.out.println("====================");
		results.incTests();
	}

	private static void testPut(final int count, final int repeats, final TestResults results) {
		System.out.println("Putting " + count + " items " + repeats + " times");

		System.out.println();
		testIntMap(repeats, TestResults.TestType.Put, results,
				new IntegerHashMapTestKernel(count) {
					@Override
					protected void doIteration(final int i) {
						value.put(i, obj);
					}
				}, new IntMapTestKernel(count) {
					@Override
					protected void doIteration(final int i) {
						value.put(i, obj);
					}
				}, new CernIntMapTestKernel(count) {
					@Override
					protected void doIteration(final int i) {
						value.put(i, obj);
					}
				});
		System.out.println();
		testLongMap(repeats, TestResults.TestType.Put, results,
				new LongHashMapTestKernel(count) {
					@Override
					protected void doIteration(final long i) {
						value.put(i, obj);
					}
				}, new LongMapTestKernel(count) {
					@Override
					protected void doIteration(final long i) {
						value.put(i, obj);
					}
				}, new CernLongMapTestKernel(count) {
					@Override
					protected void doIteration(final long i) {
						value.put(i, obj);
					}
				});
		System.out.println("--------------------");
	}

	private static void testGet(final int count, final int repeats, final TestResults results) {
		System.out.println("Getting " + count + " items " + repeats + " times");

		System.out.println();
		testIntMap(repeats, TestResults.TestType.Get, results,
				new IntegerHashMapTestKernel(count) {
					@Override
					public void init() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.get(i);
					}
				}, new IntMapTestKernel(count) {
					@Override
					public void init() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.get(i);
					}
				}, new CernIntMapTestKernel(count) {
					@Override
					public void init() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.get(i);
					}
				});
		System.out.println();
		testLongMap(repeats, TestResults.TestType.Get, results,
				new LongHashMapTestKernel(count) {
					@Override
					public void init() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.get(i);
					}
				}, new LongMapTestKernel(count) {
					@Override
					public void init() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.get(i);
					}
				}, new CernLongMapTestKernel(count) {
					@Override
					public void init() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.get(i);
					}
				});
		System.out.println("--------------------");
	}

	private static void testRemove(final int count, final int repeats, final TestResults results) {
		System.out.println("Removing " + count + " items " + repeats + " times");

		System.out.println();
		testIntMap(repeats, TestResults.TestType.Remove, results,
				new IntegerHashMapTestKernel(count) {
					@Override
					public void repeat() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.remove(i);
					}
				}, new IntMapTestKernel(count) {
					@Override
					public void repeat() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.remove(i);
					}
				}, new CernIntMapTestKernel(count) {
					@Override
					public void repeat() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.removeKey(i);
					}
				});
		System.out.println();
		testLongMap(repeats, TestResults.TestType.Remove, results,
				new LongHashMapTestKernel(count) {
					@Override
					public void repeat() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.remove(i);
					}
				}, new LongMapTestKernel(count) {
					@Override
					public void repeat() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.remove(i);
					}
				}, new CernLongMapTestKernel(count) {
					@Override
					public void repeat() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.removeKey(i);
					}
				});
		System.out.println("--------------------");
	}

	private static void testContainsKey(final int count, final int repeats, final TestResults results) {
		System.out.println("Checking existence of keys for " + count + " items " + repeats + " times");

		System.out.println();
		testIntMap(repeats, TestResults.TestType.ContainsKey, results,
				new IntegerHashMapTestKernel(count) {
					@Override
					public void init() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.containsKey(i);
					}
				}, new IntMapTestKernel(count) {
					@Override
					public void init() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.containsKey(i);
					}
				}, new CernIntMapTestKernel(count) {
					@Override
					public void init() {
						for (int i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final int i) {
						value.containsKey(i);
					}
				});
		System.out.println();
		testLongMap(repeats, TestResults.TestType.ContainsKey, results,
				new LongHashMapTestKernel(count) {
					@Override
					public void init() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.containsKey(i);
					}
				}, new LongMapTestKernel(count) {
					@Override
					public void init() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.containsKey(i);
					}
				}, new CernLongMapTestKernel(count) {
					@Override
					public void init() {
						for (long i = 0; i < count; i++) {
							value.put(i, obj);
						}
					}

					@Override
					protected void doIteration(final long i) {
						value.containsKey(i);
					}
				});
		System.out.println("--------------------");
	}

	private static void testIntMap(final int repeats, final TestResults.TestType testType, final TestResults results, final IntegerHashMapTestKernel hmKernel, final IntMapTestKernel imKernel, final CernIntMapTestKernel cimKernel) {
		hmKernel.setValue(new HashMap<Integer, Object>());
		final long hmTime = measureTest(repeats, hmKernel);
		System.out.println("HashMap<Integer, Object>: " + String.format("% 6d ms", hmTime / 1000000));
		hmKernel.setValue(null);
		System.gc();

		imKernel.setValue(new IntMap<Object>());
		final long mTime = measureTest(repeats, imKernel);
		System.out.println("IntMap<Object>          : " + String.format("% 6d ms", mTime / 1000000));
		imKernel.setValue(null);
		System.gc();

		hmKernel.setValue(new IntMap<Object>().asMap());
		final long mvTime = measureTest(repeats, hmKernel);
		System.out.println("IntMap<Object>.asMap()  : " + String.format("% 6d ms", mvTime / 1000000));
		hmKernel.setValue(null);
		System.gc();

		cimKernel.setValue(new OpenIntObjectHashMap());
		final long cmTime = measureTest(repeats, cimKernel);
		System.out.println("OpenIntObjectHashMap    : " + String.format("% 6d ms", cmTime / 1000000));
		cimKernel.setValue(null);
		System.gc();

		printSpeedComparison(new TimeEntry("Integer HashMap", hmTime),
				new TimeEntry("IntMap", mTime),
				new TimeEntry("IntMap View", mvTime),
				new TimeEntry("CERN IntHashMap", cmTime));

		testType.addIntResult(results, hmTime, mTime, mvTime, cmTime);
	}

	private static void testLongMap(final int repeats, final TestResults.TestType testType, final TestResults results, final LongHashMapTestKernel hmKernel, final LongMapTestKernel lmKernel, final CernLongMapTestKernel clmKernel) {
		hmKernel.setValue(new HashMap<Long, Object>());
		final long hmTime = measureTest(repeats, hmKernel);
		System.out.println("HashMap<Long, Object>   : " + String.format("% 6d ms", hmTime / 1000000));
		hmKernel.setValue(null);
		System.gc();

		lmKernel.setValue(new LongMap<Object>());
		final long mTime = measureTest(repeats, lmKernel);
		System.out.println("LongMap<Object>         : " + String.format("% 6d ms", mTime / 1000000));
		lmKernel.setValue(null);
		System.gc();

		hmKernel.setValue(new LongMap<Object>().asMap());
		final long mvTime = measureTest(repeats, hmKernel);
		System.out.println("LongMap<Object>.asMap() : " + String.format("% 6d ms", mvTime / 1000000));
		hmKernel.setValue(null);
		System.gc();

		clmKernel.setValue(new OpenLongObjectHashMap());
		final long cmTime = measureTest(repeats, clmKernel);
		System.out.println("OpenLongObjectHashMap   : " + String.format("% 6d ms", cmTime / 1000000));
		clmKernel.setValue(null);
		System.gc();

		printSpeedComparison(new TimeEntry("Long HashMap", hmTime),
				new TimeEntry("LongMap", mTime),
				new TimeEntry("LongMap View", mvTime),
				new TimeEntry("CERN LongHashMap", cmTime));

		testType.addLongResult(results, hmTime, mTime, mvTime, cmTime);
	}

	private static final TestKernel nullKernel = new TestKernel() {
		@Override
		public void run() {
		}

		@Override
		public void repeat() {
		}

		@Override
		public void init() {
		}
	};

	private static long measureTest(final int repeats, final TestKernel kernel) {
		final Stopwatch sw = new Stopwatch();

		// calculate loop overhead
		sw.start();
		for (int k = 0; k < repeats; k++) {
			sw.pause();
			nullKernel.repeat();

			sw.resume();
			nullKernel.run();
		}
		final long overhead = sw.timeElapsedNanos();
		sw.reset();

		kernel.init();

		// now run the test
		sw.start();
		for (int k = 0; k < repeats; k++) {
			sw.pause();
			kernel.repeat();

			sw.resume();
			kernel.run();
		}
		return sw.timeElapsedNanos() - overhead;
	}

	private static final class TimeEntry implements Comparable<TimeEntry> {
		final String name;
		final long time;

		public TimeEntry(final String name, final long time) {
			super();
			this.name = name;
			this.time = time;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (time ^ time >>> 32);
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TimeEntry other = (TimeEntry) obj;
			if (time != other.time) {
				return false;
			}
			return true;
		}

		@Override
		public int compareTo(final TimeEntry o) {
			return time > o.time ? 1 : time < o.time ? -1 : 0;
		}
	}

	static void printSpeedComparison(final TimeEntry... entries) {
		printSpeedComparison("", entries);
	}

	static void printSpeedComparison(final String prefix, final TimeEntry... entries) {
		Arrays.sort(entries);
		for (int i = 1; i < entries.length; i++) {
			final TimeEntry faster = entries[i - 1];
			final TimeEntry slower = entries[i];
			System.out.println(prefix + faster.name + " is " + getSpeedString(faster.time, slower.time) + " faster than " + slower.name);
		}
	}

	private static String getSpeedString(final long faster, final long slower) {
		final double pct = (double) slower / faster - 1.0;
		if (pct <= 1.0) {
			return pctFmt.format(pct);
		}
		return decFmt.format(pct) + " times";
	}

	private static interface TestKernel {
		void init();

		void repeat();

		void run();
	}

	private static abstract class AbstractOneArgTestKernel<T> implements TestKernel {
		protected T value;

		public AbstractOneArgTestKernel() {
		}

		public void setValue(final T value) {
			this.value = value;
		}
	}

	private static abstract class AbstractIteratedIntTestKernel<T> extends AbstractOneArgTestKernel<T> {
		private final int count;

		public AbstractIteratedIntTestKernel(final int count) {
			this.count = count;
		}

		@Override
		public void init() {
		}

		@Override
		public void repeat() {
		}

		@Override
		public void run() {
			for (int i = 0; i < count; i++) {
				doIteration(i);
			}
		}

		protected abstract void doIteration(int i);
	}

	private static abstract class AbstractIteratedLongTestKernel<T> extends AbstractOneArgTestKernel<T> {
		private final long count;

		public AbstractIteratedLongTestKernel(final long count) {
			this.count = count;
		}

		@Override
		public void init() {
		}

		@Override
		public void repeat() {
		}

		@Override
		public void run() {
			for (long i = 0; i < count; i++) {
				doIteration(i);
			}
		}

		protected abstract void doIteration(long i);
	}

	private static abstract class IntegerHashMapTestKernel extends AbstractIteratedIntTestKernel<Map<Integer, Object>> {
		public IntegerHashMapTestKernel(final int count) {
			super(count);
		}
	}

	private static abstract class IntMapTestKernel extends AbstractIteratedIntTestKernel<IntMap<Object>> {
		public IntMapTestKernel(final int count) {
			super(count);
		}
	}

	private static abstract class CernIntMapTestKernel extends AbstractIteratedIntTestKernel<OpenIntObjectHashMap> {
		public CernIntMapTestKernel(final int count) {
			super(count);
		}
	}

	private static abstract class LongHashMapTestKernel extends AbstractIteratedLongTestKernel<Map<Long, Object>> {
		public LongHashMapTestKernel(final long count) {
			super(count);
		}
	}

	private static abstract class LongMapTestKernel extends AbstractIteratedLongTestKernel<LongMap<Object>> {
		public LongMapTestKernel(final long count) {
			super(count);
		}
	}

	private static abstract class CernLongMapTestKernel extends AbstractIteratedLongTestKernel<OpenLongObjectHashMap> {
		public CernLongMapTestKernel(final long count) {
			super(count);
		}
	}

	private static class TestResults {
		public static enum TestType {
			Put {
				@Override
				protected Times getTimes(final TestResults results) {
					return results.putTimes;
				}
			},
			Get {
				@Override
				protected Times getTimes(final TestResults results) {
					return results.getTimes;
				}
			},
			Remove {
				@Override
				protected Times getTimes(final TestResults results) {
					return results.removeTimes;
				}
			},
			ContainsKey {
				@Override
				protected Times getTimes(final TestResults results) {
					return results.containsKeyTimes;
				}
			};

			public void addIntResult(final TestResults results, final long hmTime, final long mTime, final long mvTime, final long cmTime) {
				final Times times = getTimes(results);
				times.ihm += hmTime;
				times.im += mTime;
				times.imv += mvTime;
				times.cim += cmTime;
			}

			public void addLongResult(final TestResults results, final long hmTime, final long mTime, final long mvTime, final long cmTime) {
				final Times times = getTimes(results);
				times.lhm += hmTime;
				times.lm += mTime;
				times.lmv += mvTime;
				times.clm += cmTime;
			}

			protected abstract Times getTimes(TestResults results);
		}

		private static class Times {
			public Times() {
			}

			public long ihm; // Map<Integer, Object>
			public long im; //  IntMap<Object>
			public long imv; // IntMap<Object>.asMap()
			public long cim; // OpenIntObjectHashMap

			public long lhm; // Map<Long, Object>
			public long lm; //  LongMap<Object>
			public long lmv; // LongMap<Object>.asMap()
			public long clm; // OpenLongObjectHashMap

			public Times add(final Times times) {
				final Times t = new Times();
				t.ihm = ihm + times.ihm;
				t.im = im + times.im;
				t.imv = imv + times.imv;
				t.cim = cim + times.cim;

				t.lhm = lhm + times.lhm;
				t.lm = lm + times.lm;
				t.lmv = lmv + times.lmv;
				t.clm = clm + times.clm;
				return t;
			}
		}

		private int numTests;

		final Times putTimes = new Times();
		final Times getTimes = new Times();
		final Times removeTimes = new Times();
		final Times containsKeyTimes = new Times();

		public TestResults() {
		}

		public void incTests() {
			numTests++;
		}

		public void print() {
			final Times totalTimes = putTimes.add(getTimes).add(removeTimes).add(containsKeyTimes);
			System.out.println("Test results: " + numTests + " tests executed");
			System.out.println();
			System.out.println("                    Integer                IntMap   CERN Int    Long                  LongMap  CERN Long");
			System.out.println("Total times:        HashMap     IntMap   Map View    HashMap    HashMap    LongMap   Map View    HashMap");
			printTimes("  put()        :", putTimes);
			printTimes("  get()        :", getTimes);
			printTimes("  remove()     :", removeTimes);
			printTimes("  containsKey():", containsKeyTimes);
			printTimes("  total        :", totalTimes);
			System.out.println("Average times:");
			printAvgTimes("  put()        :", putTimes);
			printAvgTimes("  get()        :", getTimes);
			printAvgTimes("  remove()     :", removeTimes);
			printAvgTimes("  containsKey():", containsKeyTimes);
			printAvgTimes("  total        :", totalTimes);
			System.out.println();
			System.out.println("Summary:");
			System.out.println("  put():");
			printSummary(putTimes);
			System.out.println("  get():");
			printSummary(getTimes);
			System.out.println("  remove():");
			printSummary(removeTimes);
			System.out.println("  containsKey():");
			printSummary(containsKeyTimes);
			System.out.println("  total:");
			printSummary(totalTimes);
		}

		private static void printSummary(final Times times) {
			printSpeedComparison("    ", new TimeEntry("Integer HashMap", times.ihm), new TimeEntry("IntMap", times.im), new TimeEntry("IntMap View", times.imv), new TimeEntry("CERN IntHashMap", times.cim));
			printSpeedComparison("    ", new TimeEntry("Long HashMap", times.lhm), new TimeEntry("LongMap", times.lm), new TimeEntry("LongMap View", times.lmv), new TimeEntry("CERN LongHashMap", times.clm));
		}

		private static void printTimes(final String name, final Times times) {
			System.out.println(name
					+ "  " + String.format("% 6d ms", times.ihm / 1000000)
					+ "  " + String.format("% 6d ms", times.im / 1000000)
					+ "  " + String.format("% 6d ms", times.imv / 1000000)
					+ "  " + String.format("% 6d ms", times.cim / 1000000)
					+ "  " + String.format("% 6d ms", times.lhm / 1000000)
					+ "  " + String.format("% 6d ms", times.lm / 1000000)
					+ "  " + String.format("% 6d ms", times.lmv / 1000000)
					+ "  " + String.format("% 6d ms", times.clm / 1000000)
					);
		}

		private void printAvgTimes(final String name, final Times times) {
			System.out.println(name
					+ "  " + String.format("% 6.0f ms", (double) times.ihm / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.im / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.imv / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.cim / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.lhm / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.lm / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.lmv / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.clm / numTests / 1000000)
					);
		}
	}
}
