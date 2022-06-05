package ru.otus.processor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.model.Message;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EvenSecondErrorProcessor implements Processor {

    DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message) {
        //3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
        //         Секунда должна определяьться во время выполнения.
        //         Тест - важная часть задания
        // Обязательно посмотрите пример к паттерну Мементо!

        if (dateTimeProvider.getNowSecond() % 2 == 0) {
            throw new RuntimeException("ПАЛУНДРА!!! Четная секунда!!!");
        }
        return message;
    }
}
