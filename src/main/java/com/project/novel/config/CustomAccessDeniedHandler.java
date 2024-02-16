package com.project.novel.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

// 이 핸들러는 접근이 거부된 사용자를 특정 URL로 리다이렉트 시키는 역할

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 접근 거부 시 사용자를 리다이렉트할 URL 정의
        String deniedUrl = "/auth/access-denied?error=true";
        // 정의된 URL로 사용자를 리다이렉트
        response.sendRedirect(deniedUrl);
    }
}