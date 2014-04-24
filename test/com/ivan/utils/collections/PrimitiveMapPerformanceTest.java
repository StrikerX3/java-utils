package com.ivan.utils.collections;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ivan.utils.time.Stopwatch;

public class PrimitiveMapPerformanceTest {
	static final NumberFormat fmt = NumberFormat.getInstance(Locale.ENGLISH);
	private static final NumberFormat pctFmt = NumberFormat.getPercentInstance(Locale.ENGLISH);
	static {
		fmt.setMaximumFractionDigits(0);
		pctFmt.setMaximumFractionDigits(2);
	}

	static final Object obj = new Object();

	public static void main(final String[] args) {
		final TestResults results = new TestResults();
		warmup();
		testMaps(1, 100000000, results);
		testMaps(10, 10000000, results);
		testMaps(100, 1000000, results);
		testMaps(1000, 100000, results);
		testMaps(10000, 10000, results);
		testMaps(100000, 1000, results);
		testMaps(1000000, 100, results);
		testMaps(10000000, 10, results);
		results.print();
	}

	private static void warmup() {
		System.out.print("Warming up...");
		final Map<Integer, Object> ihm = new HashMap<Integer, Object>();
		final IntMap<Object> im = new IntMap<Object>();
		final Map<Long, Object> lhm = new HashMap<Long, Object>();
		final LongMap<Object> lm = new LongMap<Object>();

		for (int i = 0; i < 1000000; i++) {
			ihm.put(i, obj);
			im.put(i, obj);
			lhm.put((long) i, obj);
			lm.put(i, obj);

			ihm.get(i);
			im.get(i);
			lhm.get((long) i);
			lm.get(i);

			ihm.containsKey(i);
			im.containsKey(i);
			lhm.containsKey(i);
			lm.containsKey(i);

			ihm.remove(i);
			im.remove(i);
			lhm.remove((long) i);
			lm.remove(i);
		}
		System.out.println(" Done!");
		System.out.println("--------------------");
	}

	private static void testMaps(final int count, final int repeats, final TestResults results) {
		testPut(count, repeats, results);
		testGet(count, repeats, results);
		testRemove(count, repeats, results);
		testContainsKey(count, repeats, results);
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
				});
		System.out.println("--------------------");
	}

	private static void testIntMap(final int repeats, final TestResults.TestType testType, final TestResults results, final IntegerHashMapTestKernel hmKernel, final IntMapTestKernel lmKernel) {
		hmKernel.setValue(new HashMap<Integer, Object>());
		final long hmTime = measureTest(repeats, hmKernel);
		System.out.println("HashMap<Integer, Object>: " + String.format("% 6d ms", hmTime / 1000000));
		hmKernel.setValue(null);
		System.gc();

		lmKernel.setValue(new IntMap<Object>());
		final long mTime = measureTest(repeats, lmKernel);
		System.out.println("IntMap<Object>          : " + String.format("% 6d ms", mTime / 1000000));
		lmKernel.setValue(null);
		System.gc();

		System.out.println(getStringForFaster("HashMap", "IntMap", hmTime, mTime));

		testType.addIntResult(results, hmTime, mTime);
	}

	private static void testLongMap(final int repeats, final TestResults.TestType testType, final TestResults results, final LongHashMapTestKernel hmKernel, final LongMapTestKernel lmKernel) {
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

		System.out.println(getStringForFaster("HashMap", "LongMap", hmTime, mTime));

		testType.addLongResult(results, hmTime, mTime);
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

	static String getStringForFaster(final String name1, final String name2, final long time1, final long time2) {
		if (time1 > time2) {
			return name2 + " is " + pctFmt.format((double) time1 / time2 - 1.0) + " faster than " + name1;
		}
		return name1 + " is " + pctFmt.format((double) time2 / time1 - 1.0) + " faster than " + name2;
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

			public void addIntResult(final TestResults results, final long hmTime, final long mTime) {
				final Times times = getTimes(results);
				times.ihm += hmTime;
				times.im += mTime;
			}

			public void addLongResult(final TestResults results, final long hmTime, final long mTime) {
				final Times times = getTimes(results);
				times.lhm += hmTime;
				times.lm += mTime;
			}

			protected abstract Times getTimes(TestResults results);
		}

		private static class Times {
			public Times() {
			}

			public long ihm;
			public long im;
			public long lhm;
			public long lm;

			public Times add(final Times times) {
				final Times t = new Times();
				t.ihm = ihm + times.ihm;
				t.im = im + times.im;
				t.lhm = lhm + times.lhm;
				t.lm = lm + times.lm;
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
			System.out.println("                    Integer               Long");
			System.out.println("Total times:        HashMap     IntMap    HashMap    LongMap");
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
			System.out.println("    " + getStringForFaster("HashMap", "IntMap", putTimes.ihm, putTimes.im));
			System.out.println("    " + getStringForFaster("HashMap", "LongMap", putTimes.lhm, putTimes.lm));
			System.out.println("  get():");
			System.out.println("    " + getStringForFaster("HashMap", "IntMap", getTimes.ihm, getTimes.im));
			System.out.println("    " + getStringForFaster("HashMap", "LongMap", getTimes.lhm, getTimes.lm));
			System.out.println("  remove():");
			System.out.println("    " + getStringForFaster("HashMap", "IntMap", removeTimes.ihm, removeTimes.im));
			System.out.println("    " + getStringForFaster("HashMap", "LongMap", removeTimes.lhm, removeTimes.lm));
			System.out.println("  containsKey():");
			System.out.println("    " + getStringForFaster("HashMap", "IntMap", containsKeyTimes.ihm, containsKeyTimes.im));
			System.out.println("    " + getStringForFaster("HashMap", "LongMap", containsKeyTimes.lhm, containsKeyTimes.lm));
			System.out.println("  total:");
			System.out.println("    " + getStringForFaster("HashMap", "IntMap", totalTimes.ihm, totalTimes.im));
			System.out.println("    " + getStringForFaster("HashMap", "LongMap", totalTimes.lhm, totalTimes.lm));
		}

		private void printTimes(final String name, final Times times) {
			System.out.println(name
					+ "  " + String.format("% 6d ms", times.ihm / 1000000)
					+ "  " + String.format("% 6d ms", times.im / 1000000)
					+ "  " + String.format("% 6d ms", times.lhm / 1000000)
					+ "  " + String.format("% 6d ms", times.lm / 1000000)
					);
		}

		private void printAvgTimes(final String name, final Times times) {
			System.out.println(name
					+ "  " + String.format("% 6.0f ms", (double) times.ihm / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.im / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.lhm / numTests / 1000000)
					+ "  " + String.format("% 6.0f ms", (double) times.lm / numTests / 1000000)
					);
		}
	}
}
