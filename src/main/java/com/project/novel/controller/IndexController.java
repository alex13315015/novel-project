package com.project.novel.controller;

import com.project.novel.dto.BookSummaryDto;
import com.project.novel.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/")
    public String index(Model model){

        List<BookSummaryDto> newBookOfWeeks = indexService.getNewBookOfWeek();
        List<BookSummaryDto> popularityBookOfWeeks = indexService.getPopularityBookOfWeek();

        model.addAttribute("newBookOfWeeks", newBookOfWeeks);
        model.addAttribute("popularityBookOfWeeks", popularityBookOfWeeks);

        return "index/index";
    }
}
