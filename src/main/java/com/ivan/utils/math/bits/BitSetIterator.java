package com.ivan.utils.math.bits;

import java.util.BitSet;
import java.util.Iterator;

public abstract class BitSetIterator implements Iterator<Integer> {
	final BitSet bitSet;
	int pos = -1;

	BitSetIterator(final BitSet bitSet) {
		this.bitSet = bitSet;
	}

	public static BitSetIterator setIterator(final BitSet set) {
		return new SetIterator(set);
	}

	public static BitSetIterator clearIterator(final BitSet set) {
		return new ClearIterator(set);
	}

	@Override
	public boolean hasNext() {
		return nextBitPos() >= 0;
	}

	@Override
	public Integer next() {
		return pos = nextBitPos();
	}

	public void set() {
		bitSet.set(pos);
	}

	public void clear() {
		bitSet.clear(pos);
	}

	public void flip() {
		bitSet.flip(pos);
	}

	public int getPos() {
		return pos;
	}

	public abstract boolean isFinite();

	protected abstract int nextBitPos();

	private static final class SetIterator extends BitSetIterator {
		SetIterator(final BitSet bitSet) {
			super(bitSet);
		}

		@Override
		public boolean isFinite() {
			return true;
		}

		@Override
		protected int nextBitPos() {
			return bitSet.nextSetBit(pos + 1);
		}

		@Override
		public void remove() {
			bitSet.clear(pos);
		}
	}

	private static final class ClearIterator extends BitSetIterator {
		ClearIterator(final BitSet bitSet) {
			super(bitSet);
		}

		@Override
		public boolean isFinite() {
			return false;
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		protected int nextBitPos() {
			return bitSet.nextClearBit(pos + 1);
		}

		@Override
		public void remove() {
			bitSet.set(pos);
		}
	}
}
