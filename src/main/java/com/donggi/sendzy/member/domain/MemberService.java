package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void create(final Member member) {
        memberRepository.create(member);
    }

    public boolean existsByEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("요청한 회원 정보를 찾을 수 없습니다." + email));
    }
}
