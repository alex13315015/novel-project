package com.project.novel.dto;

import com.project.novel.entity.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    @NotNull(message = "아이디는 필수 입력사항입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상, 20자 이하로 입력해주세요.")
    private String userId;

    @Size(min = 2, max = 20, message = "이름은 2자 이상, 20자 이하로 입력해주세요.")
    private String userName;

    @Size(max = 20, message = "닉네임은 최대 20자만 입력해주세요.")
    private String nickname;

    @NotNull(message = "비밀번호는 필수 입력사항입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{8,20}$",
             message = "비밀번호는 영문,숫자,기호를 조합하여 8자 이상, 20자 이하로 입력해주세요.")
    private String password;

    @NotNull(message = "이메일은 필수 입력사항입니다.")
    private String email;

    private Integer age;

    private String phoneNumber;

    public static JoinDto fromEntity(Member member) {
        return JoinDto.builder()
                .userId(member.getUserId())
                .password(member.getPassword())
                .userName(member.getUserName())
                .nickname(member.getNickname())
                .age(member.getAge())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}

