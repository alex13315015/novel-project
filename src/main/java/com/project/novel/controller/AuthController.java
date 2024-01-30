package com.project.novel.controller;

import com.project.novel.dto.JoinDto;
import com.project.novel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
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
    public String joinProcess(@Valid @ModelAttribute JoinDto joinDto,
                              BindingResult bindingResult,
                              Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("joinDto",joinDto);
            return "/auth/join";
        }
        authService.join(joinDto);
        return "redirect:/auth/login";
    }
    @GetMapping("/idCheck")
    @ResponseBody
    public int idCheck(String dupId) { // dupId => 유저로부터 입력받는 값
        if(dupId == null || dupId.isEmpty())
            return -1;
        else
            return authService.idCheck(dupId);
    }
    @GetMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(String dupEmail) { // dupEmail => 유저로부터 입력받는 값
        if(dupEmail == null || dupEmail.isEmpty())
            return -1;
        else
            return authService.emailCheck(dupEmail);
    }
    @GetMapping("/passwordCheck")
    @ResponseBody
    public int passwordCheck(String secondPW) { // secondPW => 유저로부터 입력받는 재입력 값
        if(secondPW == null || secondPW.isEmpty())
            return -1;
        else
            return authService.passwordCheck(secondPW);
    }
}
