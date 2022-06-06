package ru.otus.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.model.User;

import java.util.List;

//@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserClientRepositoryImpl implements UserRepository {

    static String URL = "http://localhost:8080/";
    public static final String DELETE_URL = URL + "delete";
    public static final String CREATE_URL = URL + "create";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void save(User user) {
        restTemplate.postForEntity(CREATE_URL, user, Void.class);
    }

    @Override
    public void delete(User user) {
        restTemplate.postForEntity(DELETE_URL, user, Void.class);
    }

    @Override
    public Iterable<User> findAll() {
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL, User[].class);
        User[] body = forEntity.getBody();
        return List.of(body);
    }
}
