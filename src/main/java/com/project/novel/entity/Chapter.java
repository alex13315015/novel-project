package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Chapter {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private String contents;

    private Integer price;

    private Long hits;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

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
    }

    public void incrementHits() {
        this.hits++;
    }

}
