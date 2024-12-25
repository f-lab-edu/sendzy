package com.donggi.sendzy.account.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void create(final Account account) {
        accountRepository.create(account);
    }

    @Transactional(readOnly = true)
    public Account findByMemberId(final Long memberId) {
        return accountRepository.findByMemberId(memberId);
    }

    @Transactional
    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
