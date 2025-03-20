package com.donggi.sendzy.account.exception;

public class InvalidWithdrawalException extends RuntimeException {
    public InvalidWithdrawalException() {
        super("잔액이 부족합니다.");
    }

    public InvalidWithdrawalException(final Long amount) {
        super("송금액은 0원 이상이어야 합니다. 사용자 요청 금액: " + amount);
    }
}
