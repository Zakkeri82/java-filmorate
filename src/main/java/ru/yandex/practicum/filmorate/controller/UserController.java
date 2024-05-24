package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserId(@PathVariable String id) {
        return userService.findUserId(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable String id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getUserMutualFriends(@PathVariable String id,
                                                 @PathVariable String otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Collection<Integer> addUserFriend(@PathVariable String id,
                                             @PathVariable String friendId) {
        return userService.addFriend(id, friendId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return userService.update(newUser);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Collection<Integer> deleteUserFriend(@PathVariable String id,
                                                @PathVariable String friendId) {
        return userService.deleteFriend(id, friendId);
    }
}