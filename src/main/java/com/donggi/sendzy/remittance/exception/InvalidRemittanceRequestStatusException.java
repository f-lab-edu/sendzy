package com.donggi.sendzy.remittance.exception;

import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;

public class InvalidRemittanceRequestStatusException extends RuntimeException {

    public InvalidRemittanceRequestStatusException(final RemittanceRequestStatus status) {
        super("이미 처리된 송금 요청입니다. 현재 상태는 '" + status +  "'입니다.");
    }
}
