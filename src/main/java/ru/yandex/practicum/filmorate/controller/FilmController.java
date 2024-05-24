package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.Collection;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmId(@PathVariable String id) {
        return filmService.findFilmId(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopular(@Valid @RequestBody
                                       @RequestParam(defaultValue = "10") @PathVariable int count) {
        return filmService.getPopularFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmService.update(newFilm);
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