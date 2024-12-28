package com.donggi.sendzy.common.security;

import com.donggi.sendzy.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재 권한은 사용하지 않음
        return List.of();
    }

    @Override
    public String getPassword() {
        return member.getEncodedPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    public long getMemberId() {
        return member.getId();
    }
}
