package ru.otus.dao;

import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findByLogin(String login);
    User saveOrUpdate(User user);
    List<User> findAll();
}