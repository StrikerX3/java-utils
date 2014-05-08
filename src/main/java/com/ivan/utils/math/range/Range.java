package com.ivan.utils.math.range;

/**
 * A range of numbers.
 */
public class Range implements Comparable<Range> {
    private final int min;

    private final int max;

    public Range(final int value) {
        this(value, value);
    }

    public Range(final int min, final int max) {
        super();
        this.min = min;
        this.max = max;
    }

    public Range(final Range range) {
        this(range.min, range.max);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean canMerge(final Range range) {
        return min < range.min ?
                range.min <= max + 1 || max >= range.min - 1 :
                min <= range.max + 1 || range.max >= min - 1;
    }

    public Range merge(final Range range) {
        if (!canMerge(range)) {
            return null;
        }
        return new Range(Math.min(min, range.min), Math.max(max, range.max));
    }

    @Override
    public String toString() {
        return min == max ? Integer.toString(min) : min + ".." + max;
    }

    @Override
    public int compareTo(final Range that) {
        return min == that.min ? max - that.max : min - that.min;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + max;
        result = prime * result + min;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Range other = (Range) obj;
        if (max != other.max) {
            return false;
        }
        if (min != other.min) {
            return false;
        }
        return true;
    }
}