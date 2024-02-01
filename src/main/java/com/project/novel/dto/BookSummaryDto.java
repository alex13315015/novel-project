package com.project.novel.dto;

import com.project.novel.entity.Book;
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
        this.author = book.getMember().getNickname();
    }
}
