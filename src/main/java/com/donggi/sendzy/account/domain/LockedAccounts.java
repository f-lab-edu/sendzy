package com.donggi.sendzy.account.domain;

import java.util.List;

/**
 * 락이 걸린 송금자 계좌와 수신자 계좌를 묶어 표현하는 도메인 객체
 *
 * @param senderAccount 송금자 계좌
 * @param receiverAccount 수신자 계좌
 */
public record LockedAccounts(
    Account senderAccount,
    Account receiverAccount
) {
    public static LockedAccounts of(final List<Account> lockedAccounts, final long senderId, final long receiverId) {
        final var first = lockedAccounts.get(0);
        final var second = lockedAccounts.get(1);

        final var sender = first.getMemberId() == senderId ? first : second;
        final var receiver = first.getMemberId() == receiverId ? first : second;
        return new LockedAccounts(sender, receiver);
    }
}
