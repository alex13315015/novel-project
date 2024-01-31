package com.project.novel.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyRole {
    USER("role_user"),
    ADMIN("role_admin");

    private final String roleName;
}
