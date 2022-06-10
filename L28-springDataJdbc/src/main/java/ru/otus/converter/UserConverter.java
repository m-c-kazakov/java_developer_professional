package ru.otus.converter;

import ru.otus.dto.UserDto;
import ru.otus.model.User;

public interface UserConverter {
    User convertToUser(UserDto userDto);

    UserDto convertToDto(User user);
}
