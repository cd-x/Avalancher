package com.avalancher.service;

import com.avalancher.grpc.avalancherGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import org.junit.jupiter.api.BeforeEach;

public class AvalancherServiceTest {
    private Server server;
    private ManagedChannel channel;
    private avalancherGrpc.avalancherBlockingStub blockingStub;

    @BeforeEach
    public void setup(){
    }

}
