package com.ivan.utils.collections.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ivan.utils.collections.map.IntMap;
import com.ivan.utils.collections.map.IntMap.Entry;

public class IntMapBasicTests {
	public static void main(final String[] args) {
		final IntMap<String> m = new IntMap<String>();
		System.out.println("size(), isEmpty(): " + m.size() + ", " + m.isEmpty());

		System.out.println();
		System.out.println("put() multiples of 15 from 0 to 480");
		for (int i = 0; i < 32; i++) {
			final String str = m.put(i * 15, Integer.toString(i));
			if (str != null) {
				System.out.println("put(" + i + ") returned " + str);
			}
		}
		System.out.println("size(), isEmpty(): " + m.size() + ", " + m.isEmpty());

		System.out.println();
		System.out.println("put() integers from 0 to 32");
		for (int i = 0; i < 32; i++) {
			final String str = m.put(i, Integer.toString(i + 32));
			if (str != null) {
				System.out.println("put(" + i + ") returned " + str);
			}
		}
		System.out.println("size(), isEmpty(): " + m.size() + ", " + m.isEmpty());

		System.out.println();
		final int[] keys = m.keys();
		Arrays.sort(keys);
		System.out.println("keys()  : " + Arrays.toString(keys));
		System.out.println("keySet(): " + m.keySet());

		System.out.println();
		System.out.println("keys().length == size() ? "
				+ keys.length + " == " + m.size() + " ? "
				+ (keys.length == m.size()));
		System.out.println("keySet().size() == size() ? "
				+ m.keySet().size() + " == " + m.size() + " ? "
				+ (m.keySet().size() == m.size()));

		System.out.println();
		System.out.println("toString(): " + m);
		System.out.println("get(key)  : " + toStringGet(m));
		System.out.println("entrySet(): " + toStringEntrySet(m));

		System.out.println();
		System.out.println("values(): " + m.values());

		System.out.println();
		System.out.println("containsKey(), containsValue():");
		for (int i = 0; i < 75; i++) {
			final boolean ck = m.containsKey(i);
			final boolean cv = m.containsValue(Integer.toString(i));
			System.out.print((i < 10 ? " " : "") + i + ": " + (ck ? "K" : " ") + (cv ? "V" : " ") + "  ");
			if (i % 15 == 14) {
				System.out.println();
			}
		}

		System.out.println();
		for (int i = 60; i < 61; i++) {
			System.out.println("remove(" + i + ")    size()    keySet(): " + m.remove(i) + "    " + m.size() + "    " + m.keySet());
		}

		System.out.println();
		System.out.println("before clear():");
		System.out.println("  size()    : " + m.size());
		System.out.println("  isEmpty() : " + m.isEmpty());
		System.out.println("  toString(): " + m);
		System.out.println("  keys()    : " + Arrays.toString(m.keys()));
		System.out.println("  keySet()  : " + m.keySet());
		System.out.println("  values()  : " + m.values());
		System.out.println("  get()     : " + toStringGet(m));
		System.out.println("  entrySet(): " + toStringEntrySet(m));

		m.clear();
		System.out.println();
		System.out.println("after clear():");
		System.out.println("  size()    : " + m.size());
		System.out.println("  isEmpty() : " + m.isEmpty());
		System.out.println("  toString(): " + m);
		System.out.println("  keys()    : " + Arrays.toString(m.keys()));
		System.out.println("  keySet()  : " + m.keySet());
		System.out.println("  values()  : " + m.values());
		System.out.println("  get()     : " + toStringGet(m));
		System.out.println("  entrySet(): " + toStringEntrySet(m));

		final Map<Integer, String> hm = new HashMap<Integer, String>();
		for (int i = 100; i < 110; i++) {
			hm.put(i, Integer.toString(i * 2));
		}
		m.putAll(hm);
		System.out.println();
		System.out.println("after putAll(Map):");
		System.out.println("  size()    : " + m.size());
		System.out.println("  isEmpty() : " + m.isEmpty());
		System.out.println("  toString(): " + m);
		System.out.println("  keys()    : " + Arrays.toString(m.keys()));
		System.out.println("  keySet()  : " + m.keySet());
		System.out.println("  values()  : " + m.values());
		System.out.println("  get()     : " + toStringGet(m));
		System.out.println("  entrySet(): " + toStringEntrySet(m));

		final IntMap<String> m2 = new IntMap<String>();
		for (int i = 0; i <= 120; i += 15) {
			m2.put(i, Integer.toString(i * 5));
		}
		m.putAll(m2);
		System.out.println();
		System.out.println("after putAll(IntMap):");
		System.out.println("  size()    : " + m.size());
		System.out.println("  isEmpty() : " + m.isEmpty());
		System.out.println("  toString(): " + m);
		System.out.println("  keys()    : " + Arrays.toString(m.keys()));
		System.out.println("  keySet()  : " + m.keySet());
		System.out.println("  values()  : " + m.values());
		System.out.println("  get()     : " + toStringGet(m));
		System.out.println("  entrySet(): " + toStringEntrySet(m));

		System.out.println();
		final Map<Integer, String> toMap = m.asMap();
		System.out.println("toMap(): " + toMap);
	}

	private static String toStringGet(final IntMap<String> m) {
		final StringBuilder sb = new StringBuilder();
		sb.append('[');
		String sep = "";
		for (final int k : m.keys()) {
			sb.append(sep).append(k).append('=').append(m.get(k));
			sep = ", ";
		}
		sb.append(']');
		return sb.toString();
	}

	private static String toStringEntrySet(final IntMap<String> m) {
		final StringBuilder sb = new StringBuilder();
		sb.append('[');
		String sep = "";
		for (final Entry<String> e : m.entrySet()) {
			sb.append(sep).append(e.getKey()).append('=').append(e.getValue());
			sep = ", ";
		}
		sb.append(']');
		return sb.toString();
	}
}
