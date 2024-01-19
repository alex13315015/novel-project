package com.project.novel.dto;

import com.project.novel.entity.Book;
import com.project.novel.entity.Chapter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
public class ChapterUploadDto {

    @Size(min = 1, max = 30, message = "1자 이상 30자 이하로 입력해주세요.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    public Chapter toEntity(Book book) {
        return Chapter.builder()
                .title(title)
                .contents(contents)
                .price(price)
                .book(book)
                .build();
    }



}
