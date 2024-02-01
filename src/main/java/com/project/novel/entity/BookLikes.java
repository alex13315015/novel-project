package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookLikes {

    @Id @GeneratedValue
    @Column(name = "book_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookLikes(Member member, Book book) {
        this.member = member;
        this.book = book;
    }
}
