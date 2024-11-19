package com.donggi.sendzy.member.application;

import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.member.exception.EmailDuplicatedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public SignupService(final MemberService memberService, final PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(final SignupRequest request) {
        if (memberService.existsByEmail(request.email())) {
            throw new EmailDuplicatedException("이미 가입된 이메일입니다.");
        }

        memberService.save(request.email(), passwordEncoder.encode(request.password()));
    }
}
