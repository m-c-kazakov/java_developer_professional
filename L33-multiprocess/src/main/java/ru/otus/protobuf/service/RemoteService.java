package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteServiceGrpc.RemoteServiceImplBase;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.Response;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RemoteService extends RemoteServiceImplBase {
    @Override
    public void send(Request request, StreamObserver<Response> responseObserver) {

        AtomicLong currentNumber = new AtomicLong(request.getFirstNumber());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable runnable = () -> {
            long value = currentNumber.incrementAndGet();
            Response response = Response.newBuilder()
                    .setResult(value)
                    .build();

            responseObserver.onNext(response);
            if (value >= request.getLastNumber()) {
                scheduledExecutorService.shutdown();
                responseObserver.onCompleted();
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
    }
}
