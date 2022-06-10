package ru.otus.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.converter.UserConverter;
import ru.otus.dto.UserDto;
import ru.otus.model.User;
import ru.otus.repository.UserRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    TransactionManager transactionManager;
    UserConverter userConverter;


    @Override
    public void create(UserDto userDto) {
        User user = userConverter.convertToUser(userDto);
        transactionManager.doInTransaction(() -> userRepository.save(user));
    }

    @Override
    public void update(UserDto userDto) {
        User user = userConverter.convertToUser(userDto);
        transactionManager.doInTransaction(() -> userRepository.save(user));
    }

    @Override
    public void delete(UserDto userDto) {
        User user = userConverter.convertToUser(userDto);
        transactionManager.doInTransaction(() -> userRepository.delete(user));
    }

    @Override
    public List<UserDto> getAll() {
        Iterable<User> allUsers = userRepository.findAll();
        return StreamSupport.stream(allUsers.spliterator(), false).map(userConverter::convertToDto).toList();
    }
}
