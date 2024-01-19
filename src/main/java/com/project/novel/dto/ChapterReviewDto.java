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
    private String nickname;
    private String comments;
    private LocalDateTime createdAt;

    public ChapterReviewDto(Long id, String nickname, String comments, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
