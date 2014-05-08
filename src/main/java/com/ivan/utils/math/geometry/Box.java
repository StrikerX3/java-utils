package com.ivan.utils.math.geometry;

public class Box {
    public Point3D origin;
    public Vector3D size;

    public Box() {
        this(new Point3D(), new Vector3D());
    }

    public Box(final Point3D p1, final Point3D p2) {
        this(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
    }

    public Box(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double minX = Math.min(x1, x2);
        final double minY = Math.min(y1, y2);
        final double minZ = Math.min(z1, z2);
        final double maxX = Math.max(x1, x2);
        final double maxY = Math.max(y2, y2);
        final double maxZ = Math.max(z2, z2);
        origin = new Point3D(minX, minY, minZ);
        size = new Vector3D(maxX - minX, maxY - minY, maxZ - minZ);
    }

    public Box(final Point3D origin, final Vector3D size) {
        this.origin = origin.dup();
        this.size = size.dup();
    }

    public double getVolume() {
        return size.x * size.y * size.z;
    }

    public Point3D getCenter() {
        return origin.add(size.multiply(0.5));
    }

    public Point3D[] getBoundaryPoints() {
        final double ox = origin.x;
        final double oy = origin.y;
        final double oz = origin.z;
        final double dx = ox + size.x;
        final double dy = oy + size.y;
        final double dz = oz + size.z;
        return new Point3D[] {
                new Point3D(ox, oy, oz),
                new Point3D(dx, oy, oz),
                new Point3D(dx, dy, oz),
                new Point3D(ox, dy, oz),
                new Point3D(ox, oy, dz),
                new Point3D(dx, oy, dz),
                new Point3D(dx, dy, dz),
                new Point3D(ox, dy, dz)
        };
    }

    public Line3D[] getBoundaryLines() {
        final double ox = origin.x;
        final double oy = origin.y;
        final double oz = origin.z;
        final double dx = ox + size.x;
        final double dy = oy + size.y;
        final double dz = oz + size.z;
        return new Line3D[] {
                new Line3D(ox, oy, oz, dx, oy, oz),
                new Line3D(dx, oy, oz, dx, dy, oz),
                new Line3D(dx, dy, oz, ox, dy, oz),
                new Line3D(ox, dy, oz, ox, oy, oz),
                new Line3D(ox, oy, dz, dx, oy, dz),
                new Line3D(dx, oy, dz, dx, dy, dz),
                new Line3D(dx, dy, dz, ox, dy, dz),
                new Line3D(ox, dy, dz, ox, oy, dz),
                new Line3D(ox, oy, oz, ox, oy, dz),
                new Line3D(dx, oy, oz, dx, oy, dz),
                new Line3D(dx, dy, oz, dx, dy, dz),
                new Line3D(ox, dy, oz, ox, dy, dz)
        };
    }
}