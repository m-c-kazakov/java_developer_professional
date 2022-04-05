package ru.otus.model;

import lombok.*;

@Getter
@ToString
@Builder(toBuilder = true)
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id" )
public class Message {
    private final long id;
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final String field11;
    private final String field12;
    private final ObjectForMessage field13;

    //todo: 1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
}
