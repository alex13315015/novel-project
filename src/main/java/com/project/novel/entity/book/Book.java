package com.project.novel.entity.book;


import com.project.novel.entity.Member;
import com.project.novel.entity.Subscribe;
import com.project.novel.entity.base.BaseEntity;
import com.project.novel.entity.chapter.Chapter;
import com.project.novel.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "book")
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookLikes> bookLikes = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Subscribe> subscribes = new ArrayList<>();

    public Book(String bookName, String bookIntro, int ageRating, String bookImage, Genre genre, Member member) {
        this.bookName = bookName;
        this.bookIntro = bookIntro;
        this.ageRating = ageRating;
        this.bookImage = bookImage;
        this.genre = genre;
        this.member = member;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
