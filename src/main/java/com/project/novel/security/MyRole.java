package com.project.novel.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyRole {
    USER("role_user"),
    ADMIN("role_admin");

    private final String roleName;
}
