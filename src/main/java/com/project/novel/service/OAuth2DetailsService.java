package com.project.novel.service;

import com.project.novel.constant.Grade;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.entity.Member;
import com.project.novel.repository.MemberRepository;
import com.project.novel.social.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //log.info("구글 로그인 하면 여기로 들어오고 여기서 필요한 작업하면 된다!");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User.getAttributes()==={}",oAuth2User.getAttributes());
        log.info("userRequest==={}",userRequest.getClientRegistration().getRegistrationId());
        Map<String,Object> oAuth2UserInfo = (Map)oAuth2User.getAttributes();

        SocialUserInfo socialUserInfo = null; // 인터페이스 구현

        //로그인 정보를 알려주는 각 소셜 ex) google, naver, kakao...
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("google")) {
            socialUserInfo = new GoolgleUserInfo(oAuth2UserInfo);

        } else if(provider.equals("naver")) {
            socialUserInfo = new NaverUserInfo((Map)oAuth2UserInfo.get("response"));

        } else if(provider.equals("kakao")) {
            socialUserInfo = new KakaoUserInfo(oAuth2UserInfo);

        } else if(provider.equals("github")) {
            socialUserInfo = new GithubUserInfo(oAuth2UserInfo);
        }

        String email = socialUserInfo.getEmail();
        String nickname = socialUserInfo.getName();
        String userId = socialUserInfo.getProviderId();
        Grade role = Grade.ROLE_USER;
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());

        Member returnMember = null;

        Optional<Member> foundMember = memberRepository.findByUserId(userId);
        if(foundMember.isPresent()) {
            returnMember = foundMember.get();
        } else {
            returnMember = Member.builder()
                    .userId(userId)
                    .password(password)
                    .role(role)
                    .nickname(nickname)
                    .email(email)
                    .build();
            memberRepository.save(returnMember);
        }
        return (OAuth2User) new CustomUserDetails(returnMember, oAuth2User.getAttributes());
    }
}
