package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.application.AccountLockingService;
import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;
import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.domain.service.RemittanceStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void expireRequest(final RemittanceRequest remittanceRequest) {
        // 송금 요청 상태 변경 → EXPIRED
        remittanceRequestService.expire(remittanceRequest);

        // 송금자 계좌 롤백 처리
        rollbackHoldAmount(remittanceRequest.getSenderId(), remittanceRequest.getAmount());

        // 히스토리 저장
        recordStatusHistory(remittanceRequest);
    }

    private void recordStatusHistory(final RemittanceRequest request) {
        remittanceStatusHistoryService.recordStatusHistory(
            new RemittanceStatusHistory(
                request.getId(),
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount(),
                RemittanceRequestStatus.EXPIRED
            )
        );
    }

    private void rollbackHoldAmount(final long senderId, final long amount) {
        final var senderAccount = accountLockingService.getByMemberIdForUpdate(senderId);
        accountService.cancelWithdraw(senderAccount, amount);
    }
}
