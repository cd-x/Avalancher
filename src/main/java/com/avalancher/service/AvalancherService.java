package com.avalancher.service;

import com.avalancher.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.UUID;

public class AvalancherService extends avalancherGrpc.avalancherImplBase {
    private static final long WORKER_ID = 1L;
    private static final long DATA_CENTER_ID = 100L;

    @Override
    public void getWorkerId(Empty request, StreamObserver<WorkerIdResponse> responseObserver) {
        WorkerIdResponse response = WorkerIdResponse.newBuilder()
                .setWorkerId(WORKER_ID)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTimestamp(Empty request, StreamObserver<TimeStampResponse> responseObserver) {
        TimeStampResponse response = TimeStampResponse.newBuilder()
                .setTimstamp(Instant.now().toEpochMilli())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getId(Empty request, StreamObserver<UniqueIdResponse> responseObserver) {
        UniqueIdResponse response = UniqueIdResponse.newBuilder()
                .setUseragent(UUID.randomUUID().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDatacenterId(Empty request, StreamObserver<DataCenterIdResponse> responseObserver) {
        DataCenterIdResponse response = DataCenterIdResponse.newBuilder()
                .setDatacenterId(DATA_CENTER_ID)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
