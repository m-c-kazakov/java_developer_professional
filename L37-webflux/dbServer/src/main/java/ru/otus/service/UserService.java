package ru.otus.service;

import ru.otus.dto.UserDto;

import java.util.List;

public interface UserService {

    void create(UserDto userDto);

    void update(UserDto userDto);

    void delete(UserDto userDto);

    List<UserDto> getAll();



}
