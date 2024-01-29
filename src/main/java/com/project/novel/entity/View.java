package com.project.novel.entity;

import com.project.novel.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class View extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "view_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public View(Member member, Chapter chapter) {
        this.member = member;
        this.chapter = chapter;
    }
}
