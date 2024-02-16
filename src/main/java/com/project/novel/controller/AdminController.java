package com.project.novel.controller;

import com.project.novel.dto.AdminMemberListDto;
import com.project.novel.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/memberList")
    public String memberList(Model model) {
        List<AdminMemberListDto> memberList = adminService.getMemberList();
        model.addAttribute("memberList",memberList);
        return "/admin/memberList";
    }
}
