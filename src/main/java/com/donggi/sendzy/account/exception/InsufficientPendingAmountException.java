package com.donggi.sendzy.account.exception;

public class InsufficientPendingAmountException extends RuntimeException {
    public InsufficientPendingAmountException(final long pendingAmount, final long rollbackAmount) {
        super("롤백할 수 있는 대기 금액이 부족합니다. 현재 대기 금액: " +
            pendingAmount + ", 롤백 시도 금액: " + rollbackAmount);
    }
}
