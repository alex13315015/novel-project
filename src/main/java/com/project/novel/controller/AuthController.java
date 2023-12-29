package com.project.novel.controller;

import com.project.novel.dto.JoinDto;
import com.project.novel.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "/auth/logout";
    }
    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("joinDto",new JoinDto());
        return "/auth/join";
    }
    @PostMapping("/join")
    public String joinProcess(JoinDto joinDto) {
        memberService.join(joinDto);
        return "/auth/join";
    }
}
