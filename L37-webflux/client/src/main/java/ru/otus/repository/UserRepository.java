package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.model.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    Iterable<User> findAll();

    void delete(User user);
}
