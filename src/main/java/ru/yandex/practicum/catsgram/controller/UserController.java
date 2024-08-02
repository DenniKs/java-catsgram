package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @GetMapping
    public Map<Long, User> getUsers() {
        return users;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (users.values().stream().anyMatch(existingUser -> existingUser.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.from(LocalDateTime.now()));
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        User existingUser = users.get(user.getId());
        if (existingUser == null) {
            throw new ConditionsNotMetException("Пользователь с таким ID не найден");
        }
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail()) &&
                users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        users.put(existingUser.getId(), existingUser);
        return existingUser;
    }

    private Long getNextId() {
        return counter.incrementAndGet();
    }
}
