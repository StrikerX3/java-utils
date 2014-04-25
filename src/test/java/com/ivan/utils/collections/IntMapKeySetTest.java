package com.ivan.utils.collections;

import java.util.Iterator;
import java.util.Set;

public class IntMapKeySetTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");

		final Set<Integer> keys = map.keySet();
		System.out.println(keys);
		System.out.println(map);

		keys.remove(32);
		System.out.println();
		System.out.println(keys);
		System.out.println(map);

		map.remove(80);
		System.out.println();
		System.out.println(keys);
		System.out.println(map);

		final Iterator<Integer> it = keys.iterator();
		it.next();
		it.next();
		it.remove();
		System.out.println();
		System.out.println(keys);
		System.out.println(map);

		keys.clear();
		System.out.println();
		System.out.println(keys);
		System.out.println(map);
	}
}
