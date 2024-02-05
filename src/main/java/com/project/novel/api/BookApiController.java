package com.project.novel.api;

import com.project.novel.dto.BookLikesDto;
import com.project.novel.dto.BookListDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.service.BookService;
import com.project.novel.service.SubscribeService;
import com.project.novel.service.ViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BookApiController {

    private final BookService bookService;
    private final ViewService viewService;
    private final SubscribeService subscribeService;


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

    @GetMapping("/book/{memberId}/bookList")
    public ResponseEntity<Slice<BookListDto>> getMyBookList(@PathVariable(name="memberId") Long memberId,
                                                            @PageableDefault(size = 25, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllMyBook(memberId, pageable));
    }

    @GetMapping("/book/myRecent")
    public Slice<BookListDto> getMyRecentViewList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                  @PageableDefault(size = 25, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return viewService.recentViewList(customUserDetails.getLoggedMember().getId(), pageable);
    }

    @GetMapping("/book/mySubscribe")
    public Slice<BookListDto> getMySubscribeList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PageableDefault(size = 25, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return subscribeService.getMySubscribeList(customUserDetails.getLoggedMember().getId(), pageable);
    }

}
