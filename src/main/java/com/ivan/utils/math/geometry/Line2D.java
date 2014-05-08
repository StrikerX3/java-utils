package com.ivan.utils.math.geometry;

public class Line2D {
    public Point2D p1, p2;

    public Line2D() {
        this(new Point2D(), new Point2D());
    }

    public Line2D(final double x1, final double y1, final double x2, final double y2) {
        this(new Point2D(x1, y1), new Point2D(x2, y2));
    }

    public Line2D(final Point2D p1, final Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double lengthSq() {
        final double dx = p2.x - p1.x;
        final double dy = p2.y - p1.y;
        return dx * dx + dy * dy;
    }

    public double length() {
        return Math.sqrt(lengthSq());
    }

    public Point2D projection(final Point2D point) {
        final Vector2D u = asVector();
        final Vector2D a = p1.vectorTo(point);
        final Vector2D proj = a.projection(u);
        return p1.add(proj);
    }

    public Line2D projection(final Line2D line) {
        final Vector2D proj = asVector().projection(line.asVector());
        return new Line2D(p1, p1.add(proj));
    }

    public boolean isPerpendicular(final Line2D line) {
        return asVector().dot(line.asVector()) == 0.0;
    }

    public boolean isParallel(final Line2D line) {
        return asVector().normalizeLocal().dot(line.asVector().normalizeLocal()) == 1.0;
    }

    public Point2D intersectionPoint(final Line2D line) {
        final double dx1 = p2.x - p1.x;
        final double dy1 = p2.y - p1.y;
        final double dx2 = line.p2.x - line.p1.x;
        final double dy2 = line.p2.y - line.p1.y;
        final double denom = dy2 * dx1 - dx2 * dy1;
        if (denom == 0) {
            return null;
        }
        final double ddx = p1.x - line.p1.x;
        final double ddy = p1.y - line.p1.y;
        final double numer1 = dx2 * ddy - dy2 * ddx;
        final double numer2 = dx1 * ddy - dy1 * ddx;
        final double u1 = numer1 / denom;
        final double u2 = numer2 / denom;
        // If  numer1 == 0 && numer2 == 0  && denom == 0, lines coincide.
        // If (numer1 != 0 || numer2 != 0) && denom == 0, lines are parallel,
        //   otherwise there is an intersection.
        // If u1 and u2 are between 0 and 1 (inclusive), the line segments meet
        //   at the intersection point. If u1 and u2 are not between 0 and 1, the
        //   intersection point does not lie within the two segments.
        if (u1 >= 0 && u1 <= 1 && u2 >= 0 && u2 <= 1) {
            return new Point2D(p1.x + u1 * dx1, p1.y + u1 * dy1);
        }
        return null;
    }

    public Vector2D asVector() {
        return p1.vectorTo(p2);
    }

    @Override
    public String toString() {
        return "Line2D[" + p1.x + "x" + p1.y + ", " + p2.x + "x" + p2.y + "]";
    }
}
