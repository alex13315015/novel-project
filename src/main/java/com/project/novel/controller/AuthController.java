package com.project.novel.controller;


import com.project.novel.dto.UserSaveDto;
import com.project.novel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginMember(){
        return "auth/login";
    }

//    @PostMapping("login")
//    public String loggedMember(){
////        memberService.loginMember();
//        return "library/home";
//    }

    @GetMapping("/signup")
    public String addMember(){
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(UserSaveDto dto){
        memberService.saveMember(dto);
        return "redirect:/auth/login";
    }

}
