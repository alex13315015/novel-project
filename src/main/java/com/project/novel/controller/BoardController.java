package com.project.novel.controller;

import com.project.novel.dto.BoardDto;
import com.project.novel.dto.CommentDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.service.BoardService;
import com.project.novel.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    public final BoardService boardService;

    public final CommentService commentService;


    @GetMapping("/write")
    public String write(){
        return "/board/write";
    }

    @PostMapping("/write")
    public String writeProcess(@ModelAttribute BoardDto boardDto,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        String username = customUserDetails.getUsername();
        // BoardDto에 사용자 이름을 설정하는 메서드가 있다고 가정
        boardDto.setMemberId(username);

        log.info("boardDto==={}",boardDto);
        boardService.save(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String findAll(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) String keyword,
                          Model model) {

        // 검색 조건과 페이징 정보를 사용하여 게시판 목록을 조회
        Page<BoardDto> boardList = boardService.searchAndPaging(category, keyword, pageable);

        int blockLimit = 10;

        // Pageable의 페이지 번호는 0부터 시작하므로 사용자 인터페이스에서는 1부터 시작하도록 1을 더함
        int currentPage = pageable.getPageNumber() + 1;

        int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1);

        int endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);

        return "/board/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page) {
        boardService.updateHit(id);
        BoardDto boardDto = boardService.findById(id);

        List<CommentDto> commentDtoList = commentService.findAll(id);
        model.addAttribute("commentList", commentDtoList);

        model.addAttribute("board", boardDto);
        model.addAttribute("currentPage", page); // 현재 페이지 번호를 모델에 추가
        return "/board/view";
    }


    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardModify",boardDto);
        return "/board/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute BoardDto boardDto, Model model){
        BoardDto board = boardService.modify(boardDto);
        model.addAttribute("board",board);
        return "/board/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/list";
    }

}
