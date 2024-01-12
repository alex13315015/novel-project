package com.project.novel.entity.book;


import com.project.novel.entity.Member;
import com.project.novel.entity.base.BaseEntity;
import com.project.novel.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(of = {"id", "bookName", "member"})
public class Book extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String bookName;

    private String bookIntro;

    private int ageRating;

    private String bookImage;

    private Genre genre;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Book(String bookName, String bookIntro, int ageRating, String bookImage, Genre genre, Member member) {
        this.bookName = bookName;
        this.bookIntro = bookIntro;
        this.ageRating = ageRating;
        this.bookImage = bookImage;
        this.genre = genre;
        this.member = member;
    }
}
