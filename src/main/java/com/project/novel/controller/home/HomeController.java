package com.project.novel.controller.home;

import com.project.novel.dto.BookDto;
import com.project.novel.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class HomeController {


    @Autowired
    private final BookService bookService = new BookService();


    @GetMapping("/home")
    @ResponseBody
    public List<BookDto> homeController(@RequestParam(name = "sortType", defaultValue = "modifiedDate") String sortType){
        return bookService.findAllBook(sortType);
    }
}
