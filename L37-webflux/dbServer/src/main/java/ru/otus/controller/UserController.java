package ru.otus.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.repository.UserRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserRepository userRepository;

    @PostMapping(value = "/create")
    public String create(@RequestParam(name = "name") String name,
                         @RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         Model model) {
        userService.create(UserDto.builder()
                .name(name)
                .login(login)
                .password(password)
                .build());
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("users", userDtos);
        return "users";
    }

    @PostMapping(value = "/update")
    public String update(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         Model model) {
        userService.update(UserDto.builder()
                .id(id)
                .name(name)
                .login(login)
                .password(password)
                .build());
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("users", userDtos);
        return "users";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         Model model) {
        userService.delete(UserDto.builder()
                .id(id)
                .name(name)
                .login(login)
                .password(password)
                .build());
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("users", userDtos);
        return "users";
    }

    @GetMapping("/")
    public String readAll(Model model) {
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("users", userDtos);
        return "users";
    }
}
