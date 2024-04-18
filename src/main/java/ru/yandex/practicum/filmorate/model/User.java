package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

/**
 * User.
 */
@Data
public class User {

    private Integer id;

    @Email
    private String email;

    @NotEmpty
    private String login;

    private String name;

    @Past
    private LocalDate birthday;
}