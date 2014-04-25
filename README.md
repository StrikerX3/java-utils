java-utils
==========

Utility classes for Java.

 * **Collections** (beta):
     * Hashmaps with int and long primitives as keys, much faster than Java's HashMap&lt;Integer, ?>
     * map/filter/reduce for arrays (compatible with Java 8 lambda expressions). Also for Collections, redundant with Java 8 but useful for earlier versions.
     * Convert T[] to Iterable&lt;T>
     * Convert Iterable&lt;T> to Collection&lt;T>
     * Convert Collections of boxed types to arrays of primitive types
 * **JAR/Classpath** (alpha):
     * Find classes that implement or extend any class within JARs not currently loaded in the classpath
    * Native library helper: loads native libraries from the classpath, extracting from jar files if necessary
 * **Lang** (beta):
     * Circular buffer (needs improvements, currently only for int primitives)
     * hashCode, equals, toString with null-safety (redundant with Java 7's Objects)
     * Single object that represents a pair (2-tuple) for use as keys in hashmaps
     * Comparable comparator: a Comparator that uses the Comparable.compareTo implementation from the given type
     * Composite comparator: combines multiple comparators into one
     * Unsigned boxed primitives and utility methods for basic arithmetic and toString with radix support, including longs (without resorting to BigInteger like Java 8 does)
     * Lazy initialization of objects
     * Strongly types options and option sets
 * **Math** (alpha):
     * Clamp values to a range
     * Min and max for multiple values
     * Generate random values in a range
     * Linear and spline interpolation
     * Square root for BigDecimals
     * Least common multiple and greatest common divisor for int, long, BigInteger and BigDecimal
     * Faster trigonometrical functions based on lookup tables and nearest/linear/spline interpolation
     * Bit math (based on Hacker's Delight bit twiddling techniques)
     * K-d trees, quadtrees, octrees
 * **Misc** (beta):
     * Hierarchical memory usage tracker
     * Stopwatch
 