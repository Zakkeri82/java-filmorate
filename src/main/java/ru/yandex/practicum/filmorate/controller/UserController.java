package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;

    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserId(@PathVariable String id) {
        return inMemoryUserStorage.findUserId(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<Integer> getUserFriends(@PathVariable String id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<Integer> getUserMutualFriends(@PathVariable String id,
                                                    @PathVariable String otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Collection<Integer> addUserFriend(@Valid @RequestBody @PathVariable String id,
                                             @PathVariable String friendId) {
        return userService.addFriend(id, friendId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return inMemoryUserStorage.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return inMemoryUserStorage.update(newUser);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Collection<Integer> deleteUserFriend(@Valid @RequestBody @PathVariable String id,
                                                @PathVariable String friendId) {
        return userService.deleteFriend(id, friendId);
    }
}