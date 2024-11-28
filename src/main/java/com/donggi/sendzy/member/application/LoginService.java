package com.donggi.sendzy.member.application;

import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.dto.LoginRequest;
import com.donggi.sendzy.member.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String login(final LoginRequest request) {
        final var member = memberService.findByEmail(request.email());

        if (!passwordEncoder.matches(request.rawPassword(), member.getEncodedPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return "로그인 성공";
    }
}
