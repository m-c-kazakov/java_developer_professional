package ru.otus.model;

import lombok.Getter;
import lombok.With;

@Getter
@With
public class User {

    private final Long id;
    private final String name;
    private final String login;
    private final String password;

    public User(Long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String name, String login, String password) {
        this(null, name, login, password);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


}
