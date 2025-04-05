package com.donggi.sendzy.account.application;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountRepository;
import com.donggi.sendzy.account.domain.LockedAccounts;
import com.donggi.sendzy.account.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AccountLockingService {

    private final AccountRepository accountRepository;

    /**
     * 두 개의 계좌를 계좌 ID 기준으로 오름차순 정렬하여 락을 획득합니다.
     * 데드락을 방지하기 위해 항상 일정한 순서로 락을 획득합니다.
     * @param senderId 송금 회원 ID
     * @param receiverId 수신 회원 ID
     * @return 락을 획득한 계좌 목록
     */
    @Transactional
    public LockedAccounts getAccountsWithLockOrdered(final long senderId, final long receiverId) {
        final List<Long> sortedIds = getSortedIds(senderId, receiverId);
        final List<Account> lockedAccounts = sortedIds.stream()
            .map(accountId -> accountRepository.findByMemberIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId)))
            .toList();

        return LockedAccounts.of(lockedAccounts, senderId, receiverId);
    }

    /**
     * 회원 ID로 계좌를 조회하고 해당 계좌의 락을 획득합니다.
     * @param senderId 송신자 ID
     * @return 조회된 계좌
     */
    @Transactional
    public Account getByMemberIdForUpdate(final long senderId) {
        return accountRepository.findByMemberIdForUpdate(senderId)
            .orElseThrow(() -> new AccountNotFoundException(senderId));
    }

    private List<Long> getSortedIds(final long senderId, final long receiverId) {
        return Stream.of(senderId, receiverId)
            .sorted()
            .toList();
    }
}
