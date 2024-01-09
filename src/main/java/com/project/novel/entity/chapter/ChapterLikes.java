package com.project.novel.entity.chapter;

import com.project.novel.entity.Member;
import com.project.novel.entity.chapter.Chapter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChapterLikes {

    @Id
    @GeneratedValue
    @Column(name = "chapter_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public ChapterLikes(Member member, Chapter chapter) {
        this.member = member;
        this.chapter = chapter;
    }
}