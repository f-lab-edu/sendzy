package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;

import java.time.LocalDateTime;
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

    /**
     * 현재 시점 기준으로 만료 대상인 송금 요청을
     * ID 순으로 페이지 단위 조회합니다.
     *
     * @param lastId    직전 청크의 마지막 요청 ID
     * @param chunkSize 조회할 최대 요청 수
     * @param now       기준 시각
     * @return 만료 대상 송금 요청 리스트
     */
    List<RemittanceRequest> findExpiredRequest(final long lastId, final int chunkSize, final LocalDateTime now);

    /**
     * 만료된 송금 요청을 모두 만료 처리합니다.
     *
     * @param ids 만료 처리할 송금 요청 ID 리스트
     */
    void expireAllByIds(List<Long> ids);
}
