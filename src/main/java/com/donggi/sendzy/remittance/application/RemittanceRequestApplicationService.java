package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.application.AccountLockingService;
import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.account.domain.LockedAccounts;
import com.donggi.sendzy.common.exception.BadRequestException;
import com.donggi.sendzy.member.domain.Member;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.remittance.domain.RemittanceHistory;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;
import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.service.RemittanceHistoryService;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.domain.service.RemittanceStatusHistoryService;
import com.donggi.sendzy.remittance.infrastructure.expiration.ExpirationQueueManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemittanceRequestApplicationService {

    private final RemittanceRequestService remittanceRequestService;
    private final RemittanceHistoryService remittanceHistoryService;
    private final RemittanceStatusHistoryService remittanceStatusHistoryService;
    private final ExpirationQueueManager expirationQueueManager;
    private final AccountService accountService;
    private final AccountLockingService accountLockingService;
    private final MemberService memberService;

    /**
     * 송금 요청
     * @param senderId 송금자 ID
     * @param receiverId 수신자 ID
     * @param amount 송금할 금액
     */
    @Transactional
    public long createRemittanceRequest(final Long senderId, final Long receiverId, final Long amount) {
        validateSenderAndReceiver(senderId, receiverId);

        // 계좌 ID를 오름차순으로 정렬하여 일관된 순서로 Lock 확보
        final LockedAccounts lockedAccounts = accountLockingService.getAccountsWithLockOrdered(senderId, receiverId);
        final Account senderAccount = lockedAccounts.senderAccount();
        final Account receiverAccount = lockedAccounts.receiverAccount();

        final var sender = memberService.findById(senderAccount.getMemberId());
        final var receiver = memberService.findById(receiverAccount.getMemberId());

        // 송금 처리
        return processRemittance(sender, receiver, senderAccount, amount);
    }

    private long processRemittance(final Member sender, final Member receiver, final Account senderAccount, final Long amount) {
        // 송금 내역 저장
        final var historyId = recordRemittanceHistory(sender, senderAccount, amount);

        // 송금 요청 저장
        final var requestId = recordRemittanceRequest(sender, receiver, amount);

        // 송금 상태 내역 저장
        recordRemittanceStatusHistory(sender, receiver, amount, requestId);

        // 송금 내역에 송금 ID 업데이트
        remittanceHistoryService.updateRequestId(historyId, requestId);

        // 송금자 출금 처리 (홀딩 잔액 업데이트)
        accountService.withdraw(senderAccount, amount);

        return requestId;
    }

    private void recordRemittanceStatusHistory(final Member sender, final Member receiver, final Long amount, final Long requestId) {
        remittanceStatusHistoryService.recordStatusHistory(new RemittanceStatusHistory(
            requestId,
            sender.getId(),
            receiver.getId(),
            amount,
            RemittanceRequestStatus.PENDING
        ));
    }

    private long recordRemittanceRequest(final Member sender, final Member receiver, final Long amount) {
        final var request = new RemittanceRequest(sender.getId(), receiver.getId(), RemittanceRequestStatus.PENDING, amount);
        expirationQueueManager.register(request);
        return remittanceRequestService.recordRequestAndGetId(request);
    }

    private long recordRemittanceHistory(final Member sender, final Account senderAccount, final Long amount) {
        return remittanceHistoryService.recordHistoryAndGetId(
            new RemittanceHistory(
                null,
                sender.getId(),
                sender.getEmail(),
                "",
                amount,
                senderAccount.getBalance()
            )
        );
    }

    private void validateSenderAndReceiver(final Long senderId, final Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new BadRequestException("송금자와 수신자가 동일합니다.");
        }
    }
}
