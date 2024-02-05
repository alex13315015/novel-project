package com.project.novel.controller;

import com.project.novel.dto.JoinDto;
import com.project.novel.entity.Member;
import com.project.novel.service.AuthService;
import com.project.novel.service.MailService;
import jakarta.mail.MessagingException;
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
    private final MailService mailService;
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
    @GetMapping("/findUserId")
    public String findUserId() {
        return "/auth/findUserId";
    }
    @GetMapping("/findPassword")
    public String findPassword() {
        return "/auth/findPassword";
    }
    @GetMapping("/confirmEmail")
    @ResponseBody
    public int authenticatedEmail(String userEmail) throws MessagingException {
        // 입력한 값이 db에 있는지 확인
        if (userEmail == null || userEmail.isEmpty()) {
            return -1;
        } else {
            log.info("userEmail==={}",userEmail);
            int returnValue = authService.beRegisteredEmail(userEmail);
            if (returnValue == 1) {
                mailService.sendAuthMail(userEmail);

            } else {
                return 0;
            }
        }
        return 1;
    }
    @GetMapping("/confirmUserIdAndEmail")
    @ResponseBody
    public int authenticatedUserIdAndEmail(String userId,String userEmail) throws MessagingException {
        // 입력한 값이 db에 있는지 확인
        if (userId == null && userEmail == null || userEmail.isEmpty()) {
            return -1;
        } else {
            int returnValue = authService.beRegisteredUserIdAndEmail(userId,userEmail);
            if (returnValue == 1) {
                mailService.sendAuthMail(userEmail);
            } else {
                return 0;
            }
        }
        return 1;
    }
    @GetMapping("/confirmAuthNum")
    @ResponseBody
    public int confirmAuthNum(String authNum,String userEmail) {
        if(authNum == null || authNum.isEmpty())
            return -1;
        else
            return authService.confirmAuthNum(authNum,userEmail);
    }
    @GetMapping("/confirmUserId")
    public String confirmUserId (Model model,String email) {
        Member member = authService.findEmail(email);
        if(member != null) {
            model.addAttribute("userId",member.getUserId());
        }
        return "/auth/confirmUserId";
    }
    @GetMapping("/changePassword")
    public String changePassword () {
        return "/auth/changePassword";
    }
    @PostMapping("/changePassword")
    public String changePasswordProcess(@RequestParam String email,@ModelAttribute JoinDto joinDto) {
        authService.changePassword(email,joinDto);
        return "redirect:/auth/login";
    }
}
