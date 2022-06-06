package ru.otus.converter;

import org.springframework.stereotype.Component;
import ru.otus.dto.UserDto;
import ru.otus.model.User;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public User convertToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
    }

    @Override
    public UserDto convertToDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
