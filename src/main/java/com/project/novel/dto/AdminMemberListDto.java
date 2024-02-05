package com.project.novel.dto;

import com.project.novel.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMemberListDto {
    private String userId;
    private String userName;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Long id;

    public static AdminMemberListDto fromEntity(Member member) {
        return AdminMemberListDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
