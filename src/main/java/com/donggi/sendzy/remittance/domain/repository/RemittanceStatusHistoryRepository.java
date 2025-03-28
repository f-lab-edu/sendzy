package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;

import java.util.Optional;

public interface RemittanceStatusHistoryRepository {

    /**
     * 송금 요청 상태의 변경 이력을 저장합니다.
     * @param remittanceStatusHistory 저장할 송금 요청 상태 변경 이력
     * @return 저장된 변경 이력 ID
     */
    Long create(final RemittanceStatusHistory remittanceStatusHistory);

    /**
     * 송금 요청 ID로 송금 이력을 조회합니다.
     * @param requestId 송금 요청 ID
     * @return 송금 이력
     */
    Optional<RemittanceStatusHistory> findByRequestId(long requestId);
}
