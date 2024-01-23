package com.project.novel.controller;

import com.project.novel.dto.BookDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final BookService bookService;

    @GetMapping("/myBookList")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        model.addAttribute("myBookList", bookService.getAllMyBook(customUserDetails.getLoggedMember().getId(), "Update"));
        return "member/myBookList";
    }

    @GetMapping("/myBookList/{bookId}")
    public String myBookList(@PathVariable(name="bookId") Long bookId, Model model,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 해당 bookId를 작성한 사람인지 예외 처리
        if(!bookService.isMyBook(bookId, customUserDetails.getLoggedMember().getId())) {
            throw new IllegalArgumentException("해당 책을 작성한 사람만 접근 가능합니다.");
        }
        BookDto bookDto = bookService.getBook(bookId,"Update");
        model.addAttribute("bookInfo", bookDto);

        return "member/myBookInfo";
    }

}
