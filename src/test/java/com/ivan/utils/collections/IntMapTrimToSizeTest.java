package com.ivan.utils.collections;

public class IntMapTrimToSizeTest {
	public static void main(final String[] args) {
		final Runtime rt = Runtime.getRuntime();
		System.out.println(rt.totalMemory() - rt.freeMemory());
		final IntMap<Object> map = new IntMap<Object>();
		for (int i = 0; i < 10000000; i++) {
			map.put(i, null);
		}
		System.gc();
		System.out.println(rt.totalMemory() - rt.freeMemory());

		map.clear();
		System.gc();
		System.out.println(rt.totalMemory() - rt.freeMemory());
	}
}
