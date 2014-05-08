package com.ivan.utils.math.geom;

/**
 * A point in a bidimensional space.
 */
public class Point2D {
    public double x, y;

    public Point2D() {
        this(0, 0);
    }

    public Point2D(final Point2D point) {
        this(point.x, point.y);
    }

    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D set(final double x, final double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point2D dup() {
        return new Point2D(this);
    }

    public Point2D add(final Vector2D vector) {
        return new Point2D(x + vector.x, y + vector.y);
    }

    public Point2D subtract(final Vector2D vector) {
        return new Point2D(x - vector.x, y - vector.y);
    }

    public Vector2D vectorTo(final Point2D other) {
        return new Vector2D(other.x - x, other.y - y);
    }

    public Point2D addLocal(final Vector2D offset) {
        x += offset.x;
        y += offset.y;
        return this;
    }

    public Point2D subtractLocal(final Vector2D offset) {
        x -= offset.x;
        y -= offset.y;
        return this;
    }

    public Vector2D asVector() {
        return new Vector2D(x, y);
    }

    public Point2D projection(final Line2D line) {
        return line.projection(this);
    }

    @Override
    public String toString() {
        return "Point2D[" + x + ", " + y + "]";
    }
}