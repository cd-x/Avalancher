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
        if (channel != null) {
            channel.shutdownNow();
        }
        if (server != null) {
            server.shutdownNow();
        }
    }

    @Test
    public void testGenerateId() {
        UniqueIdResponse response = blockingStub.getId(Empty.newBuilder().build());
        System.out.println("Generated ID: " + response.getUseragent());
    }


}
