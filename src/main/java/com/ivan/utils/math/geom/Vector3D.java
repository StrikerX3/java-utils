package com.ivan.utils.math.geom;

import com.ivan.utils.math.FastMath;

public class Vector3D {
	public double x, y, z;

	public Vector3D() {
		this(0, 0, 0);
	}

	public Vector3D(final Vector3D vector) {
		this(vector.x, vector.y, vector.z);
	}

	public Vector3D(final double x, final double y, final double z) {
		set(x, y, z);
	}

	public Vector3D set(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public double sqLength() {
		return x * x + y * y + z * z;
	}

	public double length() {
		return Math.sqrt(sqLength());
	}

	public Vector3D add(final double scalar) {
		return new Vector3D(x + scalar, y + scalar, z + scalar);
	}

	public Vector3D subtract(final double scalar) {
		return new Vector3D(x - scalar, y - scalar, z - scalar);
	}

	public Vector3D multiply(final double scalar) {
		return new Vector3D(x * scalar, y * scalar, z * scalar);
	}

	public Vector3D divide(final double scalar) {
		return new Vector3D(x / scalar, y / scalar, z / scalar);
	}

	public Vector3D negate() {
		return new Vector3D(-x, -y, -z);
	}

	public double dot(final Vector3D other) {
		return x * other.x + y * other.y + z * other.z;
	}

	public Vector3D cross(final Vector3D other) {
		final Vector3D vec = new Vector3D();
		vec.x = y * other.z - z * other.y;
		vec.y = z * other.x - x * other.z;
		vec.z = x * other.y - y * other.x;
		return vec;
	}

	public Vector3D normalize() {
		if (x == 0 && y == 0 && z == 0) {
			return new Vector3D();
		}
		final double len = 1.0 / length();
		return new Vector3D(x * len, y * len, z * len);
	}

	public Point3D add(final Point3D point) {
		return point.add(this);
	}

	public Point3D subtract(final Point3D point) {
		return point.subtract(this);
	}

	public Vector3D add(final Vector3D vector) {
		return new Vector3D(x + vector.x, y + vector.y, z + vector.z);
	}

	public Vector3D subtract(final Vector3D vector) {
		return new Vector3D(x - vector.x, y - vector.y, z - vector.z);
	}

	public Vector3D addLocal(final double scalar) {
		x += scalar;
		y += scalar;
		z += scalar;
		return this;
	}

	public Vector3D subtractLocal(final double scalar) {
		x -= scalar;
		y -= scalar;
		z -= scalar;
		return this;
	}

	public Vector3D multiplyLocal(final double scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public Vector3D divideLocal(final double scalar) {
		final double inv = 1.0 / scalar;
		x *= inv;
		y *= inv;
		z *= inv;
		return this;
	}

	public Vector3D negateLocal() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public Vector3D addLocal(final Vector3D vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		return this;
	}

	public Vector3D subtractLocal(final Vector3D vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		return this;
	}

	public Vector3D normalizeLocal() {
		if (x == 0 && y == 0 && z == 0) {
			return this;
		}

		final double len = 1.0 / length();
		x *= len;
		y *= len;
		z *= len;
		return this;
	}

	private static Vector3D rotateAxisAngle(final Vector3D axis, final double cos, final double sin) {
		final double t = 1 - cos;

		final double tx = t * axis.x;
		final double ty = t * axis.y;
		final double tz = t * axis.z;

		final double txtytz = tx + ty + tz;

		final double sx = sin * axis.x;
		final double sy = sin * axis.y;
		final double sz = sin * axis.z;

		final double u = tx * txtytz + sy - sz + cos;
		final double v = ty * txtytz - sx + sz + cos;
		final double w = tz * txtytz + sx - sy + cos;

		return new Vector3D(u, v, w);
	}

	public static Vector3D rotateAxisAngle(final Vector3D axis, final double angle) {
		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public static Vector3D fastRotateAxisAngle(final Vector3D axis, final double angle) {
		final double cos = FastMath.cos(angle);
		final double sin = FastMath.sin(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public static Vector3D fastLerpRotateAxisAngle(final Vector3D axis, final double angle) {
		final double cos = FastMath.cosLerp(angle);
		final double sin = FastMath.sinLerp(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public static Vector3D fastSlerpRotateAxisAngle(final Vector3D axis, final double angle) {
		final double cos = FastMath.cosSlerp(angle);
		final double sin = FastMath.sinSlerp(angle);
		return rotateAxisAngle(axis, cos, sin);
	}

	public Vector3D reflect(final Vector3D vector) {
		final Vector3D normal = vector.normalize();
		final double dot = dot(normal) * 2;
		if (dot < 0.0) {
			return null;
		}
		return subtract(normal.multiplyLocal(dot));
	}

	public Vector3D reflectDoubleSided(final Vector3D vector) {
		final Vector3D normal = vector.normalize();
		final double dot = dot(normal) * 2;
		return subtract(normal.multiplyLocal(dot));
	}

	public Vector3D project(final Vector3D vector) {
		final double dot1 = dot(vector);
		final double dot2 = vector.dot(vector);
		final double dot = dot1 / dot2;
		return vector.multiply(dot);
	}

	public Point3D asPoint() {
		return new Point3D(x, y, z);
	}

	public static Vector3D createZeroVector() {
		return new Vector3D();
	}

	public static Vector3D createXVector() {
		return new Vector3D(1, 0, 0);
	}

	public static Vector3D createYVector() {
		return new Vector3D(0, 1, 0);
	}

	public static Vector3D createZVector() {
		return new Vector3D(0, 0, 1);
	}

	public static Vector3D createXYVector() {
		return new Vector3D(1, 1, 0).normalizeLocal();
	}

	public static Vector3D createXZVector() {
		return new Vector3D(1, 0, 1).normalizeLocal();
	}

	public static Vector3D createYZVector() {
		return new Vector3D(0, 1, 1).normalizeLocal();
	}

	public static Vector3D createXYZVector() {
		return new Vector3D(1, 1, 1).normalizeLocal();
	}

	public static Vector3D create(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
		return new Vector3D(x2 - x1, y2 - y1, z2 - z1);
	}

	@Override
	public String toString() {
		return "Vector[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
