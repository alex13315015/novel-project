package com.project.novel.dto.book;

import com.project.novel.enums.Genre;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@ToString
public class BookDto {
    private Long id;
    private String bookName;
    private Genre genre;
    private String author;
    private Integer ageRating;
    private String bookIntro;
    private Long subscribes;
    private Long hit;
    private Long likes;
    private String bookImage;
    private LocalDateTime createdDate;

    public BookDto(Long id, String bookName, Genre genre, String author, Integer ageRating, String bookIntro, Long subscribes, Long hit, Long likes, String bookImage, LocalDateTime createdDate) {
        this.id = id;
        this.bookName = bookName;
        this.genre = genre;
        this.author = author;
        this.ageRating = ageRating;
        this.bookIntro = bookIntro;
        this.subscribes = subscribes;
        this.hit = hit;
        this.likes = likes;
        this.bookImage = bookImage;
        this.createdDate = createdDate;
    }
}
