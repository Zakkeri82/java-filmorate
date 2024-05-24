package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        return inMemoryUserStorage.findUserId(userID).getFriends().stream()
                .map(id -> findUserId(String.valueOf(id)))
                .collect(Collectors.toList());
    }

    public Collection<Integer> addFriend(String userID, String friendID) {
        inMemoryUserStorage.findUserId(userID).getFriends().add(Integer.valueOf(friendID));
        inMemoryUserStorage.findUserId(friendID).getFriends().add(Integer.valueOf(userID));
        log.info("Пользователь {} добавил в друзья пользователя {}",
                inMemoryUserStorage.findUserId(userID).getName(),
                inMemoryUserStorage.findUserId(friendID).getName());
        return inMemoryUserStorage.findUserId(userID).getFriends();
    }

    public Collection<Integer> deleteFriend(String userID, String friendID) {
        inMemoryUserStorage.findUserId(userID).getFriends().remove(Integer.valueOf(friendID));
        inMemoryUserStorage.findUserId(friendID).getFriends().remove(Integer.valueOf(userID));
        log.info("Пользователь {} удалил пользователя {} из друзей",
                inMemoryUserStorage.findUserId(userID).getName(),
                inMemoryUserStorage.findUserId(friendID).getName());
        return inMemoryUserStorage.findUserId(userID).getFriends();
    }


    public Collection<User> getMutualFriends(String userID, String otherID) {
        return inMemoryUserStorage.findUserId(userID).getFriends()
                .stream()
                .filter(findUserId(otherID).getFriends()::contains)
                .map(id -> findUserId(String.valueOf(id)))
                .collect(Collectors.toList());
    }
}
