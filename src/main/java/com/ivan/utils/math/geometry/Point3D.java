package com.ivan.utils.math.geometry;

import com.ivan.utils.space.Point;

/**
 * A point in a tridimensional space.
 */
public class Point3D implements Point {
    public double x, y, z;

    public Point3D() {
        this(0, 0, 0);
    }

    public Point3D(final Point3D point) {
        this(point.x, point.y, point.z);
    }

    public Point3D(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Point3D dup() {
        return new Point3D(this);
    }

    public Point3D add(final Vector3D vector) {
        return new Point3D(x + vector.x, y + vector.y, z + vector.z);
    }

    public Point3D subtract(final Vector3D vector) {
        return new Point3D(x - vector.x, y - vector.y, z - vector.z);
    }

    public Vector3D vectorTo(final Point3D other) {
        return new Vector3D(other.x - x, other.y - y, other.z - z);
    }

    public Point3D addLocal(final Vector3D offset) {
        x += offset.x;
        y += offset.y;
        z += offset.z;
        return this;
    }

    public Point3D subtractLocal(final Vector3D offset) {
        x -= offset.x;
        y -= offset.y;
        z -= offset.z;
        return this;
    }

    public Vector3D asVector() {
        return new Vector3D(x, y, z);
    }

    public Point3D projection(final Segment3D line) {
        return line.projection(this);
    }

    @Override
    public String toString() {
        return "Point3D[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public int getDimensions() {
        return 3;
    }

    @Override
    public double get(final int axis) {
        switch (axis) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IllegalArgumentException();
        }
    }
}