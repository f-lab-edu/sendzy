package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;

import java.util.List;
import java.util.Optional;

public interface RemittanceHistoryRepository {

    /**
     * 송금 내역을 저장합니다.
     * @param remittanceHistory 저장할 송금 내역
     * @return 저장된 송금 ID
     */
    Long create(final RemittanceHistory remittanceHistory);

    /**
     * 송금 내역을 조회합니다.
     * @param id 조회할 송금 ID
     * @return 조회된 송금 내역
     */
    RemittanceHistory findById(final long id);

    /**
     * 송금 요청 ID로 송금 내역을 조회합니다.
     * @param requestId 송금 요청 ID
     * @return 조회된 송금 내역
     */
    Optional<RemittanceHistory> findByRequestId(final long requestId);

    /**
     * 송금 내역을 업데이트합니다.
     * @param remittanceHistory 업데이트할 송금 내역
     */
    void update(final RemittanceHistory remittanceHistory);
}
