package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Member findByEmailOrThrow(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("요청한 회원 정보를 찾을 수 없습니다." + email));
    }
}
