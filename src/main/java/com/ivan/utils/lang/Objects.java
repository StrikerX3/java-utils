package com.ivan.utils.lang;

public final class Objects {
	private Objects() {
	}

	// equals with null safety
	public static boolean equals(final Object obj1, final Object obj2) {
		if (obj1 == obj2) {
			return true;
		}
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}

	// hashCode with null safety
	public static int hashCode(final Object obj) {
		return obj == null ? 0 : obj.hashCode();
	}

	// toString with null safety
	public static String toString(final Object obj) {
		return obj == null ? "null" : obj.toString();
	}
}
