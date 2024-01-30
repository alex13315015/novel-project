package com.project.novel.dto;

import com.project.novel.entity.Member;
import com.project.novel.entity.Book;
import com.project.novel.enums.AgeRating;
import com.project.novel.enums.Genre;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSaveDto {
//    private Long id;

    private String bookName;

    private String bookIntro;

    private String bookImg;

    private AgeRating ageRating;

    private Genre genre;


    public Book toEntity(Member member){
        return Book.builder()
                .bookName(bookName)
                .bookIntro(bookIntro)
                .bookImage(bookImg)
                .genre(genre)
                .ageRating(ageRating)
                .member(member)
                .build();
    }
}
