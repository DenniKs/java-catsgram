package ru.yandex.practicum.catsgram.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class Post {

    @Setter
    private Integer id;
    private final String author; // автор
    private final Instant creationDate = Instant.now(); // дата создания
    @Setter
    private String description; // описание
    @Setter
    private String photoUrl; // url-адрес фотографии

    public Post(String author, String description, String photoUrl) {
        this.author = author;
        this.description = description;
        this.photoUrl = photoUrl;
    }
}