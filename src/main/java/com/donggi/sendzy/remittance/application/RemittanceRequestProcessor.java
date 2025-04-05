package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.application.AccountLockingService;
import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.domain.LockedAccounts;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
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
    private final AccountService accountService;

    @Transactional
    public void handleAcceptance(final long requestId, final long receiverId) {
        // 송금 요청 조회 및 상태 확인 (PENDING 여부)
        final var remittanceRequest = remittanceRequestService.getByIdForUpdate(requestId);
        validateReceiverAuthorityAndStatus(remittanceRequest, receiverId);

        // 송금자/수신자 계좌 락 + 조회 (ID 오름차순 → 데드락 방지)
        final LockedAccounts lockedAccounts = accountLockingService.getAccountsWithLockOrdered(remittanceRequest.getSenderId(), remittanceRequest.getReceiverId());
        final var senderAccount = lockedAccounts.senderAccount();
        final var receiverAccount = lockedAccounts.receiverAccount();

        // 이체 처리
        accountService.transfer(senderAccount, receiverAccount, remittanceRequest.getAmount());

        // 송금 요청 상태 변경 → ACCEPTED
        remittanceRequestService.accept(remittanceRequest);

        // 상태 변경 히스토리 저장
        recordStatus(remittanceRequest, RemittanceRequestStatus.ACCEPTED);
    }

    @Transactional
    public void handleRejection(final long requestId, final long receiverId) {
        // 송금 요청 조회 (락 획득)
        final var remittanceRequest = remittanceRequestService.getByIdForUpdate(requestId);

        // 수신자 권한 확인
        validateReceiverAuthorityAndStatus(remittanceRequest, receiverId);

        // 송금자 계좌 롤백 처리
        final var senderAccount = accountLockingService.getByMemberIdForUpdate(remittanceRequest.getSenderId());
        senderAccount.cancelWithdraw(remittanceRequest.getAmount());
        accountService.update(senderAccount);

        // 송금 요청 상태 변경 → REJECTED
        remittanceRequestService.reject(remittanceRequest);

        // 상태 변경 히스토리 저장
        recordStatus(remittanceRequest, RemittanceRequestStatus.REJECTED);
    }

    private void validateReceiverAuthorityAndStatus(final RemittanceRequest remittanceRequest, final long receiverId) {
        if (!remittanceRequest.getReceiverId().equals(receiverId)) {
            throw new AccessDeniedException("해당 송금 요청의 수신자만 처리할 수 있습니다.");
        }

        if (!remittanceRequest.isPending()) {
            throw new InvalidRemittanceRequestStatusException(remittanceRequest.getStatus());
        }
    }

    private void recordStatus(RemittanceRequest request, RemittanceRequestStatus status) {
        remittanceStatusHistoryService.recordStatusHistory(
            new RemittanceStatusHistory(
                request.getId(),
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount(),
                status
            )
        );
    }
}
