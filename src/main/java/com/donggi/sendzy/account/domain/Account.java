package com.donggi.sendzy.account.domain;

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

    public Account(final Long memberId, final Long balance) {
        validate(memberId, balance);
        this.memberId = memberId;
        this.balance = balance;
    }

    private void validate(final Long memberId, final Long balance) {
        validateMemberId(memberId);
        validateBalance(balance);
    }

    private void validateMemberId(final Long memberId) {
        final var fieldName = "memberId";
        Validator.notNull(memberId, fieldName);
    }

    private void validateBalance(final Long balance) {
        final var fieldName = "balance";
        Validator.notNull(balance, fieldName);
        Validator.notNegative(balance, fieldName);
    }
}
