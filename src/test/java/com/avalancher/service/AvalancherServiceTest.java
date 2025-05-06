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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertEquals;

public class AvalancherServiceTest {
    private static final Logger log = LoggerFactory.getLogger(AvalancherServiceTest.class);
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
    public static void stopServer() throws InterruptedException {
        if (Objects.nonNull(channel)) {
            channel.shutdownNow();
        }
        if (Objects.nonNull(server)) {
            server.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    @Test
    public void testGenerateId() {
        UniqueIdResponse response = blockingStub.getId(Empty.newBuilder().build());
        log.info("Generated ID: {}", response.getId());
    }

    @Test
    public void test4095ConcurrentGetIdCalls() throws InterruptedException {
        int totalRequests = 4095;
        ExecutorService executor = Executors.newFixedThreadPool(200);
        CountDownLatch latch = new CountDownLatch(totalRequests);
        Set<Long> idSet = Collections.synchronizedSet(new HashSet<>());

        for (int i = 0; i < totalRequests; i++) {
            executor.submit(() -> {
                try {
                    UniqueIdResponse response = blockingStub.getId(Empty.getDefaultInstance());
                    idSet.add(response.getId());
                } catch (Exception e) {
                    e.printStackTrace(); // You can throw if you want to fail test immediately
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        if (!completed) {
            throw new AssertionError("Not all requests completed within timeout");
        }
        assertEquals(totalRequests, idSet.size());
    }

}
