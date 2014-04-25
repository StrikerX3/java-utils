package com.ivan.utils.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

public class IntMapKeySetIteratorTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");

		final Set<Integer> keys = map.keySet();
		final Iterator<Integer> it1 = keys.iterator();
		final Iterator<Integer> it2 = keys.iterator();
		it1.next();
		it1.next();
		it2.next();
		it1.remove();
		try {
			it2.next();
			System.out.println("bad");
		} catch (final ConcurrentModificationException e) {
			System.out.println("good");
		}
		keys.clear();

		try {
			it1.next();
			System.out.println("bad");
		} catch (final ConcurrentModificationException e) {
			System.out.println("good");
		}
		try {
			it2.next();
			System.out.println("bad");
		} catch (final ConcurrentModificationException e) {
			System.out.println("good");
		}
	}
}
