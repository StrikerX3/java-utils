package com.ivan.utils.collections.list;

public class ListNodeTest {
    public static void main(final String[] args) {
        // Create a linked list for names
        final ListNode<String> names = ListNode.root();

        // Add elements to the end
        ListNode<String> lastNode = names.add("Curly");
        lastNode = lastNode.add("Sally");

        // Add elements to the start
        names.add("Robert");
        names.add("Diana");

        // Iterate over elements
        ListNode<String> it = names;
        while (it.hasNext()) {
            it = it.next();
            System.out.println(it.get());
        }

        // Insert elements after an specific node
        names.next().next().add("Larry");
        final ListNode<String> myNode = names.next().next().next();
        myNode.add("Moe");

        // Remove elements
        names.next().remove();

        // Note that you cannot remove the root node
        try {
            names.remove();
        } catch (final UnsupportedOperationException e) {
            System.out.println("!! root node cannot be removed");
        }

        // List names again
        it = names;
        System.out.println("---");
        while (it.hasNext()) {
            it = it.next();
            System.out.println(it.get());
        }
    }
}
