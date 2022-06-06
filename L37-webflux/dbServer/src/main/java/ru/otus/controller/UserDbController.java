package ru.otus.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.otus.dto.UserDto;
import ru.otus.service.UserService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDbController {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    UserService userService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<UserDto>> create(@RequestBody UserDto userDto) {

        return Mono.fromFuture(() -> CompletableFuture.supplyAsync(() -> {
                    userService.create(userDto);
                    return userService.getAll();
                }, executorService))
                .doOnNext(userDtos -> log.info("Возвращаем на клиент результат={}", userDtos));
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<UserDto>> update(@RequestBody UserDto userDto) {
        return Mono.fromFuture(() -> CompletableFuture.supplyAsync(() -> {
                    userService.update(userDto);
                    return userService.getAll();
                }, executorService))
                .doOnNext(userDtos -> log.info("Возвращаем на клиент результат={}", userDtos));
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<UserDto>> delete(@RequestBody UserDto userDto) {
        return Mono.fromFuture(() -> CompletableFuture.supplyAsync(() -> {
                    userService.delete(userDto);
                    return userService.getAll();
                }, executorService))
                .doOnNext(userDtos -> log.info("Возвращаем на клиент результат={}", userDtos));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<UserDto>> readAll() {
        return Mono.fromFuture(() -> CompletableFuture.supplyAsync(userService::getAll, executorService))
                .doOnNext(userDtos -> log.info("Возвращаем на клиент результат={}", userDtos));

    }
}
