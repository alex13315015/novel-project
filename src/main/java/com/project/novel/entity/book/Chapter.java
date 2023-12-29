package com.project.novel.entity.book;

import com.project.novel.entity.base.BaseEntity;
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

    private String title;

    private String content;

    private Long hit;

    private Long likes;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


    public Chapter(String title, String content, Long hit, Long likes, int price, Book book) {
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.likes = likes;
        this.price = price;
        this.book = book;
    }
}
