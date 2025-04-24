package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;

import java.util.List;
import java.util.Optional;

public interface RemittanceRequestRepository {

    /**
     * 송금 요청 정보를 저장합니다.
     *
     * @param remittanceRequest 저장할 송금 요청 정보
     * @return 저장된 송금 요청 ID
     */
    Long create(final RemittanceRequest remittanceRequest);

    /**
     * 송금 요청 ID로 송금 요청 정보를 조회합니다.
     *
     * @param requestId 조회할 송금 요청 ID
     * @return 조회된 송금 요청 정보
     */
    Optional<RemittanceRequest> findById(final long requestId);

    /**
     * 송금 요청 ID로 송금 요청을 조회하고, 조회된 송금 요청에 배타적 잠금(Exclusive Lock)을 겁니다.
     *
     * @param requestId 조회할 송금 요청 ID
     * @return 조회된 송금 요청 정보(Optional)
     */
    Optional<RemittanceRequest> findByIdForUpdate(final long requestId);

    /**
     * 송금 요청 정보를 업데이트합니다.
     *
     * @param remittanceRequest 업데이트할 송금 요청 정보
     */
    void update(final RemittanceRequest remittanceRequest);

    /**
     * 지정한 송신자가 보낸 송금 요청 중
     * 아직 처리되지 않은(PENDING 상태) 요청 목록을 모두 조회합니다.
     *
     * @param senderId 송금 요청을 보낸 회원 ID
     * @return PENDING 상태의 송금 요청 리스트
     */
    List<RemittanceRequest> findPendingRequestsBySenderId(final long senderId);
}
