package com.project.novel.controller;

import com.project.novel.dto.book.BookDto;
import com.project.novel.entity.book.Book;
import com.project.novel.service.BookService;
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

    private final BookService bookService;

    @GetMapping("/library")
    public String homeController(@RequestParam(value = "sortType", required = false, defaultValue = "createdDateDesc") String sortType,
                                 @RequestParam(value = "bookSearch", required = false, defaultValue = "") String bookSearch, Model model){
        List<BookDto> books = bookService.findAllBook(sortType, bookSearch);
        model.addAttribute("books", books);
        return "library/home";
    }
}
