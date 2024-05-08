package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryFilmStorageTest {

    static InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    Film film;

    @BeforeEach
    void createFilm() {
        film = new Film(
                1,
                "NameFilm",
                "Description",
                LocalDate.of(1994, 10, 14),
                1000
        );
    }

    @Test
    void validFilm() {
        inMemoryFilmStorage.validateFilm(film);
    }

    @Test
    void noValidWithoutNameFilm() {
        film.setName(null);
        Exception exception = assertThrows(
                ValidationException.class,
                () -> inMemoryFilmStorage.validateFilm(film)
        );
        assertEquals(
                "Название не может быть пустым",
                exception.getMessage()
        );
    }

    @Test
    void noValidDescription() {
        film.setDescription("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Exception exception = assertThrows(
                ValidationException.class,
                () -> inMemoryFilmStorage.validateFilm(film)
        );
        assertEquals(
                "Описание не может быть больше 200 символов",
                exception.getMessage()
        );
    }

    @Test
    void noCorrectDateOfRelease() {
        film.setReleaseDate(LocalDate.of(1894, 10, 14));
        Exception exception = assertThrows(
                ValidationException.class,
                () -> inMemoryFilmStorage.validateFilm(film)
        );
        assertEquals(
                "Некорректная дата релиза фильма",
                exception.getMessage()
        );
    }

    @Test
    void noPositiveDuration() {
        film.setDuration(-1L);
        Exception exception = assertThrows(
                ValidationException.class,
                () -> inMemoryFilmStorage.validateFilm(film)
        );
        assertEquals(
                "Продолжительность фильма должна быть положительным числом",
                exception.getMessage()
        );
    }
}