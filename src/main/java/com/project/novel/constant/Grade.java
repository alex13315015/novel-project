package com.project.novel.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Grade {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}
