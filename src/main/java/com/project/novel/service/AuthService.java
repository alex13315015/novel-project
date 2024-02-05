package com.project.novel.service;

import com.project.novel.constant.Grade;
import com.project.novel.dto.JoinDto;
import com.project.novel.entity.Authentication;
import com.project.novel.entity.Member;
import com.project.novel.repository.AuthRepository;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthRepository authRepository;
    public Member join(JoinDto joinDto) {
        Member dbJoinMember = Member.builder()
                .userId(joinDto.getUserId())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .userName(joinDto.getUserName())
                .nickname(joinDto.getNickname())
                .email(joinDto.getEmail())
                .phoneNumber(joinDto.getPhoneNumber())
                .age(joinDto.getAge())
                .role(Grade.USER.getRole())
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
    public int beRegisteredEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isEmpty()) {
            return 0;
        }
        return 1;
    }
    public int beRegisteredUserIdAndEmail (String userId, String email) {
        Optional<Member> optionalMember = memberRepository.findByUserIdAndEmail(userId,email);
        if(optionalMember.isEmpty()) {
            return 0;
        }
        return 1;
    }
    public int confirmAuthNum(String randomCode,String email) {
        Optional<Authentication> optionalUser = authRepository.findByRandomCodeAndUserEmail(randomCode,email);
        if(optionalUser.isEmpty()) {
            return 0;
        }
        return 1;
    }
    public Member findEmail (String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }
    @Transactional
    public void changePassword(String email, JoinDto joinDto) {
        Optional<Member> member = memberRepository.findByEmail(email);
        member.ifPresent(updateMember ->
                updateMember.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword())));
    }
}
