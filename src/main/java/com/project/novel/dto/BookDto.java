package com.project.novel.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class BookDto {

    private Long id;
    private String bookName;
    private String writer;
    private String bookIntro;
    private String bookImage;
    private String bookGenre;
    private Integer ageRating;
    private Integer likeCount;
    private boolean likeState;
    private Integer subscribeCount;
    private boolean subscribeState;
    private List<ChapterDto> chapterList;

}
