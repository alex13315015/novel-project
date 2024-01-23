package com.project.novel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(name = "book_id")
    private Long id;

    @Size(min = 1, max = 30, message = "1자 이상 30자 이하로 입력해주세요.")
    @NotBlank(message = "책 제목을 입력해주세요.")
    private String bookName;

    private String bookImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    @Size(min = 1, max = 800, message = "1자 이상 800자 이하로 입력해주세요.")
    @NotBlank(message = "책 소개를 입력해주세요.")
    private String bookIntro;

    @NotBlank(message = "책 장르를 선택해주세요.")
    private String bookGenre;

    @NotNull(message = "연령 등급을 선택해주세요.")
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