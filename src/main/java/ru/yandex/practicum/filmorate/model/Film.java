package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
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