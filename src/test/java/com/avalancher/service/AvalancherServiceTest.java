package com.avalancher.service;

import com.avalancher.grpc.AvalancherGrpc;
import com.avalancher.grpc.UniqueIdResponse;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;

public class AvalancherServiceTest {
    private static Server server;
    private static ManagedChannel channel;
    private static AvalancherGrpc.AvalancherBlockingStub blockingStub;
    private static final int PORT = 50052;

    @BeforeAll
    public static void setup() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new AvalancherService())
                .build()
                .start();

        channel = ManagedChannelBuilder.forAddress("localhost", PORT)
                .usePlaintext()
                .build();
        blockingStub = AvalancherGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public static void stopServer() {
        if (Objects.nonNull(channel)) {
            channel.shutdownNow();
        }
        if (Objects.nonNull(server)) {
            server.shutdownNow();
        }
    }

    @Test
    public void testGenerateId() {
        UniqueIdResponse response = blockingStub.getId(Empty.newBuilder().build());
        System.out.println("Generated ID: " + response.getId());
    }

    @Test
    public void throttle_testing(){
        long[] ids = new long[4096];
        IntStream.range(0, 4096).forEach(i ->{
            Thread t = new Thread(() ->{
                UniqueIdResponse response = blockingStub.getId(Empty.newBuilder().build());
                ids[i] = response.getId();
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Set<Long> set = new HashSet<>();
        for(long id: ids) set.add(id);
        assertEquals(ids.length, set.size());
    }

}
