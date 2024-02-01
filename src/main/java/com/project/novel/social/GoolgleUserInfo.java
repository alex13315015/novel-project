package com.project.novel.social;

import lombok.RequiredArgsConstructor;

import java.util.Map;
@RequiredArgsConstructor
public class GoolgleUserInfo implements SocialUserInfo{
    private final Map<String,Object> attributes;
    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return getProvider() + "_" + (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
