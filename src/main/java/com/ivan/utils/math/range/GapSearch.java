package com.ivan.utils.math.range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GapSearch {
    public static void main(final String[] args) {
        final List<Range> ranges = new ArrayList<Range>();
        ranges.add(new Range(99));
        System.out.println(ranges);

        final int min = 1;
        final int max = 99;
        System.out.println(min + ".." + max);
        final List<Range> gaps = findGaps(min, max, ranges);
        System.out.println(gaps);
    }

    public static void main4(final String[] args) {
        final List<Range> ranges = new ArrayList<Range>();
        ranges.add(new Range(1, 3));
        ranges.add(new Range(6, 11));
        ranges.add(new Range(13));
        ranges.add(new Range(20, 24));
        System.out.println(ranges);

        final int min = 1;
        final int max = 24;
        System.out.println(min + ".." + max);
        final List<Range> gaps = findGaps(min, max, ranges);
        System.out.println(gaps);
    }

    public static void main2(final String[] args) {
        final List<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            nums.add((int) (Math.random() * 20));
        }

        System.out.println("Numbers: " + nums);
        System.out.println("Ranges : " + createRangesFromList(nums));
    }

    public static void main3(final String[] args) {
        final List<Range> ranges = new ArrayList<Range>();
        ranges.add(new Range(2, 4));
        ranges.add(new Range(9));
        ranges.add(new Range(15));
        ranges.add(new Range(18, 26));
        ranges.add(new Range(101));
        ranges.add(new Range(69957, 75007));

        System.out.println("Values: " + ranges);

        for (int max = 1; max < 30; max++) {
            final int min = 1;
            // find the gaps between min and max left by the ranges above
            final List<Range> gaps = findGaps(min, max, ranges);
            System.out.println(min + ".." + max + ": " + gaps);
        }

        for (int min = 1; min < 30; min++) {
            final int max = 30;
            // find the gaps between min and max left by the ranges above
            final List<Range> gaps = findGaps(min, max, ranges);
            System.out.println(min + ".." + max + ": " + gaps);
        }
    }

    public static List<Range> createRangesFromList(final List<Integer> nums) {
        Collections.sort(nums);
        final List<Range> ranges = new ArrayList<Range>();
        if (!nums.isEmpty()) {
            int start;
            int last;
            start = nums.get(0);
            last = start;
            for (int i = 1; i < nums.size(); i++) {
                final int num = nums.get(i);
                if (num == last) {
                    // merge repeated numbers into a single range
                    continue;
                }
                if (num > last + 1) {
                    // numbers are not consecutive
                    ranges.add(new Range(start, last));
                    start = num;
                }
                last = num;
            }
            ranges.add(new Range(start, last));
        }
        return ranges;
    }

    /**
     * Find the gaps created by removing the given <code>ranges</code> from
     * the range <code>min</code>..<code>max</code>.
     * 
     * @param min the first number (inclusive)
     * @param max the last number (inclusive)
     * @param ranges the number ranges to be removed from the range min..max
     * @return a list containing the number ranges that represent the gaps
     */
    public static List<Range> findGaps(final int min, final int max, final List<Range> ranges) {
        // reorder and condense ranges
        Set<Range> vals = new TreeSet<Range>(ranges);
        vals = condense(vals);

        final List<Range> gaps = new ArrayList<Range>();
        int current = min - 1;
        for (final Iterator<Range> it = vals.iterator(); it.hasNext();) {
            final Range nextRange = it.next();

            // limit the search to the boundaries min..max
            if (nextRange.getMax() < min) {
                continue;
            }
            if (nextRange.getMin() > max) {
                break;
            }

            // check if there is a gap between the last and current ranges
            if (current + 1 <= nextRange.getMin() - 1) {
                // add the new gap
                gaps.add(new Range(current + 1, nextRange.getMin() - 1));
            }
            current = nextRange.getMax();
        }

        // add the last gap if needed
        if (current + 1 <= max) {
            gaps.add(new Range(current + 1, max));
        }
        return gaps;
    }

    /**
     * Condenses a list of Ranges, eliminating repetitions and merging
     * consecutive ranges.
     * 
     * @param ranges a list of ranges
     * @return a new list of condensed ranges
     */
    private static Set<Range> condense(final Set<Range> ranges) {
        final Set<Range> newVals = new TreeSet<Range>();

        Range merged = null;

        final Iterator<Range> it = ranges.iterator();
        if (it.hasNext()) {
            merged = new Range(it.next());
            while (it.hasNext()) {
                final Range range = it.next();
                if (merged.canMerge(range)) {
                    // connected ranges
                    merged = merged.merge(range);
                } else {
                    // separate ranges
                    newVals.add(new Range(merged));
                    merged = new Range(range);
                }
            }
            newVals.add(new Range(merged));
        }
        return newVals;
    }
}