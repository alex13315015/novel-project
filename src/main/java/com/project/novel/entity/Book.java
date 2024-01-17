package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String bookName;

    private String bookImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String bookIntro;

    private String bookGenre;

    private Integer ageRating;

    @OneToMany(mappedBy = "book")
    private List<Chapter> chapterList;

    @OneToMany(mappedBy = "book")
    private List<BookLikes> bookLikesList;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Book(String bookName, String bookImage, Member member, String bookIntro, String bookGenre, Integer ageRating) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.member = member;
        this.bookIntro = bookIntro;
        this.bookGenre = bookGenre;
        this.ageRating = ageRating;
    }

}
