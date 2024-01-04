package com.project.novel.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    ROLE_USER("role_user"),
    ROLE_ADMIN("role_admin");

    private final String role;
}
