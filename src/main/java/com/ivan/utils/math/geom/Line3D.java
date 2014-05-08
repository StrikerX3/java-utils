package com.ivan.utils.math.geom;

public class Line3D {
    public Point3D p1, p2;

    public Line3D() {
        this(new Point3D(), new Point3D());
    }

    public Line3D(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        this(new Point3D(x1, y1, z1), new Point3D(x2, y2, z2));
    }

    public Line3D(final Point3D p1, final Point3D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double lengthSq() {
        final double dx = p2.x - p1.x;
        final double dy = p2.y - p1.y;
        final double dz = p2.z - p1.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double length() {
        return Math.sqrt(lengthSq());
    }

    public Point3D projection(final Point3D point) {
        final Vector3D u = getVector();
        final Vector3D a = p1.vectorTo(point);
        final Vector3D proj = a.projection(u);
        return p1.add(proj);
    }

    public Line3D projection(final Line3D line) {
        final Vector3D proj = getVector().projection(line.getVector());
        return new Line3D(p1, p1.add(proj));
    }

    public boolean isPerpendicular(final Line3D line) {
        return getVector().dot(line.getVector()) == 0.0;
    }

    public boolean isParallel(final Line3D line) {
        return getVector().normalizeLocal().dot(line.getVector().normalizeLocal()) == 1.0;
    }

    public double closestDistance(final Line3D line) {
        // TODO shortest distance between the two lines
        throw new UnsupportedOperationException();
    }

    public Point3D[] closestPoints(final Line3D line) {
        // TODO one point on each line that creates the shortest distance between them
        throw new UnsupportedOperationException();
    }

    public Point3D closestPoint(final Point3D line) {
        // TODO closest point
        // project a vector formed by one end of the line segment and the point
        // in question onto the vector formed by the line
        throw new UnsupportedOperationException();
    }

    public Point3D intersectionPoint(final Line3D line) {
        // FIXME still incorrect
        // http://mathforum.org/library/drmath/view/62814.html
        final double dx1 = p2.x - p1.x;
        final double dy1 = p2.y - p1.y;
        final double dz1 = p2.z - p1.z;
        final double dx2 = line.p2.x - line.p1.x;
        final double dy2 = line.p2.y - line.p1.y;
        final double dz2 = line.p2.z - line.p1.z;
        final double denom = dy1 * dz2 + dz1 * dx2 + dx1 * dy2 - dx1 * dz2 - dz1 * dy2 - dy1 * dx2;
        if (denom == 0) {
            return null;
        }
        final double ddx = p1.x - line.p1.x;
        final double ddy = p1.y - line.p1.y;
        final double ddz = p1.z - line.p1.z;
        final double numer1 = dy2 * ddz + dz2 * ddx + dx2 * ddy - dx2 * ddz - dz2 * ddy - dy2 * ddx;
        final double numer2 = dy1 * ddz + dz1 * ddx + dx1 * ddy - dx1 * ddz - dz1 * ddy - dy1 * ddx;
        final double u1 = numer1 / denom;
        final double u2 = numer2 / denom;
        // If  numer1 == 0 && numer2 == 0  && denom == 0, lines coincide.
        // If (numer1 != 0 || numer2 != 0) && denom == 0, lines are parallel,
        //   otherwise there is an intersection.
        // If u1 and u2 are between 0 and 1 (inclusive), the line segments meet
        //   at the intersection point. If u1 and u2 are not between 0 and 1, the
        //   intersection point does not lie within the two segments.
        System.out.println(u1 + "  " + u2);
        if (u1 >= 0 && u1 <= 1 && u2 >= 0 && u2 <= 1) {
            return new Point3D(p1.x + u1 * dx1, p1.y + u1 * dy1, p1.z + u1 * dz1);
        }
        return null;
    }

    public Vector3D getVector() {
        return p1.vectorTo(p2);
    }

    @Override
    public String toString() {
        return "Line3D[" + p1.x + "x" + p1.y + "x" + p1.z + ", " + p2.x + "x" + p2.y + "x" + p2.z + "]";
    }
}
