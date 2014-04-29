package com.ivan.utils.lazy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Lazily initialized object that will be created only if needed. This class is
 * thread-safe.
 *
 * @param <T> the type of the object to lazily initialize
 */
public abstract class LazyInit<T> {
	/**
	 * The value, which will be initialized on demand.
	 */
	private T value;

	/**
	 * Determines whether this value was initialized.
	 */
	private final AtomicBoolean init = new AtomicBoolean();

	/**
	 * Retrieves the value, initializing it if necessary.
	 * 
	 * @return the value
	 */
	public T get() {
		if (!init.getAndSet(true)) {
			value = initValue();
		}
		return value;
	}

	/**
	 * Initializes this value. This method will be invoked exactly once, when
	 * {@link #get()} is invoked for the first time. Subsequent invocations of
	 * <code>get</code> will not invoke this method.
	 * 
	 * @return the initialized value
	 */
	protected abstract T initValue();
}
