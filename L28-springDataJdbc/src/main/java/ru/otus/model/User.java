package ru.otus.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Getter
@Builder
@RequiredArgsConstructor
@Table("user")
public class User {

    @Id
    private final Long id;
    private final String name;
    private final String login;
    private final String password;
}
