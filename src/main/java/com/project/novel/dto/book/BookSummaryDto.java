package com.project.novel.dto.book;

import com.project.novel.entity.book.Book;
import com.project.novel.enums.AgeRating;
import com.project.novel.enums.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookSummaryDto {
    private Long id;
    private String bookName;
    private String bookImage;
    private String author;

    public BookSummaryDto(Book book){
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.bookImage = book.getBookImage();
        this.author = book.getMember().getName();
    }
}
