package com.project.novel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookListDto {

    private Long id;
    private String bookName;
    private String bookImage;
    private String nickname;

}
