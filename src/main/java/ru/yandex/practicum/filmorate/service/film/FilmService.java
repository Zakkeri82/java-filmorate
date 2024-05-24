package ru.yandex.practicum.filmorate.service.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ErrorException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService implements FilmStorage {

    private InMemoryFilmStorage inMemoryFilmStorage;

    private InMemoryUserStorage inMemoryUserStorage;

    @Override
    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @Override
    public Film findFilmId(String id) {
        return inMemoryFilmStorage.findFilmId(id);
    }

    @Override
    public Film create(Film film) {
        inMemoryFilmStorage.create(film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        inMemoryFilmStorage.update(newFilm);
        return newFilm;
    }

    public Collection<Integer> addLike(String filmID, String userID) {
        inMemoryFilmStorage.findFilmId(filmID).getLikes().add(Integer.valueOf(userID));
        log.info("Пользователь {} поставил лайк фильму {}",
                inMemoryUserStorage.findUserId(userID).getName(),
                inMemoryFilmStorage.findFilmId(filmID).getName());
        return inMemoryFilmStorage.findFilmId(filmID).getLikes();
    }

    public Collection<Integer> deleteLike(String filmID, String userID) {
        findFilmId(filmID).getLikes().remove(Integer.valueOf(userID));
        log.info("Пользователь {} убрал лайк у фильма {}",
                inMemoryUserStorage.findUserId(userID).getName(),
                inMemoryFilmStorage.findFilmId(filmID).getName());
        return inMemoryFilmStorage.findFilmId(filmID).getLikes();
    }

    public Collection<Film> getPopularFilms(int count) {
        if (count <= 0) {
            throw new ErrorException("Размер должен быть больше нуля");
        }
        return inMemoryFilmStorage.getAllFilms().stream()
                .filter(film -> film.getLikes() != null)
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
