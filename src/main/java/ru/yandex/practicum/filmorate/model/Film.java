package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Film.
 */
@Data
public class Film {

    private int id;

    private String name;

    private String description;

    private LocalDateTime releaseDate;

    private Duration duration;
}