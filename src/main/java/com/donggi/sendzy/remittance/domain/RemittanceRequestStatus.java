package com.donggi.sendzy.remittance.domain;

public enum RemittanceRequestStatus {
    PENDING {
        @Override
        public RemittanceRequestStatus accept() {
            return ACCEPTED;
        }
    },
    ACCEPTED,
    EXPIRED,
    CANCELLED,
    ;

    public RemittanceRequestStatus accept() {
        throw new UnsupportedOperationException("현재 송금 상태에서는 수락할 수 없습니다: " + this);
    }
}
