package com.project.novel.controller;

import com.project.novel.service.ViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {


    @GetMapping({"", "/index","/"})
    public String index(Model model) {

        return "index/index";
    }
}
