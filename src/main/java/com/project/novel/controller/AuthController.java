package com.project.novel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "/auth/logout";
    }
}
