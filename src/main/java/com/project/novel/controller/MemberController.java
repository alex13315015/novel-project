package com.project.novel.controller;

import com.project.novel.dto.JoinDto;
import com.project.novel.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/my")
    public String myPage() {
        return "/member/my";
    }
    @GetMapping("/memberInfo/{id}")
    public String memberInfo(@PathVariable Long id, Model model) {
        JoinDto getMemberInfo = memberService.getMemberInfo(id);
        log.info("==={}",getMemberInfo.toString());
        model.addAttribute("getMemberInfo", getMemberInfo);
        return "/member/memberInfo";
    }
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Model model) {
        JoinDto getMemberInfo = memberService.getMemberInfo(id);
        log.info("==={}",getMemberInfo.toString());
        model.addAttribute("getMemberInfo", getMemberInfo);
        return "/member/modify";
    }
    @PostMapping("/modify/{id}")
    public String modifyProcess(@PathVariable Long id,
                @ModelAttribute JoinDto joinDto) {
            memberService.updateMember(id, joinDto);
        return "redirect:/auth/logout";
    }
}
