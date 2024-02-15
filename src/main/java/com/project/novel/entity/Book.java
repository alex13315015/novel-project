package com.project.novel.entity;

import com.project.novel.enums.BookGenre;
import com.project.novel.enums.AgeRating;
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
public class Book extends BaseEntity{

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

    @Enumerated(EnumType.STRING)
    @NotNull(message = "책 장르를 선택해주세요.")
    private BookGenre bookGenre;

    @NotNull(message = "연령 등급을 선택해주세요.")
    private AgeRating ageRating;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Chapter> chapterList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookLikes> bookLikesList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Subscribe> subscribeList;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean isActive;

    @Builder
    public Book(String bookName, String bookImage, Member member, String bookIntro, BookGenre bookGenre, AgeRating ageRating) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.member = member;
        this.bookIntro = bookIntro;
        this.bookGenre = bookGenre;
        this.ageRating = ageRating;
        this.isActive = true;
    }
    public void deactivate() {
        this.isActive = false;
    }

    // 책 정보 수정
    public void update(String bookName, String bookImage, String bookIntro, BookGenre bookGenre, AgeRating ageRating) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookIntro = bookIntro;
        this.bookGenre = bookGenre;
        this.ageRating = ageRating;
    }

    public void chapterUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
}
