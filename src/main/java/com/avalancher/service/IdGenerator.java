package com.avalancher.service;

public class IdGenerator {
    public static long getUniqueId(short workerId, short datacenterId, long timeStamp, short sequence){
        return timeStamp<<22 | ((long) datacenterId <<17) | (workerId<<12) | sequence;
    }
}
