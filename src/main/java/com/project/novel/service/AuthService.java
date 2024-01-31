package com.project.novel.service;

import com.project.novel.constant.Grade;
import com.project.novel.dto.JoinDto;
import com.project.novel.entity.Member;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Member join(JoinDto joinDto) {
        Member dbJoinMember = Member.builder()
                .userId(joinDto.getUserId())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .userName(joinDto.getUserName())
                .nickname(joinDto.getNickname())
                .email(joinDto.getEmail())
                .phoneNumber(joinDto.getPhoneNumber())
                .age(joinDto.getAge())
                .role(Grade.ROLE_USER)
                .build();
        return memberRepository.save(dbJoinMember);
    }
    public int idCheck(String userId) {
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        if(optionalMember.isEmpty()) {
            return 0;
        }
        return 1;
    }
    public int emailCheck(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isEmpty()) {
            return 0;
        }
        return 1;
    }
    public int passwordCheck(String password) {
        Optional<Member> optionalMember = memberRepository.findByPassword(password);
        if(optionalMember.isEmpty()) {
            return 0;
        }
        return 1;
    }
}
