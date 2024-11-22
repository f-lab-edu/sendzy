package com.donggi.sendzy.member.application;

import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.member.exception.EmailDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(final SignupRequest request) {
        if (memberService.existsByEmail(request.email())) {
            throw new EmailDuplicatedException("이미 가입된 이메일입니다.");
        }

        final var member = new Member(request.email(), passwordEncoder.encode(request.password()));
        memberService.save(member);
    }
}
