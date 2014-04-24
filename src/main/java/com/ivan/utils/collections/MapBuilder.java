package com.ivan.utils.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public final class MapBuilder<K, V> {
	// TODO strong/soft/weak keys
	// TODO strong/soft/weak values
	private final Map<K, V> map;

	private MapBuilder(final Map<K, V> map) {
		this.map = map;
	}

	public static <K, V> MapBuilder<K, V> map(final Map<K, V> map) {
		return new MapBuilder<K, V>(map);
	}

	public static <K, V> MapBuilder<K, V> hashMap() {
		return map(new HashMap<K, V>());
	}

	public static <K, V> MapBuilder<K, V> treeMap() {
		return map(new TreeMap<K, V>());
	}

	public static <K, V> MapBuilder<K, V> linkedHashMap() {
		return map(new LinkedHashMap<K, V>());
	}

	public static <V> MapBuilder<Integer, V> intMap() {
		return map(new IntMap<V>().asMap());
	}

	public static <V> MapBuilder<Long, V> longMap() {
		return map(new LongMap<V>().asMap());
	}

	public static <K, V> MapBuilder<K, V> hashMap(final Map<K, V> map) {
		return map(new HashMap<K, V>(map));
	}

	public static <K, V> MapBuilder<K, V> treeMap(final Map<K, V> map) {
		return map(new TreeMap<K, V>(map));
	}

	public static <K, V> MapBuilder<K, V> linkedHashMap(final Map<K, V> map) {
		return map(new LinkedHashMap<K, V>(map));
	}

	public static <V> MapBuilder<Integer, V> intMap(final Map<Integer, V> map) {
		return map(new IntMap<V>(map).asMap());
	}

	public static <V> MapBuilder<Long, V> longMap(final Map<Long, V> map) {
		return map(new LongMap<V>(map).asMap());
	}

	public MapBuilder<K, V> put(final K key, final V value) {
		map.put(key, value);
		return this;
	}

	public MapBuilder<K, V> putAll(final Map<K, V> map) {
		this.map.putAll(map);
		return this;
	}

	public Map<K, V> build() {
		return map;
	}
}
