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

    public void withdraw(final Long amount) {
        validateWithdraw(amount);
        this.pendingAmount += amount;
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
        Validator.notNegative(amount, fieldName);

        if (amount <= 0) {
            throw new InvalidWithdrawalException(amount);
        }

        if (this.balance < amount) {
            throw new InvalidWithdrawalException();
        }

        if (this.balance < this.pendingAmount + amount) {
            throw new InvalidWithdrawalException();
        }
    }
}
