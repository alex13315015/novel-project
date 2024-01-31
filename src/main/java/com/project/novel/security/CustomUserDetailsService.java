//package com.project.novel.security;
//
//import com.project.novel.dto.CustomUserDetailsDto;
//import com.project.novel.entity.Member;
//import com.project.novel.repository.MemberRepository;
//import com.project.novel.service.MemberService;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    //사용자가 만든 데이터베이스에서 유저가 존재하면 UserDetails 정보를 넘겨주면 됨
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//
//        Member member = memberRepository.findByLoginId(loginId);
//
//        if(member != null){
//            return new CustomUserDetailsDto(member);
//        }
//
//        return null;
//    }
//}
