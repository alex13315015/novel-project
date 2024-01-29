package com.project.novel.dto.book;

import com.project.novel.entity.book.Book;
import com.project.novel.enums.AgeRating;
import com.project.novel.enums.Genre;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@ToString
public class BookInfoDto {
    private Long id;
    private String bookName;
    private Genre genre;
    private String author;
    private AgeRating ageRating;
    private String bookIntro;
    private Long subscribes;
    private Long hit;
    private Long likes;
    private String bookImage;
    private LocalDateTime createdDate;

    public BookInfoDto(Book book, Long hit, Long likes, Long subscribes, String author){
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.genre = book.getGenre();
        this.bookIntro = book.getBookIntro();
        this.ageRating = book.getAgeRating();
        this.bookImage = book.getBookImage();
        this.createdDate = book.getCreatedDate();
        this.subscribes = subscribes;
        this.hit = hit;
        this.likes = likes;
        this.author = author;
    }
}
