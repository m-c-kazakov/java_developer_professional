package ru.otus.jdbc.mapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.annotations.Id;
import ru.otus.crm.model.Client;
import ru.otus.mapper.EntityClassMetaData;
import ru.otus.mapper.EntityClassMetaDataImpl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityClassMetaDataImplTest {

    EntityClassMetaData<Client> objectEntityClassMetaData;

    @Mock
    ResultSet resultSet;

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

    @Test
    @SneakyThrows
    void initializingAnObject() {

        long id = 123L;
        String name = "NAME";
        when(resultSet.getObject("id")).thenReturn(id);
        when(resultSet.getObject("name")).thenReturn(name);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        List<Client> clients = objectEntityClassMetaData.initializingAnObject(resultSet);
        assertFalse(clients.isEmpty());
        assertEquals(1, clients.size());
        Client client = clients.get(0);
        assertEquals(id, client.getId());
        assertEquals(name, client.getName());
    }

    @Test
    void getFieldsValue() {
        long id = 123L;
        String name = "NAME";
        Client client = new Client(id, name);
        List<Object> fieldsValue = objectEntityClassMetaData.getFieldsValue(client);
        assertFalse(fieldsValue.isEmpty());
        assertEquals(2, fieldsValue.size());

        assertEquals(id, (Long) fieldsValue.get(0));
        assertEquals(name, (String) fieldsValue.get(1));

    }
}