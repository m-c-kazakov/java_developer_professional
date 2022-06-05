package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.*;

class SwapPlacesField11AndField12ProcessorTest {

    @Test
    void process() {
        // 2. Сделать процессор, который поменяет местами значения field11 и field12 SwapPlacesField11AndField12Processor
        SwapPlacesField11AndField12Processor swapPlacesField11AndField12Processor = new SwapPlacesField11AndField12Processor();
        String field11 = "field11";
        String field12 = "field12";
        Message message = Message.builder().field11(field11).field12(field12).build();
        assertEquals(field11, message.getField11());
        assertEquals(field12, message.getField12());
        Message message2 = swapPlacesField11AndField12Processor.process(message);
        assertEquals(field12, message2.getField11());
        assertEquals(field11, message2.getField12());

    }
}