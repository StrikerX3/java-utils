package com.ivan.utils.options;

/**
 * Represents an option of the specified type. Options can be stored in an
 * {@link OptionSet}. Options can be created with the {@link #createOptional()},
 * {@link #createOptional(Object)} and {@link #createRequired(Object)} methods.
 *
 * @param <T> the type of the option parameter
 */
public final class Option<T> {
	/**
	 * The default value of this option
	 */
	private final T defaultValue;

	/**
	 * <code>true</code> if this option is nullable (optional),
	 * <code>false</code> otherwise.
	 */
	private final boolean nullable;

	/**
	 * Creates a new option with the specified default value
	 * 
	 * @param defaultValue the default value
	 * @throws IllegalArgumentException if the option is not nullable and the
	 * default value provided is <code>null</code>
	 */
	private Option(final T defaultValue, final boolean nullable) {
		if (!nullable & defaultValue == null) {
			throw new IllegalArgumentException("Nullable option cannot have a null default value");
		}
		this.defaultValue = defaultValue;
		this.nullable = nullable;
	}

	/**
	 * Creates a new nullable option with a <code>null</code> default value.
	 */
	public static <T> Option<T> createOptional() {
		return createOptional(null);
	}

	/**
	 * Creates a new nullable option with the specified default value.
	 * 
	 * @param defaultValue the default value
	 */
	public static <T> Option<T> createOptional(final T defaultValue) {
		return new Option<T>(defaultValue, true);
	}

	/**
	 * Creates a new non-nullable option with the specified default value.
	 * 
	 * @param defaultValue the default value
	 * @throws IllegalArgumentException if the provided default value is
	 * <code>null</code>
	 */
	public static <T> Option<T> createRequired(final T defaultValue) {
		return new Option<T>(defaultValue, false);
	}

	/**
	 * Returns the default value for this option.
	 * 
	 * @return the default value for this option
	 */
	public T getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Determines whether this option is required (<code>false</code>) or
	 * optional (<code>true</code>).
	 * 
	 * @return <code>true</code> if the option is nullable, <code>false</code>
	 * otherwise
	 */
	public boolean isNullable() {
		return nullable;
	}

	@Override
	public String toString() {
		return "Option[defaultValue=" + defaultValue + "]";
	}
}
