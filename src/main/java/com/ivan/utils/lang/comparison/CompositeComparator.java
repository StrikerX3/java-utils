package com.ivan.utils.lang.comparison;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class CompositeComparator<T extends Comparable<T>> implements Comparator<T> {
    private final Comparator<T>[] comparators;

    public CompositeComparator(final Comparator<T>... comparators) {
        this.comparators = comparators.clone();
    }

    public CompositeComparator(final Collection<Comparator<T>> comparators) {
        this.comparators = comparators.toArray(new Comparator[comparators.size()]);
    }

    public static <T extends Comparable<T>> CompositeComparatorBuilder<T> newBuilder() {
        return new CompositeComparatorBuilder<T>();
    }

    @Override
    public int compare(final T o1, final T o2) {
        for (final Comparator<T> comparator : comparators) {
            final int cmp = comparator.compare(o1, o2);
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    public static class CompositeComparatorBuilder<T extends Comparable<T>> {
        private final List<Comparator<T>> comparators = new ArrayList<Comparator<T>>();

        CompositeComparatorBuilder() {
        }

        public CompositeComparatorBuilder<T> add(final Comparator<T> comparator) {
            comparators.add(comparator);
            return this;
        }

        public CompositeComparator<T> build() {
            return new CompositeComparator<T>(comparators);
        }
    }
}
