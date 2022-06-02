package ru.otus;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeapfrogOfThreads {

    @SneakyThrows
    public static void main(String[] args) {
        new Thread(LeapfrogOfThreads::execute).start();
        new Thread(LeapfrogOfThreads::execute).start();
    }

    @SneakyThrows
    public static synchronized void execute() {

        for (int i = 1; i < 11; i++) {
            log.info("ThreadName={} Number={}", Thread.currentThread().getName(), i);
            LeapfrogOfThreads.class.notifyAll();
            LeapfrogOfThreads.class.wait();
        }

        for (int i = 9; i > 0; i--) {
            log.info("ThreadName={} Number={}", Thread.currentThread().getName(), i);
            LeapfrogOfThreads.class.notifyAll();
            LeapfrogOfThreads.class.wait();
        }
        LeapfrogOfThreads.class.notifyAll();
    }
}
