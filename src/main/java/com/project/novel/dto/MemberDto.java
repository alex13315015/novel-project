package com.project.novel.dto;

import lombok.Data;

@Data
public class MemberDto {
    private String id;
    private String password;

    public MemberDto(String id, String password){
        this.id = id;
        this.password = password;
    }
}
