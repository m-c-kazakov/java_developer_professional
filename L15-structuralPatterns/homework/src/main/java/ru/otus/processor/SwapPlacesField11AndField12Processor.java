package ru.otus.processor;

import ru.otus.model.Message;

public class SwapPlacesField11AndField12Processor implements Processor {
    @Override
    public Message process(Message message) {
        //todo: 2. Сделать процессор, который поменяет местами значения field11 и field12 SwapPlacesField11AndField12Processor
        return message.toBuilder().field11(message.getField11()).field12(message.getField12()).build();
    }
}
