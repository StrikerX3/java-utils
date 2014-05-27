package com.ivan.utils.collections.list;

import java.util.LinkedList;
import java.util.List;

import com.ivan.utils.time.Stopwatch;

public class ListNodeBenchmark {
    public static void main(final String[] args) {
        final ListNode<Integer> nodes = ListNode.create();
        final List<Integer> list = new LinkedList<Integer>();

        final Stopwatch sw = new Stopwatch();

        final int count = 2000000;

        sw.restart();
        for (int i = 0; i < count; i++) {
            nodes.add(i);
        }
        System.out.println("node add to start: " + sw.timeElapsed() + " ms");
        System.gc();

        sw.restart();
        for (int i = 0; i < count; i++) {
            list.add(0, i);
        }
        System.out.println("list add to start: " + sw.timeElapsed() + " ms");
        System.gc();

        ////////////////////////

        sw.restart();
        ListNode<Integer> node = nodes.last();
        for (int i = 0; i < count; i++) {
            node = node.add(count - i);
        }
        System.out.println("node add to end: " + sw.timeElapsed() + " ms");
        System.gc();

        sw.restart();
        for (int i = 0; i < count; i++) {
            list.add(0, count - i);
        }
        System.out.println("list add to end: " + sw.timeElapsed() + " ms");
        System.gc();

        ////////////////////////

        sw.restart();
        ListNode<Integer> it = nodes;
        long sum = 0;
        while (it.hasNext()) {
            it = it.next();
            sum += it.get();
        }
        System.out.println("node iterate: " + sw.timeElapsed() + " ms    result = " + sum);
        System.gc();

        sw.restart();
        sum = 0;
        for (final Integer i : list) {
            sum += i;
        }
        System.out.println("list iterate: " + sw.timeElapsed() + " ms    result = " + sum);
        System.gc();

        ////////////////////////
    }
}
