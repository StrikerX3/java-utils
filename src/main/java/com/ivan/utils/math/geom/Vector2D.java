package com.ivan.utils.math.geom;

import com.ivan.utils.math.FastMath;

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

	public double sqLength() {
		return x * x + y * y;
	}

	public double length() {
		return Math.sqrt(sqLength());
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
		final double len = 1.0 / length();
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

		final double len = 1.0 / length();
		x *= len;
		y *= len;
		return this;
	}

	private static Vector2D rotateAxisAngle(final Vector2D axis, final double cos, final double sin) {
		final double t = 1 - cos;

		final double tx = t * axis.x;
		final double ty = t * axis.y;

		final double txty = tx + ty;

		final double sx = sin * axis.x;
		final double sy = sin * axis.y;

		final double u = tx * txty + sy;
		final double v = ty * txty - sx;

		return new Vector2D(u, v);
	}

	public Vector2D rotateAxisAngle(final Vector2D axis, final double angle) {
		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public Vector2D fastRotateAxisAngle(final Vector2D axis, final double angle) {
		final double cos = FastMath.cos(angle);
		final double sin = FastMath.sin(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public Vector2D fastLerpRotateAxisAngle(final Vector2D axis, final double angle) {
		final double cos = FastMath.cosLerp(angle);
		final double sin = FastMath.sinLerp(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public Vector2D fastSlerpRotateAxisAngle(final Vector2D axis, final double angle) {
		final double cos = FastMath.cosSlerp(angle);
		final double sin = FastMath.sinSlerp(angle);
		return rotateAxisAngle(axis, cos, sin);
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

	public Vector2D project(final Vector2D vector) {
		final double dot1 = dot(vector);
		final double dot2 = vector.dot(vector);
		final double dot = dot1 / dot2;
		return vector.multiply(dot);
	}

	public Point2D asPoint() {
		return new Point2D(x, y);
	}

	public static Vector2D createZeroVector() {
		return new Vector2D();
	}

	public static Vector2D createXVector() {
		return new Vector2D(1, 0);
	}

	public static Vector2D createYVector() {
		return new Vector2D(0, 1);
	}

	public static Vector2D createXYVector() {
		return new Vector2D(1, 1).normalizeLocal();
	}

	public static Vector2D create(final double x1, final double y1, final double x2, final double y2) {
		return new Vector2D(x2 - x1, y2 - y1);
	}

	@Override
	public String toString() {
		return "Vector[x=" + x + ", y=" + y + "]";
	}
}
