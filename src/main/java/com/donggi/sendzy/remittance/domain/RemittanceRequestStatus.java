package com.donggi.sendzy.remittance.domain;

public enum RemittanceRequestStatus {
    PENDING {
        @Override
        public RemittanceRequestStatus accept() {
            return ACCEPTED;
        }

        @Override
        public RemittanceRequestStatus reject() {
            return REJECTED;
        }
    },
    ACCEPTED,
    REJECTED,
    EXPIRED,
    CANCELLED,
    ;

    public RemittanceRequestStatus accept() {
        throw new UnsupportedOperationException("현재 송금 상태에서는 수락할 수 없습니다: " + this);
    }

    public RemittanceRequestStatus reject() {
        throw new UnsupportedOperationException("현재 송금 상태에서는 거절할 수 없습니다: " + this);
    }
}
