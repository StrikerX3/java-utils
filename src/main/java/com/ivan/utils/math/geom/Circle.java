package com.ivan.utils.math.geom;

public class Circle {
    public Point2D center;

    public double radius;

    public Circle() {
        this(new Point2D(), 0);
    }

    public Circle(final Point2D center, final double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle(final double centerX, final double centerY, final double radius) {
        this(new Point2D(centerX, centerY), radius);
    }

    public Point2D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle[center=" + center.x + "x" + center.y + ", radius=" + radius + "]";
    }
}
