package com.ivan.utils.collections.iterator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompositeIteratorTest {
    public static void main(final String[] args) {
        final List<? extends Number> numbers = Arrays.asList(1D, 2, 3f, 4L, (short) 5, (byte) 6);
        final List<Integer> integers = Arrays.asList(7, 8, 9);
        final List<Number> empty = Collections.emptyList();

        final CompositeIterator<Number> it = new CompositeIterator<Number>(empty, numbers, empty, integers, empty);
        while (it.hasNext()) {
            final Number number = it.next();
            System.out.println("got a " + number);
        }
    }
}
