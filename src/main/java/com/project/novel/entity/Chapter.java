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
public class Chapter extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "chapter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Size(min = 1, max = 30, message = "1자 이상 30자 이하로 입력해주세요.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Lob
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    private Long hits;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    private List<ChapterReview> chapterReviewList;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    private List<Views> viewsList;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean isActive;

    @Builder
    public Chapter(Book book, String title, String contents, Integer price, Long hits) {
        this.book = book;
        this.title = title;
        this.contents = contents;
        this.price = price;
        if(hits == null) {
            hits=0L;
        }
        this.hits = hits;
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void update(String title, String contents, int price) {
        this.title = title;
        this.contents = contents;
        this.price = price;
    }
}
