package com.ivan.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Complements <code>java.util.Collections</code> with additional operations on
 * collections. Most of this class is obsolete in Java 8 due to the new stream()
 * and lambda capabilities of that version.
 */
public final class CollectionsEx {
	private CollectionsEx() {
	}

	public static <T> void concatenate(final Iterable<T> iterable, final Concatenator<T> concatenator) {
		for (final T obj : iterable) {
			concatenator.concatenate(obj);
		}
	}

	public static <T> void keep(final Iterable<T> iterable, final Predicate<T> predicate) {
		keepOrRemove(iterable, predicate, false);
	}

	public static <T> void remove(final Iterable<T> iterable, final Predicate<T> predicate) {
		keepOrRemove(iterable, predicate, true);
	}

	private static <T> void keepOrRemove(final Iterable<T> iterable, final Predicate<T> predicate, final boolean remove) {
		for (final Iterator<T> it = iterable.iterator(); it.hasNext();) {
			final T obj = it.next();
			if (predicate.evaluate(obj) == remove) {
				it.remove();
			}
		}
	}

	public static <T> void transform(final Collection<T> collection, final Transformer<T> transformer) {
		final List<T> modifiedObjs = new LinkedList<T>();
		for (final T obj : collection) {
			modifiedObjs.add(transformer.transform(obj));
		}
		collection.clear();
		collection.addAll(modifiedObjs);
	}

