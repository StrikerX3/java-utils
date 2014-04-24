package com.ivan.utils.collections;

import java.util.Map;
import java.util.WeakHashMap;

public class MapBuilderCopyFromMapTest {
	public static void main(final String[] args) {
		final Map<Integer, String> baseMap = MapBuilder.<Integer, String> linkedHashMap()
				.put(123, "one")
				.put(456, "two")
				.put(789, "three")
				.build();

		final Map<Integer, String> hashMap = MapBuilder.hashMap(baseMap)
				.put(101, "four")
				.build();
		final Map<Integer, String> linkedHashMap = MapBuilder.linkedHashMap(baseMap)
				.put(101, "four")
				.build();
		final Map<Integer, String> treeMap = MapBuilder.treeMap(baseMap)
				.put(101, "four")
				.build();
		final Map<Integer, String> intMap = MapBuilder.intMap(baseMap)
				.put(101, "four")
				.build();
		final Map<Integer, String> customMap = MapBuilder.map(new WeakHashMap<Integer, String>(baseMap))
				.put(101, "four")
				.build();

		printMap(hashMap);
		printMap(linkedHashMap);
		printMap(treeMap);
		printMap(intMap);
		printMap(customMap);
	}

	private static void printMap(final Map<Integer, String> map) {
		System.out.println(map + " -- " + map.getClass().getSimpleName());
	}
}
