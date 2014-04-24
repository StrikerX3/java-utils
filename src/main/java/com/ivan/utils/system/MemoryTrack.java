package com.ivan.utils.system;

import java.util.LinkedList;
import java.util.List;

public class MemoryTrack {
	private final String name;

	private long initialFree;
	private long initialTotal;

	private long lastFree;
	private long lastTotal;

	private long free;
	private long total;

	private final List<MemoryTrack> children = new LinkedList<MemoryTrack>();

	private final boolean autoGC;

	private boolean done;

	public MemoryTrack(final String name) {
		this(name, false);
	}

	public MemoryTrack(final String name, final boolean autoGC) {
		this.name = name;
		this.autoGC = autoGC;
		start();
	}

	private void start() {
		final Runtime rt = Runtime.getRuntime();
		if (autoGC) {
			System.gc();
		}
		initialFree = lastFree = rt.freeMemory();
		initialTotal = lastTotal = rt.totalMemory();
	}

	public MemoryTrack start(final String name) {
		final MemoryTrack mem = new MemoryTrack(name, autoGC);
		children.add(mem);
		return mem;
	}

	public MemoryTrack record(final String name) {
		final MemoryTrack mem = start(name);
		mem.initialFree = lastFree;
		mem.initialTotal = lastTotal;
		mem.done();

		final Runtime rt = Runtime.getRuntime();
		lastFree = rt.freeMemory();
		lastTotal = rt.totalMemory();

		return mem;
	}

	public void done() {
		if (done) {
			return;
		}
		final Runtime rt = Runtime.getRuntime();
		if (autoGC) {
			System.gc();
		}
		free = rt.freeMemory();
		total = rt.totalMemory();

		for (final MemoryTrack child : children) {
			child.done();
		}
		done = true;
	}

	public String getName() {
		return name;
	}

	public long getFree() {
		return free - initialFree;
	}

	public long getTotal() {
		return total - initialTotal;
	}

	public long getUsed() {
		return getTotal() - getFree();
	}

	public void visit(final MemoryTrackVisitor visitor) {
		visit(visitor, this, 0);
	}

	private static void visit(final MemoryTrackVisitor visitor, final MemoryTrack mem, final int depth) {
		visitor.visit(mem, depth);
		for (final MemoryTrack child : mem.children) {
			visit(visitor, child, depth + 1);
		}
		visitor.leave(mem, depth);
	}
}
