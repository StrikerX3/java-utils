package com.ivan.utils.options;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores a set of {@link Option}s.
 */
public final class OptionSet {
    /**
     * The values for all options currently in this set.
     */
    private final Map<Option<?>, Object> values = new HashMap<Option<?>, Object>();

    /**
     * Returns the value if not null, or the default value for the option if the
     * value is <code>null</code>.
     *
     * @param option the option to retrieve the default value from
     * @param value the value
     * @return if <code>value</code> is not null, returns the value; otherwise,
     * returns the default value of the <code>option</code>
     */
    private static <T> T valueOrDefault(final Option<T> option, final T value) {
        return value == null ? option.getDefaultValue() : value;
    }

    /**
     * Sets the value of the given option.
     *
     * @param option the option
     * @param value the value to set
     * @return the old value of the option
     * @throws IllegalArgumentException is the option is required and a null
     * value was given
     */
    public <T> T set(final Option<T> option, final T value) {
        if (!option.isNullable() && value == null) {
            throw new IllegalArgumentException("option is not nullable");
        }
        return valueOrDefault(option, (T) values.put(option, value));
    }

    /**
     * Retrieves the value of the given option.
     *
     * @param option the option
     * @return the value of the option
     */
    public <T> T get(final Option<T> option) {
        return valueOrDefault(option, (T) values.get(option));
    }

    /**
     * Resets an option to its default value.
     *
     * @param option the option to reset
     * @return the old value of the option
     */
    public <T> T reset(final Option<T> option) {
        return (T) values.remove(option);
    }

    /**
     * Resets all options to their default values.
     */
    public void reset() {
        values.clear();
    }
}
