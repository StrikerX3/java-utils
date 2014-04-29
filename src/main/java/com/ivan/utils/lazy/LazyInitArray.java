package com.ivan.utils.lazy;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Lazily initalized array of objects. The objects are initialized only once as
 * they are requested. This class is thread-safe.

 * @param <T> the object type
 */
public abstract class LazyInitArray<T> {
	/**
	 * The array of objects.
	 */
	private final T[] array;

	/**
	 * The object type.
	 */
	private final Class<T> type;

	/**
	 * Indicates if each element in the array is initialized.
	 */
	private final AtomicIntegerArray init;

	/**
	 * Creates a lazily initialized array of objects of the given type and size.
	 *
	 * @param type the object type
	 * @param size the number of elements in the array
	 */
	public LazyInitArray(final Class<T> type, final int size) {
		this.type = type;
		array = (T[]) Array.newInstance(type, size);
		init = new AtomicIntegerArray(size);
	}

	/**
	 * Retrieves a copy of the array, initializing all unitialized elements.
	 *
	 * @return a copy of the array with all elements initialized
	 */
	public T[] get() {
		final T[] copy = (T[]) Array.newInstance(type, array.length);
		for (int i = 0; i < copy.length; i++) {
			copy[i] = get(i);
		}
		return copy;
	}

	/**
	 * Retrieves the element at the given index, initializing it if necessary.
	 *
	 * @param index the element index
	 * @return the element
	 */
	public T get(final int index) {
		if (init.get(index) == 0) {
			synchronized (this) {
				if (init.get(index) == 0) {
					array[index] = initValue(index);
					init.set(index, 1);
				}
			}
		}
		return array[index];
	}

	/**
	 * Initializes the value at the given index.
	 *
	 * @param index the element index
	 * @return the initialized element
	 */
	protected abstract T initValue(int index);
}
