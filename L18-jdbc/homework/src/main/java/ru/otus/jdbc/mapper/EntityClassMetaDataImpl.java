package ru.otus.jdbc.mapper;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    Class<T> aClass;
    @Getter(lazy = true)
    List<Field> allFields = receiveAllFields();
    @Getter(lazy = true)
    String name= receiveName();
    @Getter(lazy = true)
    Constructor<T> constructor= receiveConstructor();

    public EntityClassMetaDataImpl(@NonNull Class<T> aClass) {
        this.aClass = aClass;
    }


    public String receiveName() {
        return aClass.getSimpleName();
    }

    @SneakyThrows
    public Constructor<T> receiveConstructor() {
        return aClass.getConstructor();
    }

    public List<Field> receiveAllFields() {
        return List.of(aClass.getDeclaredFields());
    }

    @Override
    public Field getIdField() {
        return getAllFields().stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найдено поле с аннотацией id"));
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
}
