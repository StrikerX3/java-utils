package com.ivan.utils.math.geom;

import com.ivan.utils.math.FastMath;

/**
 * A vector in a bidimensional space.
 */
public class Vector2D {
    public double x, y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(final Vector2D vector) {
        this(vector.x, vector.y);
    }

    public Vector2D(final double x, final double y) {
        set(x, y);
    }

    public Vector2D set(final double x, final double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2D dup() {
        return new Vector2D(this);
    }

    public double magnitudeSq() {
        return x * x + y * y;
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSq());
    }

    public Vector2D normalLH() {
        return new Vector2D(-y, x).normalizeLocal();
    }

    public Vector2D normalRH() {
        return new Vector2D(y, -x).normalizeLocal();
    }

    public Vector2D add(final double scalar) {
        return new Vector2D(x + scalar, y + scalar);
    }

    public Vector2D subtract(final double scalar) {
        return new Vector2D(x - scalar, y - scalar);
    }

    public Vector2D multiply(final double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D divide(final double scalar) {
        return new Vector2D(x / scalar, y / scalar);
    }

    public Vector2D negate() {
        return new Vector2D(-x, -y);
    }

    public double dot(final Vector2D other) {
        return x * other.x + y * other.y;
    }

    public Vector2D normalize() {
        if (x == 0 && y == 0) {
            return new Vector2D();
        }
        final double len = 1.0 / magnitude();
        return new Vector2D(x * len, y * len);
    }

    public Point2D add(final Point2D point) {
        return point.add(this);
    }

    public Point2D subtract(final Point2D point) {
        return point.subtract(this);
    }

    public Vector2D add(final Vector2D vector) {
        return new Vector2D(x + vector.x, y + vector.y);
    }

    public Vector2D subtract(final Vector2D vector) {
        return new Vector2D(x - vector.x, y - vector.y);
    }

    public Vector2D addLocal(final double scalar) {
        x += scalar;
        y += scalar;
        return this;
    }

    public Vector2D subtractLocal(final double scalar) {
        x -= scalar;
        y -= scalar;
        return this;
    }

    public Vector2D multiplyLocal(final double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public Vector2D divideLocal(final double scalar) {
        final double inv = 1.0 / scalar;
        x *= inv;
        y *= inv;
        return this;
    }

    public Vector2D negateLocal() {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2D addLocal(final Vector2D vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vector2D subtractLocal(final Vector2D vector) {
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vector2D normalizeLocal() {
        if (x == 0 && y == 0) {
            return this;
        }

        final double len = 1.0 / magnitude();
        x *= len;
        y *= len;
        return this;
    }

    public Vector2D projection(final Vector2D vec) {
        // (a.u / |u|^2) u
        final double dot = dot(vec); // a.u
        double mod = vec.magnitude(); // |u|
        mod *= mod; // |u|^2
        return vec.multiply(dot / mod);
    }

    private Vector2D rotate(final double cos, final double sin) {
        // x' = x cos f - y sin f
        // y' = y cos f + x sin f
        final double x = this.x;
        final double y = this.y;
        final double nx = x * cos - y * sin;
        final double ny = y * cos + x * sin;
        return new Vector2D(nx, ny);
    }

    public Vector2D rotate(final double angle) {
        return rotate(Math.cos(angle), Math.sin(angle));
    }

    public Vector2D rotateFast(final double angle) {
        return rotate(FastMath.cos(angle), FastMath.sin(angle));
    }

    public Vector2D rotateFastLerp(final double angle) {
        return rotate(FastMath.cosLerp(angle), FastMath.sinLerp(angle));
    }

    public Vector2D rotateFastSlerp(final double angle) {
        return rotate(FastMath.cosSlerp(angle), FastMath.sinSlerp(angle));
    }

    public Vector2D reflect(final Vector2D vector) {
        final Vector2D normal = vector.normalize();
        final double dot = dot(normal) * 2;
        if (dot < 0.0) {
            return null;
        }
        return subtract(normal.multiplyLocal(dot));
    }

    public Vector2D reflectDoubleSided(final Vector2D vector) {
        final Vector2D normal = vector.normalize();
        final double dot = dot(normal) * 2;
        return subtract(normal.multiplyLocal(dot));
    }

    public Point2D asPoint() {
        return new Point2D(x, y);
    }

    public static Vector2D zeroVector() {
        return new Vector2D(0, 0);
    }

    public static Vector2D unitVectorX() {
        return new Vector2D(1, 0);
    }

    public static Vector2D unitVectorY() {
        return new Vector2D(0, 1);
    }

    public static Vector2D unitVectorXY() {
        return new Vector2D(1, 1).normalizeLocal();
    }

    public static Vector2D create(final double x1, final double y1, final double x2, final double y2) {
        return new Vector2D(x2 - x1, y2 - y1);
    }

    @Override
    public String toString() {
        return "Vector2D[" + x + ", " + y + "]";
    }
}