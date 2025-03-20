package com.donggi.sendzy.account.domain;

import com.donggi.sendzy.account.exception.AccountNotFoundException;
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
            .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional
    public void withdraw(final Account account, final Long amount) {
        account.withdraw(amount);
        accountRepository.updatePendingAmount(account.getId(), account.getPendingAmount());
    }

    @Transactional(readOnly = true)
    public Account findByIdForUpdate(final Long memberId) {
        return accountRepository.findByIdForUpdate(memberId)
            .orElseThrow(() -> new AccountNotFoundException(memberId));
    }
}
