package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    static UserService userService = new UserService();

    User user;

    @BeforeEach
    void createUser() {
        user = new User(
                1,
                "pavlik@pochta.ru",
                "login",
                "Pasha",
                LocalDate.of(1982, 3, 27)
        );
    }

    @Test
    void validUser() {
        userService.validateUser(user);
    }

    @Test
    void noValidEmailIsEmpty() {
        user.setEmail("");
        Exception exception = assertThrows(
                ValidationException.class,
                () -> userService.validateUser(user)
        );
        assertEquals(
                "Электронная почта не может быть пустой и должна содержать символ '@'",
                exception.getMessage()
        );
    }

    @Test
    void noValidEmailWithoutDog() {
        user.setEmail("pochta.ru");
        Exception exception = assertThrows(
                ValidationException.class,
                () -> userService.validateUser(user)
        );
        assertEquals(
                "Электронная почта не может быть пустой и должна содержать символ '@'",
                exception.getMessage()
        );
    }

    @Test
    void noValidLoginIsEmpty() {
        user.setLogin("");
        Exception exception = assertThrows(
                ValidationException.class,
                () -> userService.validateUser(user)
        );
        assertEquals(
                "Логин не может быть пустым и содержать пробелы",
                exception.getMessage()
        );
    }

    @Test
    void noValidLoginWithSpace() {
        user.setLogin("login space");
        Exception exception = assertThrows(
                ValidationException.class,
                () -> userService.validateUser(user)
        );
        assertEquals(
                "Логин не может быть пустым и содержать пробелы",
                exception.getMessage()
        );
    }

    @Test
    void noValidDateOfBirthday() {
        user.setBirthday(LocalDate.of(2100, 2, 20));
        Exception exception = assertThrows(
                ValidationException.class,
                () -> userService.validateUser(user)
        );
        assertEquals(
                "Некорректная дата рождения",
                exception.getMessage()
        );
    }
}