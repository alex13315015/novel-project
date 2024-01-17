package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "chapter_id"}),
        name = "Views",
        indexes = {
        @Index(name = "index_member", columnList = "member_id"),
        @Index(name = "index_chapter", columnList = "chapter_id")
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Views {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @CreatedDate
    private LocalDateTime updatedAt;

    @Builder
    public Views(Member member, Chapter chapter) {
        this.member = member;
        this.chapter = chapter;
    }

    public void updateViews() {
        this.updatedAt = LocalDateTime.now();
    }

}
