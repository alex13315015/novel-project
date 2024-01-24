package com.project.novel.dto;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
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
