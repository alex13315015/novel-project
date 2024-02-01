package com.project.novel.dto;

import com.project.novel.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Member loggedMember;
    private Map<String, Object> attributes;

    public CustomUserDetails(Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    public CustomUserDetails(Member returnMember, Map<String, Object> attributes) {
        this.loggedMember = returnMember;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return loggedMember.getRole().toString();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return loggedMember.getPassword();
    }

    @Override
    public String getUsername() {
        return loggedMember.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
