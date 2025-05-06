package com.avalancher.utils;

import com.avalancher.config.Config;
import com.avalancher.utils.Assembler;
import com.avalancher.utils.SequenceGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblerTest {

    @Test
    public void testGenerate4095UniqueIdsInSameMillisecond() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        long currentTimestamp = System.currentTimeMillis();
        Set<Long> generatedIds = new HashSet<>();

        for (int i = 0; i < 4095; i++) {
            short sequence = sequenceGenerator.nextSequence(currentTimestamp);

            long id = Assembler.getUniqueId(
                    Config.WORKER_ID,
                    Config.DATA_CENTER_ID,
                    currentTimestamp,
                    sequence
            );

            boolean added = generatedIds.add(id);
            if (!added) {
                throw new AssertionError("Duplicate ID found at iteration " + i);
            }
        }

        assertEquals(4095, generatedIds.size(), "Should generate 4095 unique IDs in the same millisecond");
    }

}
