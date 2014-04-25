package com.ivan.utils.space.quadtree;

import java.util.Collection;

import com.ivan.utils.collections.ArraysEx;
import com.ivan.utils.collections.Predicate;
import com.ivan.utils.space.Point2D;

public final class Quadtree<T extends Point2D> {
    private boolean leaf;

    private T value;

    // bounding box
    private double minX, maxX, cX;

    private double minY, maxY, cY;

    //    negative  positive
    // X  (l)eft    (r)ight
    // Y  (b)ottom  (t)op
    private Quadtree<T> lt, rt, lb, rb;

    private Quadtree() {
    }

    private Quadtree(final T[] values) {
        if (values.length == 1) {
            leaf = true;
            value = values[0];
        } else {
            leaf = false;

            // determine bounding box
            minX = maxX = values[0].getX();
            minY = maxY = values[0].getY();

            for (int i = 1; i < values.length; i++) {
                minX = Math.min(minX, values[i].getX());
                minY = Math.min(minY, values[i].getY());

                maxX = Math.max(maxX, values[i].getX());
                maxY = Math.max(maxY, values[i].getY());
            }

            // calculate center of box
            cX = (maxX + minX) * 0.5;
            cY = (maxY + minY) * 0.5;

            // subdivide
            lb = createChild(minX, cX, false, minY, cY, false, values);
            rb = createChild(cX, maxX, true, minY, cY, false, values);

            lt = createChild(minX, cX, false, minY, cY, false, values);
            rt = createChild(cX, maxX, true, minY, cY, false, values);
        }
    }

    private static <T extends Point2D> Quadtree<T> createChild(
            final double minX, final double maxX, final boolean includeMaxX,
            final double minY, final double maxY, final boolean includeMaxY,
            final T[] values) {

        // find out which values are within the range
        final T[] valuesWithinBox = ArraysEx.keep(values, new Predicate<T>() {
            @Override
            public boolean evaluate(final T node) {
                final double x = node.getX();
                final double y = node.getY();

                final boolean withinX = x >= minX && (includeMaxX ? x <= maxX : x < maxX);
                final boolean withinY = y >= minY && (includeMaxY ? y <= maxY : y < maxY);

                return withinX && withinY;
            }
        });

        if (valuesWithinBox.length == 0) {
            return null;
        }
        return new Quadtree<T>(valuesWithinBox);
    }

    public static <T extends Point2D> Quadtree<T> build(final T... values) {
        if (values.length == 0) {
            return null;
        }
        return new Quadtree<T>(values);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Point2D> Quadtree<T> build(final Collection<T> values) {
        return build((T[]) values.toArray(new Object[values.size()]));
    }

    ////////////////////////////////////////////////////////////////////////////

    public T findNearest(final T value) {
        if (leaf) {
            return this.value;
        }

        // find out in which quadrant the value is in
        final double x = value.getX();
        final double y = value.getY();

        /*System.out.println();
        System.out.println(x + "x" + y + "  " + cX + "x" + cY);
        System.out.println("going "
                + (x <= cX ? "LEFT_" : "RIGHT_")
                + (y <= cY ? "BOTTOM" : "TOP"));*/
        final Quadtree<T> child =
                x <= cX
                        ? y <= cY ? lb : lt
                        : y <= cY ? rb : rt;
        if (child == null) {
            return null;
        }
        return child.findNearest(value);
    }

    public void visit(final QuadtreeVisitor<T> visitor) {
        visit(visitor, 0, QuadtreeNodePosition.ROOT);
    }

    private void visit(final QuadtreeVisitor<T> visitor, final int depth, final QuadtreeNodePosition pos) {
        if (leaf) {
            visitor.visitLeaf(value, depth, pos);
        } else {
            visitor.visitNode(depth, pos);
        }

        if (lt != null) {
            lt.visit(visitor, depth + 1, QuadtreeNodePosition.LEFT_TOP);
        }
        if (rt != null) {
            rt.visit(visitor, depth + 1, QuadtreeNodePosition.RIGHT_TOP);
        }
        if (lb != null) {
            lb.visit(visitor, depth + 1, QuadtreeNodePosition.LEFT_BOTTOM);
        }
        if (rb != null) {
            rb.visit(visitor, depth + 1, QuadtreeNodePosition.RIGHT_BOTTOM);
        }

        if (leaf) {
            visitor.leaveLeaf(value, depth, pos);
        } else {
            visitor.leaveNode(depth, pos);
        }
    }
}
