package com.donggi.sendzy.member.application;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountRepository;
import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.member.dto.SignupRequest;
import com.donggi.sendzy.member.exception.EmailDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Service 는 내부적으로 @Component 를 포함하고 있어 해당 클래스를 컴포넌트 스캔 대상으로 등록합니다.
 * 또한 해당 클래스가 비즈니스 로직을 담당하는 클래스임을 명시합니다.
 */
@Service
@RequiredArgsConstructor
public class SignupService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Transactional
    public void signup(final SignupRequest request) {
        if (memberService.existsByEmail(request.email())) {
            throw new EmailDuplicatedException("이미 가입된 이메일입니다.");
        }

        final var member = new Member(request.email(), passwordEncoder.encode(request.rawPassword()));
        final var createdMemberId = memberService.registerMemberAndGetId(member);
        accountRepository.create(new Account(createdMemberId));
    }
}
