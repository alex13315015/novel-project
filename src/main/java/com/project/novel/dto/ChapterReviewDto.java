package com.project.novel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@Builder
public class ChapterReviewDto {

    private Long id;
    private Long memberId;
    private String nickname;
    private String comments;
    private LocalDateTime createdAt;

    public ChapterReviewDto(Long id, Long memberId, String nickname, String comments, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.nickname = nickname;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
