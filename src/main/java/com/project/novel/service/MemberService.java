package com.project.novel.service;

import com.project.novel.dto.JoinDto;
import com.project.novel.entity.Member;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Member join(JoinDto joinDto) {
        Member dbJoinMember = Member.builder()
                .userId(joinDto.getUserId())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .userName(joinDto.getUserName())
                .nickName(joinDto.getNickName())
                .email(joinDto.getEmail())
                .phoneNumber(joinDto.getPhoneNumber())
                .age(joinDto.getAge())
                .adminCheck(false)
                .build();
        return memberRepository.save(dbJoinMember);
    }
    public int idCheck(String userId) {
        return memberRepository.checkDuplicatedId(userId);
    }
    public int emailCheck(String email) {
        return memberRepository.checkDuplicatedEmail(email);
    }
}
