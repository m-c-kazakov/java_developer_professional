package ru.otus.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Сохратяет объект в базу, читает объект из базы
 */

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    DbExecutor dbExecutor;
    EntitySQLMetaData<T> sqlQuery;
    EntityClassMetaData<T> classMetaData;

    MyCache<Long, T> myCache = new MyCache<>();

    @Override
    public Optional<T> findById(Connection connection, long id) {

        T cacheResult = myCache.get(id);
        if (Objects.nonNull(cacheResult)) {
            return Optional.ofNullable(cacheResult);
        }

        return dbExecutor.executeSelect(
                connection,
                sqlQuery.getSelectByIdSql(),
                List.of(id),
                resultSet -> classMetaData.initializingAnObject(resultSet).stream().findFirst()
                        .map(result -> {
                            myCache.put(id, result);
                            return result;
                        })
                        .orElse(null)
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
