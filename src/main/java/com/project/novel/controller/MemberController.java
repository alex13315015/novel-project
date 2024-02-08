package com.project.novel.controller;

import com.project.novel.dto.BookDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.dto.JoinDto;
import com.project.novel.service.BookService;
import com.project.novel.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BookService bookService;

    @GetMapping("/my")
    public String myPage() {
        return "/member/my";
    }

    @GetMapping("/memberInfo/{id}")
    public String memberInfo(@PathVariable Long id, Model model) {
        JoinDto getMemberInfo = memberService.getMemberInfo(id);
        log.info("==={}", getMemberInfo.toString());
        model.addAttribute("getMemberInfo", getMemberInfo);
        return "/member/memberInfo";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Model model) {
        JoinDto getMemberInfo = memberService.getMemberInfo(id);
        log.info("==={}", getMemberInfo.toString());
        model.addAttribute("getMemberInfo", getMemberInfo);
        return "/member/modify";
    }

    @PostMapping("/modify/{id}")
    public String modifyProcess(@PathVariable Long id,
                                @ModelAttribute JoinDto joinDto) {
        memberService.updateMember(id, joinDto);
        return "redirect:/auth/logout";
    }

    @GetMapping("/myBookList")
    public String mypage() {
        return "member/myBookList";
    }

    @GetMapping("/myBookList/{bookId}")
    public String myBookList(@PathVariable(name="bookId") Long bookId, Model model,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             @PageableDefault Pageable pageable) {
        // 해당 book 작성한 사람인지 확인
        if(bookService.isMyBook(bookId, customUserDetails.getLoggedMember().getId())) {
            BookDto bookDto = bookService.getBook(bookId,"DESC", pageable);
            model.addAttribute("bookInfo", bookDto);
        } else {
            throw new IllegalArgumentException("해당 책을 작성한 사람만 접근 가능합니다.");
        }

        return "member/myBookInfo";
    }
}
