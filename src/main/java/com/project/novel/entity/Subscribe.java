package com.project.novel.entity;

import com.project.novel.entity.book.Book;
import jakarta.persistence.*;


@Entity
public class Subscribe {

    @Id @GeneratedValue
    @Column(name = "subscribe_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
