package com.project.novel.dto;


import com.project.novel.enums.BookGenre;
import com.project.novel.enums.AgeRating;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class BookDto {

    private Long id;
    private String bookName;
    private String writer;
    private String bookIntro;
    private String bookImage;
    private BookGenre bookGenre;
    private AgeRating ageRating;
    private Integer likeCount;
    private boolean likeState;
    private Integer subscribeCount;
    private boolean subscribeState;
    private Page<ChapterDto> chapterList;
    private Long totalHits;
    private List<Object> chapterIdList;

}