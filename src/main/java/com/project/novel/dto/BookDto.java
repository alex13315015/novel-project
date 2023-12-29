package com.project.novel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookDto {
    private Long id;
    private String bookName;
    private Long subscribes;
    private String author;
    private Long hit;
    private Long likes;
    private LocalDateTime modifiedDate;


    public BookDto(Long id, String bookName, Long subscribes, String author, Long hit, Long likes, LocalDateTime modifiedDate) {
        this.id = id;
        this.bookName = bookName;
        this.subscribes = subscribes;
        this.author = author;
        this.hit = hit;
        this.likes = likes;
        this.modifiedDate = modifiedDate;
    }
}
