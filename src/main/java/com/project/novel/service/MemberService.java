package com.project.novel.service;

import com.project.novel.dto.JoinDto;
import com.project.novel.dto.MemberProfileDto;
import com.project.novel.entity.Member;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    @Value("${file.path}")
    private String uploadFolder;

    public JoinDto getMemberInfo(Long id) {
        Optional<Member> memberEntity = memberRepository.findById(id);
        return memberEntity.map(JoinDto::fromEntity).orElse(null);
    }
    @Transactional
    public void updateMember(Long id, JoinDto joinDto) {
        // 특정 데이터를 찾아서 먼저 select 해야한다.
        Optional<Member> member = memberRepository.findById(id);

        // 해당 데이터가 존재해야한다.
        member.ifPresent(selectMember -> {
            selectMember.setUserName(joinDto.getUserName());
            selectMember.setPassword(joinDto.getPassword());
            selectMember.setNickname(joinDto.getNickname());
            selectMember.setEmail(joinDto.getEmail());
            selectMember.setPhoneNumber(joinDto.getPhoneNumber());
            selectMember.setAge(joinDto.getAge());
            selectMember.setUpdatedAt(LocalDateTime.now());
        });
    }
    @Transactional
    public Member changeProfileImg(Long id, MultipartFile profile_image) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profile_image.getOriginalFilename();
        String thumbnailFileName = "thumb" + imageFileName;
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        File originalFile = new File(uploadFolder + imageFileName);
        try {
            Files.write(imageFilePath, profile_image.getBytes());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Optional<Member> optionalMember = memberRepository.findById(id); // 엔티티 member 찾아서
        if(optionalMember.isPresent()) {
            optionalMember.get().setProfileImage(imageFileName); // setter로 update
            return optionalMember.get();
        } else {
            throw new UsernameNotFoundException("등록되지 않은 회원입니다.");
        }
    }
    @Transactional
    public MemberProfileDto getProfile(Long id) {
        MemberProfileDto memberProfileDto = new MemberProfileDto();
        Member memberInfo =
                memberRepository.findById(id).orElseThrow(
                        () -> new UsernameNotFoundException("등록되지 않은 회원입니다.")
                );
        memberProfileDto.setMember(memberInfo);
        return memberProfileDto;
    }
}
