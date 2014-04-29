package com.ivan.utils.system;

public interface MemoryTrackVisitor {
	void visit(MemoryTrack memory, int depth);

	void leave(MemoryTrack memory, int depth);
}
