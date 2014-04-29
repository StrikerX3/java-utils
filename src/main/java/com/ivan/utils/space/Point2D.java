package com.ivan.utils.space;

public abstract class Point2D implements Point {
	@Override
	public int getDimensions() {
		return 2;
	}

	@Override
	public double get(final int axis) {
		if (axis == 0) {
			return getX();
		}
		if (axis == 1) {
			return getY();
		}
		throw new IllegalArgumentException("Invalid axis: " + axis);
	}

	public abstract double getX();

	public abstract double getY();
}
