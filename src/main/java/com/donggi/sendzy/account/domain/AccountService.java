package com.donggi.sendzy.account.domain;

import com.donggi.sendzy.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Account getByMemberId(final Long memberId) {
        return accountRepository.findByMemberId(memberId)
            .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다. :" + memberId));
    }
}
