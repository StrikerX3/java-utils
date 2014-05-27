package com.ivan.utils.collections.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositeIterator<T> implements Iterator<T> {
    private final Iterable<? extends T>[] iterables;
    private Iterator<? extends T> currIterator;
    private int index;
    private T next;
    private boolean hasNext;

    private static final Iterator<?> NULL_ITERATOR = new Iterator<Object>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new NoSuchElementException();
        }
    };

    public CompositeIterator(final Iterable<? extends T>... iterables) {
        this.iterables = iterables;
        index = 0;
        hasNext = true;
        if (iterables.length > 0) {
            currIterator = iterables[index].iterator();
        } else {
            currIterator = (Iterator<T>) NULL_ITERATOR;
        }
        findNext();
    }

    private void findNext() {
        while (!currIterator.hasNext()) {
            index++;
            if (index == iterables.length) {
                hasNext = false;
                return;
            }
            currIterator = iterables[index].iterator();
        }
        next = currIterator.next();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        final T result = next;
        findNext();
        return result;
    }

    @Override
    public void remove() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        currIterator.remove();
        findNext();
    }
}
