package com.project.novel.service;

import com.project.novel.dto.UserSaveDto;
import com.project.novel.entity.Member;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveMember(UserSaveDto dto){

        memberRepository.save(dto.toEntity(bCryptPasswordEncoder));
    }
}
