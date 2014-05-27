package com.ivan.utils.collections.map;

import java.util.Collection;
import java.util.Iterator;

import com.ivan.utils.collections.map.IntMap;

public class IntMapValuesTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");

		final Collection<String> values = map.values();
		System.out.println(values);
		System.out.println(map);

		values.remove("b");
		System.out.println();
		System.out.println(values);
		System.out.println(map);

		map.remove(80);
		System.out.println();
		System.out.println(values);
		System.out.println(map);

		final Iterator<String> it = values.iterator();
		it.next();
		it.next();
		it.remove();
		System.out.println();
		System.out.println(values);
		System.out.println(map);

		values.clear();
		System.out.println();
		System.out.println(values);
		System.out.println(map);
	}
}
