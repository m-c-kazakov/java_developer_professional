package ru.otus.jdbc.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    Class<T> aClass;

    @Override
    public String getName() {
        return aClass.getSimpleName();
    }

    @Override
    @SneakyThrows
    public Constructor<T> getConstructor() {
        return aClass.getConstructor();
    }

    @Override
    public Field getIdField() {
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return null;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return null;
    }
}
