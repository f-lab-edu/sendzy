package com.donggi.sendzy.remittance.exception;

public class ExpiredRemittanceRequestException extends RuntimeException {

    public ExpiredRemittanceRequestException() {
        super("송금 요청이 만료되었습니다.");
    }
}
