package com.ivan.utils.collections;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Complements <code>java.util.Arrays</code> with additional operations on
 * arrays.
 */
public final class ArraysEx {
	private ArraysEx() {
	}

	////////////////////////////////////////////////////////////////////////////

	public static <T> void concatenate(final T[] array, final Concatenator<T> concatenator) {
		for (final T obj : array) {
			concatenator.concatenate(obj);
		}
	}

	public static <T> T[] keep(final T[] array, final Predicate<T> predicate) {
		return keepOrRemove(array, predicate, false);
	}

	public static <T> T[] remove(final T[] array, final Predicate<T> predicate) {
		return keepOrRemove(array, predicate, true);
	}

	private static <T> T[] keepOrRemove(final T[] array, final Predicate<T> predicate, final boolean remove) {
		final T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
		int j = 0;
		for (int i = 0; i < array.length; i++) {
			if (predicate.evaluate(array[i]) != remove) {
				newArray[j++] = array[i];
			}
		}
		return Arrays.copyOf(newArray, j);
	}

	public static <T> void transform(final T[] array, final Transformer<T> transformer) {
		for (int i = 0; i < array.length; i++) {
			array[i] = transformer.transform(array[i]);
		}
	}
}
