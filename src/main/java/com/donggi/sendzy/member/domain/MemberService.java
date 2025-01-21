package com.donggi.sendzy.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long registerMemberAndGetId(final Member member) {
        return memberRepository.create(member);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }
}
