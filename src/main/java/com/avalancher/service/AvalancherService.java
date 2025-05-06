package com.avalancher.service;

import com.avalancher.config.Config;
import com.avalancher.grpc.*;
import com.avalancher.utils.IdGenerator;
import com.avalancher.utils.SequenceGenerator;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;

public class AvalancherService extends AvalancherGrpc.AvalancherImplBase {
    private final SequenceGenerator sequenceGenerator = new SequenceGenerator();

    @Override
    public void getWorkerId(Empty request, StreamObserver<WorkerIdResponse> responseObserver) {
        WorkerIdResponse response = WorkerIdResponse.newBuilder()
                .setWorkerId(Config.WORKER_ID).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getId(Empty request, StreamObserver<UniqueIdResponse> responseObserver) {
        long timestamp = System.currentTimeMillis();
        short sequence = sequenceGenerator.nextSequence(timestamp);
        long id = IdGenerator.getUniqueId(Config.WORKER_ID, Config.DATA_CENTER_ID, timestamp, sequence);
        UniqueIdResponse response = UniqueIdResponse.newBuilder().setId(id).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDatacenterId(Empty request, StreamObserver<DataCenterIdResponse> responseObserver) {
        DataCenterIdResponse response = DataCenterIdResponse.newBuilder()
                .setDatacenterId(Config.DATA_CENTER_ID).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
