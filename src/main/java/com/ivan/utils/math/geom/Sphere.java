package com.ivan.utils.math.geom;

public class Sphere {
    private final Point3D center;

    private final double radius;

    public Sphere(final Point3D center, final double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double getVolume() {
        return 4.0 / 3.0 * Math.PI * radius * radius * radius;
    }
}
