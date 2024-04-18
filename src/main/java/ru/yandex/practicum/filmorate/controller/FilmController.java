package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        boolean isNoValidRelease = film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
        if (film.getName() == null) {
            log.error("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.error("Описание не может быть больше 200 символов");
            throw new ValidationException("Описание не может быть больше 200 символов");
        } else if (isNoValidRelease) {
            log.error("Некорректная дата релиза фильма");
            throw new ValidationException("Некорректная дата релиза фильма");
        } else if (film.getDuration() < 0) {
            log.error("Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Добавлен фильм " + film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            boolean isNoValidRelease = newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
            if (newFilm.getName() == null) {
                log.error("Название не может быть пустым");
                throw new ValidationException("Название не может быть пустым");
            } else if (newFilm.getDescription().length() > 200) {
                log.error("Описание не может быть больше 200 символов");
                throw new ValidationException("Описание не может быть больше 200 символов");
            } else if (isNoValidRelease) {
                log.error("Некорректная дата релиза фильма");
                throw new ValidationException("Некорректная дата релиза фильма");
            } else if (newFilm.getDuration() < 0) {
                log.error("Продолжительность фильма должна быть положительным числом");
                throw new ValidationException("Продолжительность фильма должна быть положительным числом");
            }
            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            films.put(oldFilm.getId(), oldFilm);
            log.info("Изменен фильм " + oldFilm);
            return oldFilm;
        }
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}