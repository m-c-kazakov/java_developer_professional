package ru.otus.jdbc.mapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityClassMetaDataImplTest {

    EntityClassMetaData<Client> objectEntityClassMetaData;

    @BeforeEach
    void setUp() {
        objectEntityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);
    }

    @Test
    void getName() {
        assertEquals("Client", objectEntityClassMetaData.getName());
    }

    @Test
    @SneakyThrows
    void getConstructor() {
        assertEquals(Client.class.getConstructor(), objectEntityClassMetaData.getConstructor());
    }

    @Test
    void getIdField() {
    }

    @Test
    void getAllFields() {

    }

    @Test
    void getFieldsWithoutId() {
    }
}