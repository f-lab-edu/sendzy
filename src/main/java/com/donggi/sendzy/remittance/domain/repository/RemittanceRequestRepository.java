package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;

import java.util.Optional;

public interface RemittanceRequestRepository {

    /**
     * 송금 요청 정보를 저장합니다.
     * @param remittanceRequest 저장할 송금 요청 정보
     * @return 저장된 송금 요청 ID
     */
    Long create(final RemittanceRequest remittanceRequest);

    /**
     * 송금자 ID로 송금 요청 정보를 조회합니다.
     * @param senderId 조회할 송금자 ID
     * @return 조회된 송금 요청 정보
     */
    Optional<RemittanceRequest> findBySenderId(final Long senderId);

    /**
     * 송금 요청 ID로 송금 요청 정보를 조회합니다.
     * @param requestId 조회할 송금 요청 ID
     * @return 조회된 송금 요청 정보
     */
    Optional<RemittanceRequest> findById(long requestId);
}
