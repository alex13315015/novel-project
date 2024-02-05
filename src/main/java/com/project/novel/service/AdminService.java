package com.project.novel.service;

import com.project.novel.dto.AdminMemberListDto;
import com.project.novel.entity.Member;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final MemberRepository memberRepository;
    public List<AdminMemberListDto> getMemberList() {
        List<Member> memberList = memberRepository.findAll();
        List<AdminMemberListDto> memberDtoList = new ArrayList<>();
        for(Member member : memberList) {
            memberDtoList.add(AdminMemberListDto.fromEntity(member));
        }
        return memberDtoList;
    }
    @Transactional
    public void deleteMember (Long id) {
        memberRepository.deleteMember(id);
    }
}
