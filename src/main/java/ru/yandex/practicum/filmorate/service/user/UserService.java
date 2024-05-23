package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserStorage {

    private InMemoryUserStorage inMemoryUserStorage;

    @Override
    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @Override
    public User findUserId(String id) {
        return inMemoryUserStorage.findUserId(id);
    }

    @Override
    public User create(User user) {
        inMemoryUserStorage.create(user);
        return user;
    }

    @Override
    public User update(User newUser) {
        inMemoryUserStorage.update(newUser);
        return newUser;
    }

    public Collection<User> getUserFriends(String userID) {
        return findUserId(userID).getFriends().stream()
                .map(id -> findUserId(String.valueOf(id)))
                .collect(Collectors.toList());
    }

    public Collection<Integer> addFriend(String userID, String friendID) {
        isPresentUser(userID, friendID);
        findUserId(userID).getFriends().add(Integer.valueOf(friendID));
        findUserId(friendID).getFriends().add(Integer.valueOf(userID));
        log.info("Пользователь {} добавил в друзья пользователя {}",
                findUserId(userID).getName(),
                findUserId(friendID).getName());
        return findUserId(userID).getFriends();
    }

    public Collection<Integer> deleteFriend(String userID, String friendID) {
        isPresentUser(userID, friendID);
        findUserId(userID).getFriends().remove(Integer.valueOf(friendID));
        findUserId(friendID).getFriends().remove(Integer.valueOf(userID));
        log.info("Пользователь {} удалил пользователя {} из друзей",
                findUserId(userID).getName(),
                findUserId(friendID).getName());
        return findUserId(userID).getFriends();
    }


    public Collection<User> getMutualFriends(String userID, String otherID) {
        isPresentUser(userID, otherID);
        return findUserId(userID).getFriends()
                .stream()
                .filter(findUserId(otherID).getFriends()::contains)
                .map(id -> findUserId(String.valueOf(id)))
                .collect(Collectors.toList());
    }

    private void isPresentUser(String userID, String friendID) {
        if (findUserId(userID) == null) {
            throw new NotFoundException("Пользователь с id = " + userID + " не найден");
        }
        if (findUserId(friendID) == null) {
            throw new NotFoundException("Пользователь с id = " + friendID + " не найден");
        }
    }
}
