package com.donggi.sendzy.account.exception;

import com.donggi.sendzy.common.exception.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException(final Long memberId) {
        super("계좌를 찾을 수 없습니다. :" + memberId);
    }
}
