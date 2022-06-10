package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.Response;

import java.util.concurrent.atomic.AtomicLong;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StreamObserverImpl implements StreamObserver<Response> {

    static Logger log = LoggerFactory.getLogger(StreamObserverImpl.class);

    AtomicLong result = new AtomicLong(0);

    @Override
    public void onNext(Response response) {
        changeResult(response.getResult());
    }

    private synchronized void changeResult(Long newResult) {
        result.set(newResult);
    }

    @Override
    public void onError(Throwable t) {
        log.error("При получении результата от GRPC сервера произошла ошибка={}", t.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("Запрос выполнен!");
    }

    public synchronized Long getLastNumber() {
        Long lastResult = result.get();
        log.info("lastResult={}", lastResult);
        changeResult(0L);
        return lastResult;
    }
}
