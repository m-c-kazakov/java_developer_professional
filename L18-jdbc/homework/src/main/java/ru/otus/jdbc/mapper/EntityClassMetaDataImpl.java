package ru.otus.jdbc.mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    Class<T> aClass;
    @Getter(lazy = true)
    List<Field> allFields = receiveAllFields();
    @Getter(lazy = true)
    String name = receiveName();
    @Getter(lazy = true)
    Constructor<T> constructor = receiveConstructor();
    @Getter(lazy = true)
    List<Method> methods = receiveAllMethods();

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

    public List<Method> receiveAllMethods() {
        return List.of(aClass.getMethods());
    }

    @Override
    public Field getIdField() {
        return getAllFields().stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найдено поле с аннотацией id"));
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream().filter(field -> !field.isAnnotationPresent(Id.class)).toList();
    }

    @Override
    @SneakyThrows
    public List<T> initializingAnObject(ResultSet resultSet) {
        List<T> objects = new ArrayList<>();
        while (resultSet.next()) {
            T object = getConstructor().newInstance();
            objects.add(object);
            for (Field field : getAllFields()) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }

        }
        return objects;
    }

    @Override
    public List<Object> getFieldsValue(T object) {
        return getAllFields().stream()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Unexpected error");
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
