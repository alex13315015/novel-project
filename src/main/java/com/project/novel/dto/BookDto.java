package com.project.novel.dto;

import com.project.novel.enums.AgeRating;
import com.project.novel.enums.Genre;
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
    private Genre bookGenre;
    private AgeRating ageRating;
    private Integer likeCount;
    private boolean likeState;
    private Integer subscribeCount;
    private boolean subscribeState;
    private Page<ChapterDto> chapterList;

}
