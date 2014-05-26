package com.ivan.utils.collections.list;

/**
 * An inline linked list node. Used as follows:
 * <pre>
 * // Create a linked list for names
 * ListNode&lt;String&gt; names = ListNode.root();
 * 
 * // Add elements to the end
 * ListNode&lt;String&gt; lastNode = names.add("John");
 * lastNode = lastNode.add("Jane");
 * 
 * // Add elements to the start
 * names.add("Jack");
 * names.add("Jill");
 * 
 * // Iterate over elements
 * ListNode<String> it = names;
 * while (it.hasNext()) {
 *     it = it.next();
 *     System.out.println(it.get());
 * }
 * 
 * // Insert elements after an specific node
 * names.next().next().add("Larry");
 * ListNode<String> myNode = names.next().next().next();
 * myNode.add("Moe");
 * 
 * // Remove elements
 * names.next().remove();
 * 
 * // Note that you cannot remove the root node
 * try {
 *     names.remove();
 * } catch (final UnsupportedOperationException e) {
 *     System.out.println("Root node cannot be removed");
 * }
 * </pre>
 *
 * @param <T> the element type
 */
public class ListNode<T> {
    private ListNode<T> prev;
    private ListNode<T> next;
    private T value;

    private ListNode(final T e) {
        this.value = e;
    }

    ////////////////
    // Navigation

    public boolean hasNext() {
        return next != null;
    }

    public ListNode<T> next() {
        return next;
    }

    public boolean hasPrevious() {
        return prev != null;
    }

    public ListNode<T> previous() {
        return prev;
    }

    ////////////////
    // Modification

    public ListNode<T> add(final T e) {
        final ListNode<T> node = new ListNode<T>(e);
        node.prev = this;
        node.next = next;
        if (hasNext()) {
            next.prev = node;
        }
        this.next = node;
        return node;
    }

    public ListNode<T> remove() {
        final ListNode<T> node = next;
        if (hasPrevious()) {
            prev.next = next;
        }
        if (hasNext()) {
            next.prev = prev;
        }
        prev = next = null;
        return node;
    }

    public T get() {
        return value;
    }

    public void set(final T e) {
        value = e;
    }

    ////////////////
    // Root node

    public static <T> ListNode<T> root() {
        return new RootNode<T>();
    }

    private static final class RootNode<T> extends ListNode<T> {
        private RootNode() {
            super(null);
        }

        @Override
        public T get() {
            throw new UnsupportedOperationException("root node has no value");
        }

        @Override
        public void set(final T e) {
            throw new UnsupportedOperationException("root node has no value");
        }

        @Override
        public ListNode<T> remove() {
            throw new UnsupportedOperationException("cannot remove the root node");
        }
    }
}
