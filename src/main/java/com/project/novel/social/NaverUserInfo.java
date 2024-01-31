package com.project.novel.social;

import lombok.RequiredArgsConstructor;

import java.util.Map;
@RequiredArgsConstructor
public class NaverUserInfo implements SocialUserInfo{
    private final Map<String,Object> attributes;
    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return getProvider() + "_" + (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }
}
