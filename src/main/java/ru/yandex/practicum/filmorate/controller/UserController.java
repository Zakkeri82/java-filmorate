package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public User createNewUser(@RequestBody User user) {
        if (user.getEmail() == null) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        boolean isExist = users.values()
                .stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()));
        if (isExist) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        boolean isExist = users.values()
                .stream().anyMatch(user1 -> user1.getEmail().equals(newUser.getEmail()));
        if (isExist) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        User oldUser = users.get(newUser.getId());
        if (users.containsKey(newUser.getId())) {
            if (newUser.getEmail() != null) {
                oldUser.setEmail(newUser.getEmail());
            }
            if (newUser.getUsername() != null) {
                oldUser.setUsername(newUser.getUsername());
            }
            if (newUser.getPassword() != null) {
                oldUser.setPassword(newUser.getPassword());
            }
            users.put(oldUser.getId(), oldUser);
        }
        return oldUser;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
