package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;

public interface RemittanceHistoryRepository {

    /**
     * 송금 내역을 저장합니다.
     * @param remittanceHistory 저장할 송금 내역
     * @return 저장된 송금 ID
     */
    Long create(RemittanceHistory remittanceHistory);
}
