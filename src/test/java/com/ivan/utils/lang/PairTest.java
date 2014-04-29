package com.ivan.utils.lang;

import java.util.HashMap;
import java.util.Map;

public class PairTest {
	private static final class MyPair extends Pair<Integer, String> {
		public MyPair(final Integer num, final String name) {
			super(num, name);
		}
	}

	public static void main(final String[] args) {
		final MyPair myKey = new MyPair(25, "twenty five");
		final Map<Pair<Integer, String>, String> things = new HashMap<Pair<Integer, String>, String>();
		things.put(myKey, "blah");

		System.out.println(things);

		final MyPair myKey2 = new MyPair(25, "twenty five");
		final MyPair myKey3 = new MyPair(30, "thirty");
		things.put(myKey2, "bleh");
		things.put(myKey3, "meh");

		System.out.println(things);
	}
}
