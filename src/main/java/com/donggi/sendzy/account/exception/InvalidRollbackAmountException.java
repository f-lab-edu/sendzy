package com.donggi.sendzy.account.exception;

public class InvalidRollbackAmountException extends RuntimeException {
    public InvalidRollbackAmountException(final long amount) {
        super("롤백 금액은 0보다 커야 합니다. 입력된 금액: " + amount);
    }
}
