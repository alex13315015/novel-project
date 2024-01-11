package com.project.novel.dto;

import com.project.novel.entity.Member;
import com.project.novel.entity.book.Book;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSaveDto {
    private String bookName;

    private String bookIntro;

    private String bookImg;

    private int ageRating;

    private String genre;


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
