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
    private Long pendingAmount;

    public Account(final Long memberId) {
        validate(memberId);
        this.memberId = memberId;
        this.balance = 0L;
        this.pendingAmount = 0L;
    }

    private void validate(final Long memberId) {
        validateMemberId(memberId);
    }

    private void validateMemberId(final Long memberId) {
        final var fieldName = "memberId";
        Validator.notNull(memberId, fieldName);
    }
}
