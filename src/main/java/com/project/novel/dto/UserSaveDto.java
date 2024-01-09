package com.project.novel.dto;

import com.project.novel.entity.Member;
import com.project.novel.security.MyRole;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveDto {
    private String loginId;
    private String name;
    private String password;

    public Member toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return Member.builder()
                .loginId(loginId)
                .name(name)
                .password(bCryptPasswordEncoder.encode(password))
                .role(MyRole.USER)
                .build();
    }

}
