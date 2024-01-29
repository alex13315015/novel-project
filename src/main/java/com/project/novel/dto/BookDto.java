package com.project.novel.dto;

import lombok.*;
import org.springframework.data.domain.Page;


@Getter
@Builder(toBuilder = true)
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
    private Page<ChapterDto> chapterList;

}
