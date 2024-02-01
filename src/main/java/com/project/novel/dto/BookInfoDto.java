package com.project.novel.dto;

import com.project.novel.entity.Book;
import com.project.novel.enums.AgeRating;
import com.project.novel.enums.BookGenre;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@ToString
public class BookInfoDto {
    private Long id;
    private String bookName;
    private BookGenre bookGenre;
    private String author;
    private AgeRating ageRating;
    private String bookIntro;
    private Long subscribes;
    private Long hit;
    private Long likes;
    private String bookImage;
    private LocalDateTime createdAt;

    public BookInfoDto(Book book, Long hit, Long likes, Long subscribes, String author){
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.bookGenre = book.getBookGenre();
        this.bookIntro = book.getBookIntro();
        this.ageRating = book.getAgeRating();
        this.bookImage = book.getBookImage();
        this.createdAt = book.getCreatedAt();
        this.subscribes = subscribes;
        this.hit = hit;
        this.likes = likes;
        this.author = author;
    }
}
