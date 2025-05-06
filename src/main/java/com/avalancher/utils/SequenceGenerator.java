package com.avalancher.utils;

public class SequenceGenerator {
    private static final int MAX_SEQUENCE = 4095; // 12 bits

    private long lastTimestamp = -1L;
    private short sequence = 0;

    public synchronized short nextSequence(long currentTimestamp) {
        if (currentTimestamp == lastTimestamp) {
            sequence++;
            if (sequence > MAX_SEQUENCE) {
                // Busy wait until next millisecond
                while ((currentTimestamp = System.currentTimeMillis()) == lastTimestamp) {
                    Thread.yield();
                }
                lastTimestamp = currentTimestamp;
                sequence = 0;
            }
        } else {
            sequence = 0;
            lastTimestamp = currentTimestamp;
        }
        return sequence;
    }
}
