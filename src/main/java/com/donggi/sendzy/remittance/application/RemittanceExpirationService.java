package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.application.AccountLockingService;
import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.domain.RollbackTarget;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.domain.service.RemittanceStatusHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemittanceExpirationService {

    private final RemittanceRequestService remittanceRequestService;
    private final RemittanceStatusHistoryService remittanceStatusHistoryService;
    private final AccountLockingService accountLockingService;
    private final AccountService accountService;

    /**
     * 송금 요청 만료 처리 (REQUIRES_NEW 트랜잭션)
     * <p>
     * 송금 요청 수락/거절 흐름 중 만료 상태로 전환해야 하는 경우,
     * 예외 발생 여부와 관계없이 만료 처리와 히스토리 기록이 DB에 반영되어야 하므로
     * 별도의 트랜잭션으로 분리해 처리합니다.
     * <p>
     * - 기존 트랜잭션이 롤백되더라도, 해당 메서드는 독립적으로 커밋됩니다.
     * - Propagation.REQUIRES_NEW 설정을 통해 트랜잭션을 분리합니다.
     *
     * @param remittanceRequest 만료 처리할 송금 요청
     */
    @Async("remittanceExpireExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void expireRequest(final RemittanceRequest remittanceRequest) {
        // 송금 요청 상태 변경 → EXPIRED
        remittanceRequestService.expire(remittanceRequest);

        // 송금자 계좌 롤백 처리
        rollbackHoldAmount(remittanceRequest.getSenderId(), remittanceRequest.getAmount());

        // 히스토리 저장
        remittanceStatusHistoryService.recordStatusHistory(
            RemittanceStatusHistory.forExpiration(
                remittanceRequest.getId(),
                remittanceRequest.getSenderId(),
                remittanceRequest.getReceiverId(),
                remittanceRequest.getAmount()
            )
        );
    }

    @Transactional
    public void expireRequestBatch(final int chunkSize, final LocalDateTime now) {
        // 만료된 송금 요청 목록 조회
        final var expiredRequests = remittanceRequestService.getExpiredRequest(chunkSize, now);

        log.info("[만료 배치] 만료 대상 요청 수: {}", expiredRequests.size());

        if (expiredRequests.isEmpty()) {
            log.info("[만료 배치] 만료된 송금 요청 없음");
            return;
        }

        // 만료된 송금 요청에 대해 송금자의 출금 대기 금액 롤백
        rollbackPendingAmounts(expiredRequests);

        // 송금 요청 만료 처리
        expireRequests(expiredRequests);

        // 만료된 송금 요청 상태 기록
        recordStatusHistory(expiredRequests);

        log.info("[만료 배치 완료] 총 {}건의 요청을 만료 처리했습니다.", expiredRequests.size());
    }

    private void rollbackPendingAmounts(final List<RemittanceRequest> requests) {
        final var rollbackTargets = getRollbackTargets(requests);
        accountService.rollbackHoldAmounts(rollbackTargets);
    }

    private void expireRequests(final List<RemittanceRequest> requests) {
        requests.forEach(RemittanceRequest::expire);
        remittanceRequestService.bulkUpdate(requests);
    }

    private void recordStatusHistory(final List<RemittanceRequest> requests) {
        final var histories = requests.stream()
            .map(r -> RemittanceStatusHistory.forExpiration(
                r.getId(),
                r.getSenderId(),
                r.getReceiverId(),
                r.getAmount()
            ))
            .toList();
        remittanceStatusHistoryService.bulkInsert(histories);
    }

    private List<RollbackTarget> getRollbackTargets(final List<RemittanceRequest> expiredRequests) {
        return expiredRequests.stream()
            .map(r -> new RollbackTarget(r.getSenderId(), r.getAmount()))
            .toList();
    }

    private void rollbackHoldAmount(final long senderId, final long amount) {
        final var senderAccount = accountLockingService.getByMemberIdForUpdate(senderId);
        accountService.cancelWithdraw(senderAccount, amount);
    }
}
