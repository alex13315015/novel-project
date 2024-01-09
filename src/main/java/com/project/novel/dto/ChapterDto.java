package com.project.novel.dto;

import com.project.novel.entity.Member;
import com.project.novel.entity.book.Book;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ChapterDto {
    private Long bookId;

//    private Long id;
//    private String title;
//    private String content;
//    private Long hit;
//    private Long likes;
//    private int price;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id")
//    private Book book;

}
