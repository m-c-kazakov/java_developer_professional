package ru.otus.services.processors;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

// Этот класс нужно реализовать 2
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final BlockingQueue<SensorData> dataBuffer;

    private final ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new ArrayBlockingQueue<>(bufferSize*2, true);
    }

    @Override
    public synchronized void process(SensorData data) {
        dataBuffer.add(data);

        checkDataBufferAndFlush();

    }

    private synchronized void checkDataBufferAndFlush() {
        threadPoolExecutor.execute(() -> {
            if (dataBuffer.size() >= bufferSize) {
                flush();
            }
        });
    }

    public synchronized void flush() {
        List<SensorData> bufferData = new ArrayList<>();

        while (!dataBuffer.isEmpty()) {
            SensorData poll = dataBuffer.poll();
            bufferData.add(poll);
            if (bufferData.size() >= bufferSize) {
                break;
            }
        }
        if (!bufferData.isEmpty()) {
            bufferData.sort(Comparator.comparing(SensorData::getMeasurementTime));
            writer.writeBufferedData(bufferData);
        }

    }

    @Override
    public synchronized void onProcessingEnd() {
        flush();
    }
}