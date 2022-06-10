package ru.otus.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.model.User;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactiveUserClientRepository implements UserRepository {
    public static final String DELETE_URL = "delete";
    public static final String CREATE_URL = "create";
    static String URL = "http://localhost:8080/";
    WebClient webClient;

    public ReactiveUserClientRepository(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(URL)
                .build();
    }


    @Override
    public void save(User user) {
        webClient
                .post()
                .uri(CREATE_URL)
                .bodyValue(user)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity()
                .block();
//        restTemplate.postForEntity(CREATE_URL, user, Void.class);
    }

    @Override
    public void delete(User user) {
        webClient
                .post()
                .uri(DELETE_URL)
                .bodyValue(user)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity()
                .block();
//        restTemplate.postForEntity(DELETE_URL, user, Void.class);
    }

    @Override
    public Iterable<User> findAll() {
        return webClient
                .get()
                .uri(URL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User[].class)
                .map(List::of)
                .block();

//        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL, User[].class);
//        User[] body = forEntity.getBody();
//        return List.of(body);
    }
}
