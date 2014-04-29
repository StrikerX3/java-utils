package com.ivan.utils.time;

/**
 * A simple stopwatch that measures the time elapsed between two instants.
 * Useful for benchmarking code or measuring the duration of an operation.
 * <p>
 * Here's an example of how to use a stopwatch:
 * <pre>
 * Stopwatch sw = new Stopwatch();
 * 
 * sw.start();
 * // ... do some heavy work here
 * long time = sw.timeElapsed(); // in milliseconds
 * </pre>
 * If you need to perform multiple measurements in succession, you can invoke
 * {@link #timeElapsed()} again, which will return the interval of time since
 * the stopwatch was started (most likely a higher value than previous calls of
 * this method), or you can {@link #reset()} then {@link #start()} again to
 * begin a new measurement.<br>
 * The method {@link #timeElapsedNanos()} can be used instead of
 * <code>timeElapsed()</code> for higher precision.
 * <p>
 * The <code>Stopwatch</code> class uses the {@link System#nanoTime()} method
 * for maximum accuracy in time measurements.
 */
public class Stopwatch {
	private long startTime;
	private long pauseTime;
	private boolean started;
	private boolean paused;

	public Stopwatch() {
		reset();
	}

	/**
	 * Resets this stopwatch, stopping it and clearing the time. Resetting a
	 * stopped stopwatch has no effect.
	 */
	public void reset() {
		startTime = 0L;
		started = false;
	}

	/**
	 * Starts this stopwatch. Starting an already started stopwatch has no
	 * effect.
	 * 
	 * @return <code>true</code> if this stopwatch was successfully started;
	 * <code>false</code> if the stopwatch was already started when invoking
	 * this method
	 */
	public boolean start() {
		if (started) {
			return false;
		}
		started = true;
		startTime = System.nanoTime();
		return true;
	}

	/**
	 * Restarts this stopwatch. Effectively resets and immediately starts the
	 * stopwatch.
	 */
	public void restart() {
		reset();
		start();
	}

	/**
	 * Pauses this stopwatch. A paused stopwatch will stop counting time until
	 * it is resumed by invoking {@link #resume()}. Pausing an already paused
	 * stopwatch has no effect. The stopwatch must be started to be paused.
	 * 
	 * @return <code>true</code> if this stopwatch was paused successfully;
	 * <code>false</code> if it was already paused
	 */
	public boolean pause() {
		if (paused || !started) {
			return false;
		}

		pauseTime = System.nanoTime();
		paused = true;

		return true;
	}

	/**
	 * Resumes this stopwatch. The stopwatch will continue counting time from
	 * the point it was paused. Resuming an already resumed stopwatch has no
	 * effect.
	 * 
	 * @return <code>true</code> if this stopwatch was paused successfully;
	 * <code>false</code> if it was already paused
	 */
	public boolean resume() {
		if (!paused || !started) {
			return false;
		}

		startTime += System.nanoTime() - pauseTime;
		paused = false;

		return true;
	}

	/**
	 * Retrieves the time elapsed (in milliseconds) since the stopwatch was
	 * started.
	 * 
	 * @return the time elapsed (in milliseconds)
	 */
	public long timeElapsed() {
		return timeElapsedNanos() / 1000000L;
	}

	/**
	 * Retrieves the time elapsed (in nanoseconds) since the stopwatch was
	 * started.
	 * 
	 * @return the time elapsed (in nanoseconds)
	 */
	public long timeElapsedNanos() {
		if (!started) {
			throw new IllegalStateException("Not started");
		}
		if (paused) {
			return pauseTime - startTime;
		}
		return System.nanoTime() - startTime;
	}
}
