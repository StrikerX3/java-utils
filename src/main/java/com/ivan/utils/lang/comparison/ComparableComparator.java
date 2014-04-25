package com.ivan.utils.lang.comparison;

import java.util.Comparator;

public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }
}