	public static <T> void transform(final List<T> list, final Transformer<T> transformer) {
		for (final ListIterator<T> it = list.listIterator(); it.hasNext();) {
			final T obj = it.next();
			it.set(transformer.transform(obj));
		}
	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts a list of <code>Boolean</code> objects into an array of
	 * primitive <code>boolean</code>s.
	 *
	 * @param values the list of <code>Boolean</code>s
	 * @return an array of <code>boolean</code>s
	 */
	public static boolean[] toBooleanArray(final List<Boolean> values) {
		final boolean[] items = new boolean[values.size()];
		final Iterator<Boolean> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Byte</code> objects into an array of
	 * primitive <code>byte</code>s.
	 *
	 * @param values the list of <code>Byte</code>s
	 * @return an array of <code>byte</code>s
	 */
	public static byte[] toByteArray(final List<Byte> values) {
		final byte[] items = new byte[values.size()];
		final Iterator<Byte> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Character</code> objects into an array of
	 * primitive <code>char</code>s.
	 *
	 * @param values the list of <code>Character</code>s
	 * @return an array of <code>char</code>s
	 */
	public static char[] toCharArray(final List<Character> values) {
		final char[] items = new char[values.size()];
		final Iterator<Character> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Short</code> objects into an array of
	 * primitive <code>int</code>s.
	 *
	 * @param values the list of <code>Short</code>s
	 * @return an array of <code>short</code>s
	 */
	public static short[] toShortArray(final List<Short> values) {
		final short[] items = new short[values.size()];
		final Iterator<Short> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Integer</code> objects into an array of
	 * primitive <code>int</code>s.
	 *
	 * @param values the list of <code>Integer</code>s
	 * @return an array of <code>int</code>s
	 */
	public static int[] toIntArray(final List<Integer> values) {
		final int[] items = new int[values.size()];
		final Iterator<Integer> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Long</code> objects into an array of
	 * primitive <code>long</code>s.
	 *
	 * @param values the list of <code>Long</code>s
	 * @return an array of <code>long</code>s
	 */
	public static long[] toLongArray(final List<Long> values) {
		final long[] items = new long[values.size()];
		final Iterator<Long> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Float</code> objects into an array of
	 * primitive <code>float</code>s.
	 *
	 * @param values the list of <code>Float</code>s
	 * @return an array of <code>float</code>s
	 */
	public static float[] toFloatArray(final List<Float> values) {
		final float[] items = new float[values.size()];
		final Iterator<Float> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	/**
	 * Converts a list of <code>Double</code> objects into an array of
	 * primitive <code>double</code>s.
	 *
	 * @param values the list of <code>Double</code>s
	 * @return an array of <code>double</code>s
	 */
	public static double[] toDoubleArray(final List<Double> values) {
		final double[] items = new double[values.size()];
		final Iterator<Double> iterator = values.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = iterator.next();
		}
		return items;
	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts an array of <code>boolean</code> values into an
	 * <code>Iterable</code> of <code>Boolean</code> objects.
	 *
	 * @param values the array of <code>boolean</code>s
	 * @return an <code>Iterable</code> of <code>Boolean</code>s
	 */
	public static Iterable<Boolean> toIterable(final boolean[] values) {
		return new Iterable<Boolean>() {
			@Override
			public Iterator<Boolean> iterator() {
				return new Iterator<Boolean>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Boolean next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>byte</code> values into an
	 * <code>Iterable</code> of <code>Byte</code> objects.
	 *
	 * @param values the array of <code>byte</code>s
	 * @return an <code>Iterable</code> of <code>Byte</code>s
	 */
	public static Iterable<Byte> toIterable(final byte[] values) {
		return new Iterable<Byte>() {
			@Override
			public Iterator<Byte> iterator() {
				return new Iterator<Byte>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Byte next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>char</code> values into an
	 * <code>Iterable</code> of <code>Character</code> objects.
	 *
	 * @param values the array of <code>char</code>s
	 * @return an <code>Iterable</code> of <code>Character</code>s
	 */
	public static Iterable<Character> toIterable(final char[] values) {
		return new Iterable<Character>() {
			@Override
			public Iterator<Character> iterator() {
				return new Iterator<Character>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Character next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>short</code> values into an
	 * <code>Iterable</code> of <code>Short</code> objects.
	 *
	 * @param values the array of <code>short</code>s
	 * @return an <code>Iterable</code> of <code>Short</code>s
	 */
	public static Iterable<Short> toIterable(final short[] values) {
		return new Iterable<Short>() {
			@Override
			public Iterator<Short> iterator() {
				return new Iterator<Short>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Short next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>int</code> values into an
	 * <code>Iterable</code> of <code>Integer</code> objects.
	 *
	 * @param values the array of <code>int</code>s
	 * @return an <code>Iterable</code> of <code>Integer</code>s
	 */
	public static Iterable<Integer> toIterable(final int[] values) {
		return new Iterable<Integer>() {
			@Override
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Integer next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>long</code> values into an
	 * <code>Iterable</code> of <code>Long</code> objects.
	 *
	 * @param values the array of <code>long</code>s
	 * @return an <code>Iterable</code> of <code>Long</code>s
	 */
	public static Iterable<Long> toIterable(final long[] values) {
		return new Iterable<Long>() {
			@Override
			public Iterator<Long> iterator() {
				return new Iterator<Long>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Long next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>float</code> values into an
	 * <code>Iterable</code> of <code>Float</code> objects.
	 *
	 * @param values the array of <code>float</code>s
	 * @return an <code>Iterable</code> of <code>Float</code>s
	 */
	public static Iterable<Float> toIterable(final float[] values) {
		return new Iterable<Float>() {
			@Override
			public Iterator<Float> iterator() {
				return new Iterator<Float>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Float next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	/**
	 * Converts an array of <code>double</code> values into an
	 * <code>Iterable</code> of <code>Double</code> objects.
	 *
	 * @param values the array of <code>double</code>s
	 * @return an <code>Iterable</code> of <code>Double</code>s
	 */
	public static Iterable<Double> toIterable(final double[] values) {
		return new Iterable<Double>() {
			@Override
			public Iterator<Double> iterator() {
				return new Iterator<Double>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return pos < values.length;
					}

					@Override
					public Double next() {
						return values[pos++];
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("cannot remove an element from an array");
					}
				};
			}
		};
	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts an <code>Iterable</code> into a <code>Collection</code>. This
	 * method returns an <code>ArrayList</code> containing all elements returned
	 * by the iterator generated by <code>iterable</code> in the order they are
	 * returned.
	 *
	 * @param iterable the iterable to convert
	 * @return a <code>Collection</code> containing all elements of the iterable
	 */
	public static <E> Collection<E> toCollection(final Iterable<E> iterable) {
		final Collection<E> col = new ArrayList<E>();
		final Iterator<E> it = iterable.iterator();
		while (it.hasNext()) {
			final E item = it.next();
			col.add(item);
		}
		return col;
	}
}
