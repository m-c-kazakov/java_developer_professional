package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.service.StreamObserverImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        RemoteServiceGrpc.RemoteServiceStub remoteServiceStub = RemoteServiceGrpc.newStub(channel);


        Request request = Request.newBuilder()
                .setFirstNumber(1)
                .setLastNumber(10)
                .build();

        StreamObserverImpl observer = new StreamObserverImpl();
        remoteServiceStub.send(request, observer);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        AtomicReference<Long> value = new AtomicReference<>(0L);

        AtomicReference<Integer> cursor = new AtomicReference<>(0);
        Runnable runnable = () -> {
            value.set(value.get() + observer.getLastNumber() + 1);
            log.info("RESULT: " + value);
            if (cursor.get() >= 50) {
                scheduledExecutorService.shutdown();
            }
            cursor.set(cursor.get() + 1);
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }
}
