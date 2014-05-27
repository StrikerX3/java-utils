package com.ivan.utils.collections.map;

import java.util.Iterator;
import java.util.Set;

import com.ivan.utils.collections.map.IntMap;
import com.ivan.utils.collections.map.IntMap.Entry;

public class IntMapEntrySetTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");

		final Set<Entry<String>> entries = map.entrySet();
		System.out.println(entries);
		System.out.println(map);

		final Iterator<Entry<String>> it = entries.iterator();
		it.next();
		final Entry<String> secondEntry = it.next();
		for (final Entry<String> entry : entries) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
		entries.remove(secondEntry);
		System.out.println();
		System.out.println(entries);
		System.out.println(map);

		map.remove(80);
		System.out.println();
		System.out.println(entries);
		System.out.println(map);

		final Iterator<Entry<String>> it2 = entries.iterator();
		it2.next();
		it2.next();
		it2.remove();
		System.out.println();
		System.out.println(entries);
		System.out.println(map);

		entries.clear();
		System.out.println();
		System.out.println(entries);
		System.out.println(map);
	}
}
