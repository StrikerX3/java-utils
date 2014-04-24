package com.ivan.utils.collections;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class IntMapValuesIteratorTest {
	public static void main(final String[] args) {
		final IntMap<String> map = new IntMap<String>(16);
		map.put(0, "a");
		map.put(16, "b");
		map.put(32, "c");
		map.put(48, "d");
		map.put(64, "e");
		map.put(80, "f");

		final Collection<String> values = map.values();
		final Iterator<String> it1 = values.iterator();
		final Iterator<String> it2 = values.iterator();
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
		values.clear();

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
