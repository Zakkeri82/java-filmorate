package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    private String name;

    @Size(min = 1, max = 200)
    private String description;

    @Past
    private LocalDate releaseDate;

    @Min(1)
    private long duration;
}