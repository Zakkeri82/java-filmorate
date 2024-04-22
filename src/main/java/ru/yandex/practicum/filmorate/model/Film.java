package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {

    private Integer id;

    @NotEmpty
    private String name;

    @Size(max = 200)
    private String description;

    @Past
    private LocalDate releaseDate;

    private long duration;
}