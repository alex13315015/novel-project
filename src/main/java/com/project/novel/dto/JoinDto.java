package com.project.novel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    private String userId;
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private int age;
    private String phoneNumber;

}
