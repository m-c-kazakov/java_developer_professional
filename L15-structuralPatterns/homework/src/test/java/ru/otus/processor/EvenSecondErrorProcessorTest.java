package ru.otus.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvenSecondErrorProcessorTest {

    @Mock
    private DateTimeProvider dateTimeProvider;

    @Test
    void process() {
        when(dateTimeProvider.getNowSecond()).thenReturn(1);
        Message message = Message.builder().build();
        EvenSecondErrorProcessor evenSecondErrorProcessor = new EvenSecondErrorProcessor(dateTimeProvider);
        assertEquals(message, evenSecondErrorProcessor.process(message));
    }

    @Test
    void process$WithThrow() {
        when(dateTimeProvider.getNowSecond()).thenReturn(2);
        EvenSecondErrorProcessor evenSecondErrorProcessor = new EvenSecondErrorProcessor(dateTimeProvider);
        assertThrows(RuntimeException.class, ()-> evenSecondErrorProcessor.process(Message.builder().build()));
    }
}