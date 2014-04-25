package com.ivan.utils.space.kdtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ivan.utils.space.Point;

public final class KdTree<T extends Point> {
    final int k;

    private KdNode root;

    private KdTree(final T[] points) {
        k = points[0].getDimensions();
        root = createNode(points, 0);
    }

    private KdNode createNode(final T[] points, final int depth) {
        if (points.length == 0) {
            return null;
        }

        final int axis = depth % k;
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(final Point o1, final Point o2) {
                final double a1 = o1.get(axis);
                final double a2 = o2.get(axis);
                return a1 > a2 ? 1 : a1 < a2 ? -1 : 0;
            }
        });

        final int medianIndex = points.length / 2;
        final KdNode node = new KdNode(depth, points[medianIndex]);
        if (medianIndex > 0) {
            final T[] less = Arrays.copyOfRange(points, 0, medianIndex);
            if (less.length > 0) {
                node.lesser = createNode(less, depth + 1);
                node.lesser.parent = node;
            }
        }
        if (medianIndex < points.length - 1) {
            final T[] more = Arrays.copyOfRange(points, medianIndex + 1, points.length);
            if (more.length > 0) {
                node.greater = createNode(more, depth + 1);
                node.greater.parent = node;
            }
        }

        return node;
    }

    public static <T extends Point> KdTree<T> build(final T... points) {
        return new KdTree<T>(points);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Point> KdTree<T> build(final Collection<T> points) {
        return build(points.toArray((T[]) new Object[points.size()]));
    }

    //////////////////////////////////////////////////////////

    public boolean add(final T value) {
        if (value == null) {
            return false;
        }

        if (root == null) {
            root = new KdNode(0, value);
            return true;
        }

        KdNode node = root;
        while (true) {
            if (node.comparePoint(value) <= 0) {
                if (node.lesser == null) {
                    final KdNode newNode = new KdNode(node, value);
                    node.lesser = newNode;
                    break;
                }
                node = node.lesser;
            } else {
                if (node.greater == null) {
                    final KdNode newNode = new KdNode(node, value);
                    node.greater = newNode;
                    break;
                }
                node = node.greater;
            }
        }

        return true;
    }

    public boolean contains(final T value) {
        if (value == null) {
            return false;
        }

        final KdNode node = getNode(value);
        return node != null;
    }

    public boolean remove(final T value) {
        if (value == null) {
            return false;
        }

        final KdNode node = getNode(value);
        if (node == null) {
            return false;
        }

        final KdNode parent = node.parent;
        if (parent != null) {
            if (parent.lesser != null && node.equals(parent.lesser)) {
                final T[] nodes = getTree(node);
                if (nodes.length > 0) {
                    parent.lesser = createNode(nodes, node.depth);
                    if (parent.lesser != null) {
                        parent.lesser.parent = parent;
                    }
                } else {
                    parent.lesser = null;
                }
            } else {
                final T[] nodes = getTree(node);
                if (nodes.length > 0) {
                    parent.greater = createNode(nodes, node.depth);
                    if (parent.greater != null) {
                        parent.greater.parent = parent;
                    }
                } else {
                    parent.greater = null;
                }
            }
        } else {
            final T[] nodes = getTree(node);
            if (nodes.length > 0) {
                root = createNode(nodes, node.depth);
            } else {
                root = null;
            }
        }

        return true;
    }

    public T findNearest(final T value) {
        return findNearest(value, 1).iterator().next();
    }

    public Collection<T> findNearest(final T value, final int numToFind) {
        if (value == null) {
            return null;
        }

        // Map used for results
        final TreeSet<KdNode> results = new TreeSet<KdNode>(new EuclideanComparator(value));

        // Find the closest leaf node
        KdNode prev = null;
        KdNode node = root;
        while (node != null) {
            if (node.comparePoint(value) < 0) {
                prev = node;
                node = node.greater;
            } else {
                prev = node;
                node = node.lesser;
            }
        }
        final KdNode leaf = prev;

        if (leaf != null) {
            // Used to not re-examine nodes
            final Set<KdNode> examined = new HashSet<KdNode>();

            // Go up the tree, looking for better solutions
            node = leaf;
            while (node != null) {
                // Search node
                searchNode(value, node, numToFind, results, examined);
                node = node.parent;
            }
        }

        // Load up the collection of the results
        final Collection<T> collection = new ArrayList<T>(numToFind);
        for (final KdNode kdNode : results) {
            collection.add(kdNode.point);
        }
        return collection;
    }

    private final void searchNode(final T value, final KdNode node, final int numToSearch, final TreeSet<KdNode> results, final Set<KdNode> examined) {
        examined.add(node);

        // Search node
        KdNode lastNode = null;
        double lastDistance = Double.MAX_VALUE;
        if (results.size() > 0) {
            lastNode = results.last();
            lastDistance = dist(lastNode.point, value);
        }
        final double nodeDistance = dist(node.point, value);
        if (Double.compare(nodeDistance, lastDistance) < 0) {
            if (results.size() == numToSearch && lastNode != null) {
                results.remove(lastNode);
            }
            results.add(node);
        } else if (Double.compare(nodeDistance, lastDistance) == 0) {
            results.add(node);
        } else if (results.size() < numToSearch) {
            results.add(node);
        }
        lastNode = results.last();
        lastDistance = dist(lastNode.point, value);

        final int axis = node.depth % k;
        final KdNode lesser = node.lesser;
        final KdNode greater = node.greater;

        // Search children branches, if axis aligned distance is less than current distance
        if (lesser != null && !examined.contains(lesser)) {
            examined.add(lesser);

            final double p1 = node.point.get(axis);
            final double p2 = value.get(axis) - lastDistance;
            final boolean intersects = p2 <= p1;

            // Continue down lesser branch
            if (intersects) {
                searchNode(value, lesser, numToSearch, results, examined);
            }
        }
        if (greater != null && !examined.contains(greater)) {
            examined.add(greater);

            final double p1 = node.point.get(axis);
            final double p2 = value.get(axis) + lastDistance;
            final boolean intersects = p2 >= p1;

            // Continue down greater branch
            if (intersects) {
                searchNode(value, greater, numToSearch, results, examined);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////

    private final KdNode getNode(final T value) {
        if (root == null || value == null) {
            return null;
        }

        KdNode node = root;
        while (true) {
            if (node.point.equals(value)) {
                return node;
            } else if (node.comparePoint(value) < 0) {
                if (node.greater == null) {
                    return null;
                }
                node = node.greater;
            } else {
                if (node.lesser == null) {
                    return null;
                }
                node = node.lesser;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private final T[] getTree(final KdNode root) {
        if (root == null) {
            return (T[]) new Object[0];
        }
        final List<T> list = getTreeList(root);
        return (T[]) list.toArray(new Object[list.size()]);
    }

    private final List<T> getTreeList(final KdNode root) {
        final List<T> list = new ArrayList<T>();

        if (root.lesser != null) {
            list.add(root.lesser.point);
            list.addAll(getTreeList(root.lesser));
        }
        if (root.greater != null) {
            list.add(root.greater.point);
            list.addAll(getTreeList(root.greater));
        }

        return list;
    }

    //////////////////////////////////////////////////////////
    double dist(final Point p1, final Point p2) {
        double d = 0.0;
        for (int i = 0; i < k; i++) {
            final double a1 = p1.get(i);
            final double a2 = p2.get(i);
            final double da = a2 - a1;
            d += da * da;
        }
        return d;
    }

    class KdNode {
        final int depth;

        final T point;

        KdNode lesser, greater;

        KdNode parent;

        public KdNode(final int depth, final T point) {
            this.depth = depth;
            this.point = point;
        }

        public KdNode(final KdNode parent, final T point) {
            this(parent.depth + 1, point);
        }

        public int comparePoint(final T that) {
            final int axis = depth % k;
            final double a1 = point.get(axis);
            final double a2 = that.get(axis);
            return a1 > a2 ? 1 : a1 < a2 ? -1 : 0;
        }
    }

    class EuclideanComparator implements Comparator<KdNode> {
        private Point point = null;

        public EuclideanComparator(final Point point) {
            this.point = point;
        }

        @Override
        public int compare(final KdNode o1, final KdNode o2) {
            final double d1 = dist(point, o1.point);
            final double d2 = dist(point, o2.point);
            return Double.compare(d1, d2);
        }
    }
}
