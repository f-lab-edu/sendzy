package com.donggi.sendzy.account.domain;

import com.donggi.sendzy.account.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Account getByMemberId(final long memberId) {
        return accountRepository.findByMemberId(memberId)
            .orElseThrow(() -> new AccountNotFoundException(memberId));
    }

    @Transactional
    public void withdraw(final Account account, final long amount) {
        account.reserveWithdraw(amount);
        accountRepository.update(account);
    }

    @Transactional
    public void deposit(final Account account, final long amount) {
        account.deposit(amount);
        accountRepository.update(account);
    }

    @Transactional
    public void transfer(final Account sender, final Account receiver, final long amount) {
        sender.commitWithdraw(amount);
        receiver.deposit(amount);
        accountRepository.update(sender);
        accountRepository.update(receiver);
    }

    @Transactional
    public void cancelWithdraw(final Account account, final long amount) {
        account.cancelWithdraw(amount);
        accountRepository.update(account);
    }

    @Transactional
    public void rollbackHoldAmounts(final List<RollbackTarget> rollbackTargets) {
        // 롤백 대상 송금자 ID에 해당하는 계좌 정보를 조회하고 ID 기준으로 매핑
        final Map<Long, Account> accounts = loadAccounts(rollbackTargets);

        // 각 롤백 대상에 대해 계좌의 출금 대기 금액을 롤백 처리
        applyRollbacks(accounts, rollbackTargets);

        // 롤백 처리된 계좌들을 일괄 업데이트하여 DB에 반영
        accountRepository.bulkUpdate(accounts.values().stream().toList());
    }

    private Map<Long, Account> loadAccounts(final List<RollbackTarget> targets) {
        final Set<Long> senderIds = targets.stream()
            .map(RollbackTarget::senderId)
            .collect(Collectors.toSet());

        final List<Account> accountList = accountRepository.findAllByMemberIdIn(senderIds);
        return accountList.stream()
            .collect(Collectors.toMap(Account::getMemberId, Function.identity()));
    }

    private void applyRollbacks(final Map<Long, Account> accountMap, final List<RollbackTarget> targets) {
        for (RollbackTarget target : targets) {
            final Account account = accountMap.get(target.senderId());
            if (account == null) {
                throw new AccountNotFoundException(target.senderId());
            }
            account.cancelWithdraw(target.amount());
        }
    }
}
