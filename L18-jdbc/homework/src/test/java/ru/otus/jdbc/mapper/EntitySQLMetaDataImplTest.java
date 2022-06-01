package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import static org.junit.jupiter.api.Assertions.*;

class EntitySQLMetaDataImplTest {

    EntitySQLMetaDataImpl<Client> entitySQLMetaDataClient;
    EntityClassMetaData<Client> entityClassMetaDataClient;

    EntitySQLMetaDataImpl<Manager> entitySQLMetaDataManager;
    EntityClassMetaData<Manager> entityClassMetaDataManager;

    @BeforeEach
    void setUp() {
        entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);

        entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
        entitySQLMetaDataManager = new EntitySQLMetaDataImpl<>(entityClassMetaDataManager);
    }

    @Test
    void getSelectAllSql() {
        assertEquals("SELECT * FROM Client", entitySQLMetaDataClient.getSelectAllSql());
        assertEquals("SELECT * FROM Manager", entitySQLMetaDataManager.getSelectAllSql());
    }

    @Test
    void getSelectByIdSql() {
        assertEquals("SELECT * FROM Client WHERE id=?", entitySQLMetaDataClient.getSelectByIdSql());
        assertEquals("SELECT * FROM Manager WHERE no=?", entitySQLMetaDataManager.getSelectByIdSql());
    }

    @Test
    void getInsertSql() {
        assertEquals("INSERT INTO Client(name) VALUES(?)", entitySQLMetaDataClient.getInsertSql(new Client("NAME")));
        assertEquals("INSERT INTO Manager(label) VALUES(?)", entitySQLMetaDataManager.getInsertSql(new Manager(null, "Manager", null)));
        assertEquals("INSERT INTO Manager(label,param1) VALUES(?,?)", entitySQLMetaDataManager.getInsertSql(new Manager(null, "Manager", "PARAM2")));

    }

    @Test
    void getUpdateSql() {
        assertEquals("UPDATE Client SET name=? WHERE id=?", entitySQLMetaDataClient.getUpdateSql());
        assertEquals("UPDATE Manager SET label=?, param1=? WHERE no=?", entitySQLMetaDataManager.getUpdateSql());
    }
}