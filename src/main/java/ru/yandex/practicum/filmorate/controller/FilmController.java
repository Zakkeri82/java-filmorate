package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;


@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmId(@PathVariable String id) {
        return inMemoryFilmStorage.findFilmId(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopular(@Valid @RequestBody
                                       @RequestParam(defaultValue = "10") @PathVariable int count) {
        return filmService.getPopularFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return inMemoryFilmStorage.update(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public Collection<Integer> addFilmLike(@PathVariable String id,
                                           @PathVariable String userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Collection<Integer> deleteFilmLike(@PathVariable String id,
                                              @PathVariable String userId) {
        return filmService.deleteLike(id, userId);
    }
}