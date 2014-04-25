package com.ivan.utils.space;

public abstract class Point3D implements Point {
	@Override
	public int getDimensions() {
		return 3;
	}

	@Override
	public double get(final int axis) {
		if (axis == 0) {
			return getX();
		}
		if (axis == 1) {
			return getY();
		}
		if (axis == 2) {
			return getZ();
		}
		throw new IllegalArgumentException("Invalid axis: " + axis);
	}

	public abstract double getX();

	public abstract double getY();

	public abstract double getZ();
}
