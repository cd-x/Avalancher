package com.avalancher.utils;

public class IdGenerator {
    private static final long CUSTOM_EPOCH = 1609459200000L; // Jan 1, 2021

    public static long getUniqueId(int workerId, int datacenterId, long timestampMillis, int sequence) {
        long timePart = (timestampMillis - CUSTOM_EPOCH) & 0x1FFFFFFFFFFL; // 41 bits
        long datacenterPart = datacenterId & 0x1F; // 5 bits
        long workerPart = workerId & 0x1F;         // 5 bits
        long sequencePart = sequence & 0xFFF;      // 12 bits

        return (timePart << 22)
                | (datacenterPart << 17)
                | (workerPart << 12)
                | sequencePart;
    }
}