package com.avalancher.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SequenceGeneratorTest {
    @Test
    public void testSequenceOverflow() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        long timestamp = System.currentTimeMillis();

        for (int i = 0; i < 4096; i++) {
            sequenceGenerator.nextSequence(timestamp);
        }

        // Now it should overflow or reset
        short seq = sequenceGenerator.nextSequence(timestamp);

        assertEquals(0, seq, "Sequence should reset after 4095");
    }

}
