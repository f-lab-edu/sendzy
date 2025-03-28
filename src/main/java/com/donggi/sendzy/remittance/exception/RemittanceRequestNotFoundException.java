package com.donggi.sendzy.remittance.exception;

import com.donggi.sendzy.common.exception.NotFoundException;

public class RemittanceRequestNotFoundException extends NotFoundException {
    public RemittanceRequestNotFoundException() {
        super("송금 요청 정보를 찾을 수 없습니다.");
    }

    public RemittanceRequestNotFoundException(final long requestId) {
        super("송금 요청 정보를 찾을 수 없습니다. requestId: " + requestId);
    }
}
