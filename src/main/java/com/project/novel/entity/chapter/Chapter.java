package com.project.novel.entity.chapter;

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
@ToString(of = {"id", "title", "content", "price"})
public class Chapter extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "chapter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private String content;

    private int price;

    private Long hits;

    public Chapter(Book book, String title, String content, int price, Long hits) {
        this.book = book;
        this.title = title;
        this.content = content;
        this.price = price;
        this.hits = hits;
    }
}
