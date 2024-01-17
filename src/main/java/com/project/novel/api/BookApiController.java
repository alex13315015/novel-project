package com.project.novel.api;

import com.project.novel.dto.BookLikesDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookApiController {

    private final BookService bookService;


    @PostMapping("/book/{bookId}/like")
    public BookLikesDto likeBook(@PathVariable(name="bookId") Long bookId,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        bookService.like(bookId, customUserDetails.getLoggedMember().getId());
        return BookLikesDto.builder()
                .likeCount(bookService.likeCount(bookId))
                .likeState(bookService.isLikedByUser(bookId, customUserDetails.getLoggedMember().getId()))
                .build();
    }

    @DeleteMapping("/book/{bookId}/like")
    public BookLikesDto unlikeBook(@PathVariable(name="bookId") Long bookId,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        bookService.unlike(bookId, customUserDetails.getLoggedMember().getId());
        return BookLikesDto.builder()
                .likeCount(bookService.likeCount(bookId))
                .likeState(bookService.isLikedByUser(bookId, customUserDetails.getLoggedMember().getId()))
                .build();
    }


}
