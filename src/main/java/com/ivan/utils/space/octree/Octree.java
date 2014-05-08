package com.ivan.utils.space.octree;

import java.util.Collection;

import com.ivan.utils.collections.ArraysEx;
import com.ivan.utils.collections.Predicate;
import com.ivan.utils.math.geometry.Point3D;

public final class Octree<T extends Point3D> {
    private boolean leaf;

    private T value;

    // bounding box
    private double minX, maxX, cX;

    private double minY, maxY, cY;

    private double minZ, maxZ, cZ;

    //    negative  positive
    // X  (l)eft    (r)ight
    // Y  (f)ront   (b)ack
    // Z  (b)ottom  (t)op
    private Octree<T> lft, lbt, rft, rbt;

    private Octree<T> lfb, lbb, rfb, rbb;

    private Octree() {
    }

    private Octree(final T[] values) {
        if (values.length == 1) {
            leaf = true;
            value = values[0];
        } else {
            leaf = false;

            // determine bounding box
            minX = maxX = values[0].x;
            minY = maxY = values[0].y;
            minZ = maxZ = values[0].z;

            for (int i = 1; i < values.length; i++) {
                minX = Math.min(minX, values[i].x);
                minY = Math.min(minY, values[i].y);
                minZ = Math.min(minZ, values[i].z);

                maxX = Math.max(maxX, values[i].x);
                maxY = Math.max(maxY, values[i].y);
                maxZ = Math.max(maxZ, values[i].z);
            }

            // calculate center of cube
            cX = (maxX + minX) * 0.5;
            cY = (maxY + minY) * 0.5;
            cZ = (maxZ + minZ) * 0.5;

            // subdivide
            lfb = createChild(minX, cX, false, minY, cY, false, minZ, cZ, false, values);
            lbb = createChild(minX, cX, false, cY, maxY, true, minZ, cZ, false, values);
            rfb = createChild(cX, maxX, true, minY, cY, false, minZ, cZ, false, values);
            rbb = createChild(cX, maxX, true, cY, maxY, true, minZ, cZ, false, values);

            lft = createChild(minX, cX, false, minY, cY, false, cZ, maxZ, true, values);
            lbt = createChild(minX, cX, false, cY, maxY, true, cZ, maxZ, true, values);
            rft = createChild(cX, maxX, true, minY, cY, false, cZ, maxZ, true, values);
            rbt = createChild(cX, maxX, true, cY, maxY, true, cZ, maxZ, true, values);
        }
    }

    private static <T extends Point3D> Octree<T> createChild(
            final double minX, final double maxX, final boolean includeMaxX,
            final double minY, final double maxY, final boolean includeMaxY,
            final double minZ, final double maxZ, final boolean includeMaxZ,
            final T[] values) {

        // find out which values are within the range
        final T[] valuesWithinBox = ArraysEx.keep(values, new Predicate<T>() {
            @Override
            public boolean evaluate(final T node) {
                final double x = node.x;
                final double y = node.y;
                final double z = node.z;

                final boolean withinX = x >= minX && (includeMaxX ? x <= maxX : x < maxX);
                final boolean withinY = y >= minY && (includeMaxY ? y <= maxY : y < maxY);
                final boolean withinZ = z >= minZ && (includeMaxZ ? z <= maxZ : z < maxZ);

                return withinX && withinY && withinZ;
            }
        });

        if (valuesWithinBox.length == 0) {
            return null;
        }
        return new Octree<T>(valuesWithinBox);
    }

    public static <T extends Point3D> Octree<T> build(final T... values) {
        if (values.length == 0) {
            return null;
        }
        return new Octree<T>(values);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Point3D> Octree<T> build(final Collection<T> values) {
        return build((T[]) values.toArray(new Object[values.size()]));
    }

    ////////////////////////////////////////////////////////////////////////////

    public T findNearest(final T value) {
        if (leaf) {
            return this.value;
        }

        // find out in which octant the value is in
        final double x = value.x;
        final double y = value.y;
        final double z = value.z;

        /*System.out.println();
        System.out.println(x + "x" + y + "x" + z + "  " + cX + "x" + cY + "x" + cZ);
        System.out.println("going "
                + (x <= cX ? "LEFT_" : "RIGHT_")
                + (y <= cY ? "FRONT_" : "BACK_")
                + (z <= cZ ? "BOTTOM" : "TOP"));*/
        final Octree<T> child =
                x <= cX
                        ? y <= cY
                                ? z <= cZ ? lfb : lft
                                : z <= cZ ? lbb : lbt
                        : y <= cY
                                ? z <= cZ ? rfb : rft
                                : z <= cZ ? rbb : rbt;
        if (child == null) {
            return null;
        }
        return child.findNearest(value);
    }

    public void visit(final OctreeVisitor<T> visitor) {
        visit(visitor, 0, OctreeNodePosition.ROOT);
    }

    private void visit(final OctreeVisitor<T> visitor, final int depth, final OctreeNodePosition pos) {
        if (leaf) {
            visitor.visitLeaf(value, depth, pos);
        } else {
            visitor.visitNode(depth, pos);
        }

        if (lft != null) {
            lft.visit(visitor, depth + 1, OctreeNodePosition.LEFT_FRONT_TOP);
        }
        if (rft != null) {
            rft.visit(visitor, depth + 1, OctreeNodePosition.RIGHT_FRONT_TOP);
        }
        if (lbt != null) {
            lbt.visit(visitor, depth + 1, OctreeNodePosition.LEFT_BACK_TOP);
        }
        if (rbt != null) {
            rbt.visit(visitor, depth + 1, OctreeNodePosition.RIGHT_BACK_TOP);
        }
        if (lfb != null) {
            lfb.visit(visitor, depth + 1, OctreeNodePosition.LEFT_FRONT_BOTTOM);
        }
        if (rfb != null) {
            rfb.visit(visitor, depth + 1, OctreeNodePosition.RIGHT_FRONT_BOTTOM);
        }
        if (lbb != null) {
            lbb.visit(visitor, depth + 1, OctreeNodePosition.LEFT_BACK_BOTTOM);
        }
        if (rbb != null) {
            rbb.visit(visitor, depth + 1, OctreeNodePosition.RIGHT_BACK_BOTTOM);
        }

        if (leaf) {
            visitor.leaveLeaf(value, depth, pos);
        } else {
            visitor.leaveNode(depth, pos);
        }
    }
}
