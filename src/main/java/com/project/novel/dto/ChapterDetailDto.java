package com.project.novel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class ChapterDetailDto {

    private Long bookId;
    private Long chapterId;
    private String title;
    private String contents;
    private Long hits;
    private String createdAt;

}
