package com.avalancher.service;

import com.avalancher.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.time.Instant;

public class AvalancherService extends AvalancherGrpc.AvalancherImplBase {
    private static final short WORKER_ID = 1;
    private static final short DATA_CENTER_ID = 100;

    @Override
    public void getWorkerId(Empty request, StreamObserver<WorkerIdResponse> responseObserver) {
        WorkerIdResponse response = WorkerIdResponse.newBuilder()
                .setWorkerId(WORKER_ID)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getId(Empty request, StreamObserver<UniqueIdResponse> responseObserver) {
        UniqueIdResponse response = UniqueIdResponse.newBuilder()
                .setUseragent(IdGenerator.getUniqueId(WORKER_ID, DATA_CENTER_ID, Instant.now().toEpochMilli(), (short) 123))
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
