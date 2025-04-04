package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.application.AccountLockingService;
import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;
import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.domain.service.RemittanceStatusHistoryService;
import com.donggi.sendzy.remittance.exception.InvalidRemittanceRequestStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RemittanceRequestProcessor {

    private final RemittanceRequestService remittanceRequestService;
    private final AccountLockingService accountLockingService;
    private final RemittanceStatusHistoryService remittanceStatusHistoryService;

    @Transactional
    public void handleAcceptance(final long requestId, final long receiverId) {
        // 송금 요청 조회 및 상태 확인 (PENDING 여부)
        final var remittanceRequest = remittanceRequestService.getByIdForUpdate(requestId);
        if (!remittanceRequest.getReceiverId().equals(receiverId)) {
            throw new AccessDeniedException("요청 수락 권한이 없습니다.");
        }

        if (!remittanceRequest.isPending()) {
            throw new InvalidRemittanceRequestStatusException(remittanceRequest.getStatus());
        }

        // 송금자/수신자 계좌 락 + 조회 (ID 오름차순 → 데드락 방지)
        final var accounts = accountLockingService.getAccountsWithLockOrdered(remittanceRequest.getSenderId(), remittanceRequest.getReceiverId());
        final var senderAccount = remittanceRequest.getSenderId().equals(accounts.get(0).getMemberId()) ? accounts.get(0) : accounts.get(1);
        final var receiverAccount = remittanceRequest.getReceiverId().equals(accounts.get(0).getMemberId()) ? accounts.get(0) : accounts.get(1);

        // 출금/입금 처리
        senderAccount.commitWithdraw(remittanceRequest.getAmount());
        receiverAccount.deposit(remittanceRequest.getAmount());

        // 송금 요청 상태 변경 → ACCEPTED
        remittanceRequestService.accept(remittanceRequest);

        // 상태 변경 히스토리 저장
        remittanceStatusHistoryService.recordStatusHistory(
            new RemittanceStatusHistory(
                remittanceRequest.getId(),
                remittanceRequest.getSenderId(),
                remittanceRequest.getReceiverId(),
                remittanceRequest.getAmount(),
                RemittanceRequestStatus.ACCEPTED
            ));
    }


    public void handleRejection() {
        // 요청 거절
    }


    public void handleExpiration() {
        // 요청 만료
    }
}
