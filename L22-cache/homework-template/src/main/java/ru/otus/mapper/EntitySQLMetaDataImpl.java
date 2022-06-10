package ru.otus.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    EntityClassMetaData<T> entityClassMetaData;

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM %s".formatted(entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM %s WHERE %s=?".formatted(entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql(T object) {
        return "INSERT INTO %s(%s) VALUES(%s)".formatted(
                entityClassMetaData.getName(),
                entityClassMetaData.getFieldsWithoutId().stream().filter(field -> {
                            field.setAccessible(true);
                            try {
                                return Objects.nonNull(field.get(object));
                            } catch (IllegalAccessException ignored) {
                                return false;
                            }
                        }).map(Field::getName)
                        .collect(Collectors.joining(",")),
                entityClassMetaData.getFieldsWithoutId().stream().filter(field -> {
                            field.setAccessible(true);
                            try {
                                return Objects.nonNull(field.get(object));
                            } catch (IllegalAccessException ignored) {
                                return false;
                            }
                        }).map(field -> "?")
                        .collect(Collectors.joining(",")));
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE %s SET %s WHERE %s=?".formatted(
                entityClassMetaData.getName(),
                entityClassMetaData.getFieldsWithoutId().stream().map(field -> field.getName() + "=?")
                        .collect(Collectors.joining(", ")),
                entityClassMetaData.getIdField().getName());
    }
}
