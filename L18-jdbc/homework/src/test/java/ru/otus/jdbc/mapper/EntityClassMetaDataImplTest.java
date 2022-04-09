package ru.otus.jdbc.mapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.annotations.Id;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Field idField = objectEntityClassMetaData.getIdField();
        assertNotNull(idField);
        assertEquals("id", idField.getName());
    }

    @Test
    void getAllFields() {
        List<Field> allFields = objectEntityClassMetaData.getAllFields();
        assertFalse(allFields.isEmpty());
    }

    @Test
    void getFieldsWithoutId() {
        List<Field> fieldsWithoutId = objectEntityClassMetaData.getFieldsWithoutId();
        assertFalse(fieldsWithoutId.isEmpty());
        assertTrue(fieldsWithoutId.stream().noneMatch(field -> field.isAnnotationPresent(Id.class)));
    }
}