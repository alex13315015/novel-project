package com.project.novel.controller;

import com.project.novel.dto.book.BookInfoDto;
import com.project.novel.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/library")
    public String homeController(@RequestParam(value = "sortType", required = false, defaultValue = "createdDateDesc") String sortType,
                                 @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model){

        List<BookInfoDto> books = libraryService.test(sortType, keyword, page, model);
        model.addAttribute("books", books);
        model.addAttribute("sortType", sortType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "library/library";
    }
}
