package ru.otus.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDaoImpl implements UserDao {


    DataTemplate<User> userDataTemplate;
    TransactionManager transactionManager;

    @Override
    public Optional<User> findById(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userOptional = userDataTemplate.findById(session, id);
            log.info("user: {}", userOptional);
            return userOptional;
        });
    }


    @Override
    public Optional<User> findByLogin(String login) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userOptional = userDataTemplate.findByEntityField(session, "login", login);
            log.info("user: {}", userOptional);
            return userOptional.stream().findFirst();
        });
    }

    @Override
    public User saveOrUpdate(User user) {
        return transactionManager.doInTransaction(session -> {
            var clone = user.toBuilder().build();
            if (user.getId() == null) {
                userDataTemplate.insert(session, clone);
                log.info("created user: {}", clone);
                return clone;
            }
            userDataTemplate.update(session, clone);
            log.info("updated user: {}", clone);
            return clone;
        });
    }

    @Override
    public List<User> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userList = userDataTemplate.findAll(session);
            log.info("userList:{}", userList);
            return userList;
        });
    }
}
