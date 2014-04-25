package com.ivan.utils.lang;

import java.util.concurrent.Semaphore;

public class CircularBuffer {
    private final int[] data;

    private int readPos, writePos;

    private long readLen, writeLen;

    private final Semaphore writeLock = new Semaphore(1);

    private final Semaphore readLock = new Semaphore(1);

    public CircularBuffer(final int size) {
        data = new int[size];
    }

    public int getSize() {
        return data.length;
    }

    public int availableRead() {
        return (int) (writeLen - readLen);
    }

    public int availableWrite() {
        return (int) (data.length - writeLen + readLen);
    }

    public long getReadSize() {
        return readLen;
    }

    public long getWrittenSize() {
        return writeLen;
    }

    public final int read(final int[] b) {
        return read(b, 0, b.length);
    }

    public final int read(final int[] b, final int offset, final int length) {
        final int lenToEndOfArray = data.length - readPos;
        final int remainingSize = availableRead();
        final int readSize = Math.min(length, remainingSize);
        if (lenToEndOfArray > readSize) {
            System.arraycopy(data, readPos, b, offset, readSize);
        } else {
            System.arraycopy(data, readPos, b, offset, lenToEndOfArray);
            System.arraycopy(data, offset, b, lenToEndOfArray, readSize - lenToEndOfArray);
            readPos -= data.length;
        }
        readPos += readSize;
        readLen += readSize;

        writeLock.release();
        return readSize;
    }

    public void readFully(final int[] out) {
        int remainingLen = out.length;
        int len = read(out);
        int totalLen = len;
        remainingLen -= len;
        while (remainingLen > 0) {
            for (;;) {
                try {
                    readLock.acquire();
                    break;
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            len = read(out, totalLen, remainingLen);
            totalLen += len;
            remainingLen -= len;
        }
    }

    public final int write(final int[] b) {
        return write(b, 0, b.length);
    }

    public final int write(final int[] b, final int offset, final int length) {
        final int lenToEndOfArray = data.length - writePos;
        final int remainingSize = availableWrite();
        final int writeSize = Math.min(length, remainingSize);
        if (lenToEndOfArray > writeSize) {
            System.arraycopy(b, offset, data, writePos, writeSize);
        } else {
            System.arraycopy(b, offset, data, writePos, lenToEndOfArray);
            System.arraycopy(b, offset + lenToEndOfArray, data, 0, writeSize - lenToEndOfArray);
            writePos -= data.length;
        }
        writePos += writeSize;
        writeLen += writeSize;
        readLock.release();
        return writeSize;
    }

    public void writeFully(final int[] out) {
        int remainingLen = out.length;
        int len = write(out);
        int totalLen = len;
        remainingLen -= len;
        while (remainingLen > 0) {
            for (;;) {
                try {
                    writeLock.acquire();
                    break;
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            len = write(out, totalLen, remainingLen);
            totalLen += len;
            remainingLen -= len;
        }
    }
}
