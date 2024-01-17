package com.project.novel.dto;

import com.project.novel.entity.Book;
import com.project.novel.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class BookUploadDto {

    @Size(min = 1, max = 30, message = "1자 이상 30자 이하로 입력해주세요.")
    @NotBlank(message = "책 제목을 입력해주세요.")
    private String bookName;

    @Size(min = 1, max = 800, message = "1자 이상 800자 이하로 입력해주세요.")
    @NotBlank(message = "책 소개를 입력해주세요.")
    private String bookIntro;

    private MultipartFile bookImage;

    @NotBlank(message = "책 장르를 선택해주세요.")
    private String bookGenre;

    @NotNull(message = "연령 등급을 선택해주세요.")
    private Integer ageRating;

    public Book toEntity(String bookName, String bookIntro,
                             String bookImage, String bookGenre,
                             Integer ageRating, Member member)
    {
        return Book.builder()
                .bookName(bookName)
                .bookIntro(bookIntro)
                .bookImage(bookImage)
                .bookGenre(bookGenre)
                .ageRating(ageRating)
                .member(member)
                .build();
    }
}
