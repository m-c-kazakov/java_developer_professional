package ru.otus.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.UserDto;
import ru.otus.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDbController {

    UserService userService;

    @PostMapping(value = "/create")
    public List<UserDto> create(@RequestBody UserDto userDto) {
        userService.create(userDto);
        return userService.getAll();
    }

    @PostMapping(value = "/update")
    public List<UserDto> update(@RequestBody UserDto userDto) {
        userService.update(userDto);
        return userService.getAll();
    }

    @PostMapping(value = "/delete")
    public List<UserDto> delete(@RequestBody UserDto userDto) {
        userService.delete(userDto);
        return userService.getAll();
    }
    @GetMapping("/")
    public List<UserDto> readAll() {
        return userService.getAll();
    }
}
