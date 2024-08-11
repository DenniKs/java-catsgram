package ru.yandex.practicum.catsgram.service;

public class ConditionsNotMetException extends RuntimeException {
    public ConditionsNotMetException(String s) {
        super(s);
    }
}
