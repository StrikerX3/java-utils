package com.ivan.utils.collections;

public class IntMapRemoveTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");
		map.remove(80);
		map.remove(32);
		map.remove(0);
	}
}
