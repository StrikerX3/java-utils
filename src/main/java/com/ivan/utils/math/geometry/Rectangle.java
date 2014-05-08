package com.ivan.utils.math.geometry;

public class Rectangle {
    public Point2D origin;
    public Vector2D size;

    public Rectangle() {
        this(new Point2D(), new Vector2D());
    }

    public Rectangle(final Point2D p1, final Point2D p2) {
        this(p1.x, p1.y, p2.x, p2.y);
    }

    public Rectangle(final double x1, final double y1, final double x2, final double y2) {
        final double minX = Math.min(x1, x2);
        final double minY = Math.min(y1, y2);
        final double maxX = Math.max(x1, x2);
        final double maxY = Math.max(y2, y2);
        origin = new Point2D(minX, minY);
        size = new Vector2D(maxX - minX, maxY - minY);
    }

    public Rectangle(final Point2D origin, final Vector2D size) {
        this.origin = origin.dup();
        this.size = size.dup();
    }

    public double getArea() {
        return size.x * size.y;
    }

    public Point2D getCenter() {
        return origin.add(size.multiply(0.5));
    }

    public Point2D[] getBoundaryPoints() {
        final double ox = origin.x;
        final double oy = origin.y;
        final double dx = ox + size.x;
        final double dy = oy + size.y;
        return new Point2D[] {
                new Point2D(ox, oy),
                new Point2D(dx, oy),
                new Point2D(dx, dy),
                new Point2D(ox, dy)
        };
    }

    public Line2D[] getBoundaryLines() {
        final double ox = origin.x;
        final double oy = origin.y;
        final double dx = ox + size.x;
        final double dy = oy + size.y;
        return new Line2D[] {
                new Line2D(ox, oy, dx, oy),
                new Line2D(dx, oy, dx, dy),
                new Line2D(dx, dy, ox, dy),
                new Line2D(ox, dy, ox, oy)
        };
    }

    @Override
    public String toString() {
        return "Rectangle[origin=" + origin.x + " x " + origin.y + ", size=" + size.x + " x " + size.y + "]";
    }
}