package com.donggi.sendzy.remittance.exception;

import com.donggi.sendzy.common.exception.NotFoundException;

public class RemittanceStatusHistoryNotFoundException extends NotFoundException {
    public RemittanceStatusHistoryNotFoundException() {
        super("송금 상태 이력이 존재하지 않습니다.");
    }

    public RemittanceStatusHistoryNotFoundException(long historyId) {
        super("송금 상태 이력이 존재하지 않습니다. historyId: " + historyId);
    }
}
