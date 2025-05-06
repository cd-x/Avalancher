package com.avalancher.config;

public class Config {
    public static final long CUSTOM_EPOCH;
    public static final short DATA_CENTER_ID;
    public static final short WORKER_ID;

    static {
        CUSTOM_EPOCH = parseLongEnv("CUSTOM_EPOCH", 1715000000000L);

        DATA_CENTER_ID = parseShortEnv("DATA_CENTER_ID", (short) 0);
        if (DATA_CENTER_ID < 0 || DATA_CENTER_ID > 31) {
            throw new IllegalArgumentException("DATA_CENTER_ID must be between 0 and 31");
        }

        WORKER_ID = parseShortEnv("WORKER_ID", (short) 0);
        if (WORKER_ID < 0 || WORKER_ID > 31) {
            throw new IllegalArgumentException("WORKER_ID must be between 0 and 31");
        }
    }

    private static long parseLongEnv(String key, long defaultValue) {
        String value = System.getenv(key);
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid long value for " + key);
            }
        }
        return defaultValue;
    }

    private static short parseShortEnv(String key, short defaultValue) {
        String value = System.getenv(key);
        if (value != null) {
            try {
                return Short.parseShort(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid short value for " + key);
            }
        }
        return defaultValue;
    }
}
