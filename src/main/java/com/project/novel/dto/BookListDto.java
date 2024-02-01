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
    private Long chapterId;

    public BookListDto(Long id, String bookName, String bookImage, String nickname) {
        this.id = id;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.nickname = nickname;
    }

    public BookListDto(Long id, String bookName, String bookImage, String nickname, Long chapterId) {
        this.id = id;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.nickname = nickname;
        this.chapterId = chapterId;
    }

}
