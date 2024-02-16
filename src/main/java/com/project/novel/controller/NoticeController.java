package com.project.novel.controller;

import com.project.novel.dto.CommentDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.dto.NoticeCommentDto;
import com.project.novel.dto.NoticeDto;
import com.project.novel.service.NoticeCommentService;
import com.project.novel.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    public final NoticeService noticeService;

    public final NoticeCommentService noticeCommentService;

    @GetMapping("/write")
    public String write(){
        return "/notice/write";
    }

    @PostMapping("/write")
    public String writeProcess(@ModelAttribute NoticeDto noticeDto,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        String username = customUserDetails.getUsername();
        // BoardDto에 사용자 이름을 설정하는 메서드가 있다고 가정
        noticeDto.setMemberId(username);

        log.info("noticeDto==={}",noticeDto);
        noticeService.save(noticeDto);
        return "redirect:/notice/list";
    }

    @GetMapping("/list")
    public String findAll(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) String keyword,
                          Model model) {

        // 검색 조건과 페이징 정보를 사용하여 게시판 목록을 조회
        Page<NoticeDto> noticeList = noticeService.searchAndPaging(category, keyword, pageable);

        int blockLimit = 10;

        // Pageable의 페이지 번호는 0부터 시작하므로 사용자 인터페이스에서는 1부터 시작하도록 1을 더함
        int currentPage = pageable.getPageNumber() + 1;
        int totalPages = noticeList.getTotalPages();
        // 데이터가 없을 경우를 처리하여 항상 최소 1 페이지는 표시되도록 함
        totalPages = Math.max(totalPages, 1);

        int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1);
        int endPage = Math.min(startPage + blockLimit - 1, totalPages);


        model.addAttribute("noticeList", noticeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages); // 전체 페이지 수 모델에 추가
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);

        return "/notice/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page) {

        NoticeDto noticeDto = noticeService.findById(id);
        List<NoticeCommentDto> commentDtoList = noticeCommentService.findAll(id);

        model.addAttribute("commentList", commentDtoList);
        model.addAttribute("notice", noticeDto);
        model.addAttribute("currentPage", page); // 현재 페이지 번호를 모델에 추가
        return "/notice/view";
    }


    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model){
        NoticeDto noticeDto = noticeService.findById(id);
        model.addAttribute("noticeModify",noticeDto);

        return "/notice/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute NoticeDto noticeDto, Model model){

        NoticeDto notice = noticeService.modify(noticeDto);
        model.addAttribute("notice", notice);

        return "/notice/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noticeCommentService.delete(id);
        return "redirect:/notice/list";
    }
}
