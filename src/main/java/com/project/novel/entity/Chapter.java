package com.project.novel.entity;

import com.project.novel.entity.base.BaseEntity;
import com.project.novel.entity.book.Book;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chapter extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "chapter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private String content;

    private Long hits;

    public Chapter(Book book, String title, String content, Long hits) {
        this.book = book;
        this.title = title;
        this.content = content;
        this.hits = hits;
    }
}
