package com.project.novel.controller;

import com.project.novel.dto.ChapterDetailDto;
import com.project.novel.dto.ChapterUploadDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.repository.BookRepository;
import com.project.novel.service.ChapterService;
import com.project.novel.service.ViewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.swing.*;

@Controller
@RequestMapping("/chapter")
@RequiredArgsConstructor
@Slf4j
public class ChapterController {

    private final BookRepository bookRepository;
    private final ChapterService chapterService;
    private final ViewService viewService;

    @GetMapping("/write/{bookId}")
    public String writeChapter(@PathVariable(name="bookId") Long bookId, Model model,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails.getLoggedMember().getId() != bookRepository.findById(bookId).get().getMember().getId()) {
            JOptionPane.showMessageDialog(null, customUserDetails.getUsername() + "의 소설이 아닙니다.","권한 오류", JOptionPane.WARNING_MESSAGE);
            return "redirect:/member/myBookList";
        }
        model.addAttribute("bookId", bookId);
        model.addAttribute("chapterUploadDto", new ChapterUploadDto());
        return "chapter/write";
    }

    @PostMapping("/write/{bookId}")
    public String writeProcess(@Valid @ModelAttribute ChapterUploadDto chapterUploadDto,
                               BindingResult bindingResult,
                               @PathVariable(name="bookId") Long bookId,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors());
            return "redirect:/chapter/write/" + bookId;
        }
        chapterService.writeChapter(chapterUploadDto, bookId);
        return "redirect:/member/myBookList/" + bookId;
    }


    @GetMapping("/read/{chapterId}")
    public String readChapter(@PathVariable(name="chapterId") Long chapterId,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails,
                              Model model) {
        viewService.updateView(customUserDetails.getLoggedMember().getId(), chapterId);
        ChapterDetailDto chapterDetailDto = chapterService.getChapterDetail(chapterId);
        model.addAttribute("chapterDetailDto", chapterDetailDto);
        return "chapter/read";
    }

}
