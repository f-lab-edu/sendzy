package com.donggi.sendzy.member.domain;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(final String email, final String password) {
        memberRepository.save(new Member(email, password));
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
