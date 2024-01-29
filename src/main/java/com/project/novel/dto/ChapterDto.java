package com.project.novel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@ToString
public class ChapterDto {

    private Long id;
    private String title;
    private String createdAt;
    private Long hits;

    @Builder
    public ChapterDto(Long id, String title, LocalDateTime createdAt, Long hits) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.id = id;
        this.title = title;
        this.createdAt = createdAt.format(formatter);
        this.hits = hits;
    }

}
