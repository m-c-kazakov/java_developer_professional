package ru.otus.jdbc.mapper;

import lombok.RequiredArgsConstructor;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@RequiredArgsConstructor
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> sqlQuery;
    private final EntityClassMetaData<T> classMetaData;

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                sqlQuery.getSelectByIdSql(),
                List.of(id),
                resultSet -> classMetaData.initializingAnObject(resultSet).stream().findFirst().orElse(null)
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                sqlQuery.getSelectAllSql(),
                List.of(),
                classMetaData::initializingAnObject
        ).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(
                connection,
                sqlQuery.getInsertSql(object),
                classMetaData.getFieldsValue(object));
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(
                connection,
                sqlQuery.getUpdateSql(),
                classMetaData.getFieldsValue(object));
    }
}
