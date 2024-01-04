package com.project.novel.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    @Size(min = 8, max = 20, message = "아이디는 8~20글자만 가능합니다.")
    private String userId;
    @Size(min = 3, max = 20, message = "이름은 8~20글자만 가능합니다.")
    private String userName;
    @Size(max = 20, message = "닉네임은 최대 20글자까지 가능합니다.")
    private String nickName;


    private String password;

    private String email;

    private String rrnFront;
    private String rrnBack;

    private String phoneNumber;

}
