package com.project.novel.controller;


import com.project.novel.dto.BookSaveDto;
import com.project.novel.entity.book.Book;
import com.project.novel.enums.Genre;
import com.project.novel.service.BookService;
import com.project.novel.util.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/make")
    public String makeBook(Model model){
        model.addAttribute("genreList", Genre.values());
        model.addAttribute("selectedGenre", Genre.NOVEL); // 기본 선택 값 설정
        return "book/makebook";
    }

    @PostMapping("/make")
    public String saveBook(BookSaveDto bookSaveDto, @RequestParam MultipartFile file) throws IOException {
        String attachFile = fileStore.storeFile(file);
        bookSaveDto.setBookImg(attachFile);
        bookService.saveBook(bookSaveDto);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFilePath(fileName));
    }

    @GetMapping("/library")
    public String homeController(Model model){
        List<Book> books = bookService.findAllBook();
        System.out.println(books.size());
        model.addAttribute("books", books);
        return "library/home";
    }



}
