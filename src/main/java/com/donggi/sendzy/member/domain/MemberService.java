package com.donggi.sendzy.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(final Member member) {
        memberRepository.save(member);
    }

    public boolean existsByEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }
}
