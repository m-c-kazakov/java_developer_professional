package ru.otus.jdbc.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    EntityClassMetaData<T> entityClassMetaDataClient;

    @Override
    public String getSelectAllSql() {
        return null;
    }

    @Override
    public String getSelectByIdSql() {
        return null;
    }

    @Override
    public String getInsertSql() {
        return null;
    }

    @Override
    public String getUpdateSql() {
        return null;
    }
}
