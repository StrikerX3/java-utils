package com.ivan.utils.math.noise;

import com.ivan.utils.math.geometry.Point3D;


public class Perlin {
	private final int[] p = new int[512];

	private static int permutation[] = { 151, 160, 137, 91, 90, 15,
			131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
			190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
			88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
			77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
			102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
			135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
			5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
			23, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
			129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
			251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
			49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
			138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
	};

	private static final Perlin instance = new Perlin();

	private Perlin() {
		for (int i = 0; i < 256; i++) {
			p[256 + i] = p[i] = permutation[i];
		}
	}

	public static Perlin getInstance() {
		return instance;
	}

	private static double fade(final double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	private static double lerp(final double t, final double a, final double b) {
		return a + t * (b - a);
	}

	private static double grad(final int hash, final double x, final double y, final double z) {
		final int h = hash & 15;
		// convert lowest 4 bits of hash code into 12 gradient directions
		final double u = h < 8 || h == 12 || h == 13 ? x : y;
		final double v = h < 4 || h == 12 || h == 13 ? y : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	public static double noise(final Point3D point) {
		return noise(point.x, point.y, point.z);
	}

	public static double noise(double x, double y, double z) {
		final Perlin myPerlin = getInstance();

		// find unit cube that contains point
		final int X = (int) Math.floor(x) & 255;
		final int Y = (int) Math.floor(y) & 255;
		final int Z = (int) Math.floor(z) & 255;

		// find relative x,y,z of point in cube
		x -= Math.floor(x);
		y -= Math.floor(y);
		z -= Math.floor(z);

		// compute fade curves for each of x,y,z
		final double u = fade(x);
		final double v = fade(y);
		final double w = fade(z);

		// hash coordinates of the 8 cube corners
		final int A = myPerlin.p[X] + Y;
		final int AA = myPerlin.p[A] + Z;
		final int AB = myPerlin.p[A + 1] + Z;
		final int B = myPerlin.p[X + 1] + Y;
		final int BA = myPerlin.p[B] + Z;
		final int BB = myPerlin.p[B + 1] + Z;

		// add blended results from 8 corners of cube
		return lerp(w, lerp(v, lerp(u, grad(myPerlin.p[AA], x, y, z),
				grad(myPerlin.p[BA], x - 1, y, z)),
				lerp(u, grad(myPerlin.p[AB], x, y - 1, z),
						grad(myPerlin.p[BB], x - 1, y - 1, z))),
				lerp(v, lerp(u, grad(myPerlin.p[AA + 1], x, y, z - 1),
						grad(myPerlin.p[BA + 1], x - 1, y, z - 1)),
						lerp(u, grad(myPerlin.p[AB + 1], x, y - 1, z - 1),
								grad(myPerlin.p[BB + 1], x - 1, y - 1, z - 1))));
	}
}
