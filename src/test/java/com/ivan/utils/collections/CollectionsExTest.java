package com.ivan.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ivan.utils.math.bits.BitMath;

// this is obsolete with Java 8
public class CollectionsExTest {
	public static void main(final String[] args) {
		final List<Integer> nums = Arrays.asList(1, 2, 4, 5, 7, 8, 9, 10, 15, 16);
		final Predicate<Integer> powersOfTwo = new Predicate<Integer>() {
			@Override
			public boolean evaluate(final Integer num) {
				return BitMath.bitCount(num) == 1;
			}
		};
		final Predicate<Integer> evenNumbers = new Predicate<Integer>() {
			@Override
			public boolean evaluate(final Integer num) {
				return (num & 1) == 0;
			}
		};

		final Concatenator<Integer> sum = new Concatenator<Integer>() {
			private int sum = 0;

			@Override
			public void concatenate(final Integer num) {
				sum += num;
			}

			@Override
			public String toString() {
				return Integer.toString(sum);
			}
		};
		final Concatenator<Integer> product = new Concatenator<Integer>() {
			private int prod;
			private boolean firstNum = true;

			@Override
			public void concatenate(final Integer num) {
				if (firstNum) {
					firstNum = false;
					prod = num;
				} else {
					prod *= num;
				}
			}

			@Override
			public String toString() {
				return Integer.toString(prod);
			}
		};

		final Transformer<Integer> addOne = new Transformer<Integer>() {
			@Override
			public Integer transform(final Integer num) {
				return num + 1;
			}
		};
		final Transformer<Integer> dbl = new Transformer<Integer>() {
			@Override
			public Integer transform(final Integer num) {
				return num * 2;
			}
		};

		System.out.println("Numbers: " + nums);

		System.out.println();
		testKeep("powers of two", nums, powersOfTwo);
		testKeep("even numbers", nums, evenNumbers);

		System.out.println();
		testRemove("powers of two", nums, powersOfTwo);
		testRemove("even numbers", nums, evenNumbers);

		System.out.println();
		testConcat("sum", nums, sum);
		testConcat("product", nums, product);

		System.out.println();
		testTransform("+1", nums, addOne);
		testTransform("*2", nums, dbl);
	}

	private static void testKeep(final String name, final List<Integer> nums, final Predicate<Integer> predicate) {
		final List<Integer> copy = new ArrayList<Integer>(nums);
		CollectionsEx.keep(copy, predicate);
		System.out.println("Kept " + name + ": " + copy);
	}

	private static void testRemove(final String name, final List<Integer> nums, final Predicate<Integer> predicate) {
		final List<Integer> copy = new ArrayList<Integer>(nums);
		CollectionsEx.remove(copy, predicate);
		System.out.println("Removed " + name + ": " + copy);
	}

	private static void testConcat(final String name, final List<Integer> nums, final Concatenator<Integer> concat) {
		CollectionsEx.concatenate(nums, concat);
		System.out.println("Concatenated " + name + ": " + concat);
	}

	private static void testTransform(final String name, final List<Integer> nums, final Transformer<Integer> xform) {
		CollectionsEx.transform(nums, xform);
		System.out.println("Transformed " + name + ": " + nums);
	}
}
