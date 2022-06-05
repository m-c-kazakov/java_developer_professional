package ru.otus;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeapfrogOfThreads {

    static Queue<Integer> queue = new ArrayBlockingQueue<>(2);

    @SneakyThrows
    public static void main(String[] args) {
        int firstThread = 0;
        queue.add(firstThread);
        int secondThread = 1;
        queue.add(secondThread);
        new Thread(()-> LeapfrogOfThreads.execute(firstThread)).start();
        new Thread(()-> LeapfrogOfThreads.execute(secondThread)).start();
    }

    @SneakyThrows
    public static synchronized void execute(Integer threadNumber) {

        for (int i = 1; i < 11; i++) {
            log.info("ThreadName={} Number={}", Thread.currentThread().getName(), i);
            waitExecute(threadNumber);
        }

        for (int i = 9; i > 0; i--) {
            log.info("ThreadName={} Number={}", Thread.currentThread().getName(), i);
            waitExecute(threadNumber);
        }
        LeapfrogOfThreads.class.notifyAll();
    }

    @SneakyThrows
    private static void waitExecute(Integer threadNumber) {

        queue.poll();
        queue.add(threadNumber);
        while (!Objects.equals(queue.peek(), threadNumber)) {
            LeapfrogOfThreads.class.notifyAll();
            LeapfrogOfThreads.class.wait();
        }

    }
}
