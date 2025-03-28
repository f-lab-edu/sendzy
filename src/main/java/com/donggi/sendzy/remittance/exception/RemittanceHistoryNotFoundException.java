package com.donggi.sendzy.remittance.exception;

import com.donggi.sendzy.common.exception.NotFoundException;

public class RemittanceHistoryNotFoundException extends NotFoundException {
    public RemittanceHistoryNotFoundException() {
        super("송금 내역이 존재하지 않습니다.");
    }

    public RemittanceHistoryNotFoundException(long historyId) {
        super("송금 내역이 존재하지 않습니다. historyId: " + historyId);
    }
}
