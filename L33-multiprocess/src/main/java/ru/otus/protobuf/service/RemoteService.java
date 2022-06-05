package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteServiceGrpc.RemoteServiceImplBase;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.Response;

public class RemoteService extends RemoteServiceImplBase {
    @Override
    public void send(Request request, StreamObserver<Response> responseObserver) {
        super.send(request, responseObserver);
    }
}
