package com.donggi.sendzy.account.domain;

import com.donggi.sendzy.account.exception.InvalidWithdrawalException;
import com.donggi.sendzy.common.utils.Validator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private Long id;
    private Long memberId;
    private Long balance;
    private Long pendingAmount;

    public Account(final Long memberId) {
        validate(memberId);
        this.memberId = memberId;
        this.balance = 0L;
        this.pendingAmount = 0L;
    }

    public void reserveWithdraw(final Long amount) {
        validateWithdraw(amount);
        this.pendingAmount += amount;
    }

    public void commitWithdraw(final long amount) {
        this.balance -= amount;
        this.pendingAmount -= amount;
    }

    public void cancelWithdraw(final long amount) {
        this.pendingAmount -= amount;
    }

    public void deposit(final Long amount) {
        final var fieldName = "amount";
        Validator.notNull(amount, fieldName);
        Validator.notNegative(amount, fieldName);

        this.balance += amount;
    }

    private void validate(final Long memberId) {
        validateMemberId(memberId);
    }

    private void validateMemberId(final Long memberId) {
        final var fieldName = "memberId";
        Validator.notNull(memberId, fieldName);
    }

    private void validateWithdraw(final Long amount) {
        final var fieldName = "amount";
        Validator.notNull(amount, fieldName);

        if (!isAmountPositive(amount)) {
            throw new InvalidWithdrawalException(amount);
        }

        if (!hasSufficientBalanceFor(amount)) {
            throw new InvalidWithdrawalException();
        }

        if (!hasSufficientBalanceIncludingPending(amount)) {
            throw new InvalidWithdrawalException();
        }
    }

    private boolean hasSufficientBalanceIncludingPending(long amount) {
        return this.pendingAmount + amount <= this.balance;
    }

    private boolean hasSufficientBalanceFor(final long amount) {
        return amount <= this.balance;
    }

    private boolean isAmountPositive(final long amount) {
        return 0 < amount;
    }
}
