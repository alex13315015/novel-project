package com.project.novel.controller;

import com.project.novel.dto.BookDto;
import com.project.novel.entity.book.Book;
import com.project.novel.service.BookService;
import com.project.novel.util.FileStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class LibraryController {

    private final BookService bookService;

    public LibraryController(BookService bookService){
        this.bookService = bookService;
    }





}
