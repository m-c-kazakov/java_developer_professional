package ru.otus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User {

    @Id
    private Long id;
    private String name;
    private String login;
    private String password;
}
