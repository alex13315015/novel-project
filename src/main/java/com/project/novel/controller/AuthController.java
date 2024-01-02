package com.project.novel.controller;

import com.project.novel.dto.JoinDto;
import com.project.novel.repository.MemberRepository;
import com.project.novel.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @GetMapping("/login")
    public String login() {
        return "/auth/login";
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
    @GetMapping("/idCheck")
    @ResponseBody
    public int idCheck(String dupId) {
        if(dupId == null || dupId.isEmpty())
            return -1;
        else
            return memberService.idCheck(dupId);
    }
}
