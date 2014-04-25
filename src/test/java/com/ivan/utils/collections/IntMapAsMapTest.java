package com.ivan.utils.collections;

import java.util.Map;

public class IntMapAsMapTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");

		final Map<Integer, String> asMap = map.asMap();
		System.out.println(asMap);
		System.out.println(map);

		asMap.put(25, "g");
		System.out.println();
		System.out.println(asMap);
		System.out.println(map);

		map.put(50, "h");
		System.out.println();
		System.out.println(asMap);
		System.out.println(map);

		asMap.remove(50);
		System.out.println();
		System.out.println(asMap);
		System.out.println(map);

		map.remove(25);
		System.out.println();
		System.out.println(asMap);
		System.out.println(map);

		System.out.println();
		System.out.println("contains  key 25? " + asMap.containsKey(25));
		System.out.println("contains  key 16? " + asMap.containsKey(16));
		System.out.println("value for key 16: " + asMap.get(16));

		System.out.println();
		System.out.println("contains  value f? " + asMap.containsValue("f"));
		System.out.println("contains  value g? " + asMap.containsValue("g"));

		asMap.clear();
		System.out.println();
		System.out.println(asMap);
		System.out.println(map);
	}
}
