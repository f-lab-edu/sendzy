package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RemittanceRequestApplicationService {

    private final AccountService accountService;
    private final RemittanceHistoryService remittanceHistoryService;
    private final RemittanceRequestService remittanceRequestService;
    private final RemittanceStatusHistoryService remittanceStatusHistoryService;
    private final MemberService memberService;

    /**
     * 송금 요청
     * @param senderId 송금자 ID
     * @param receiverId 수신자 ID
     * @param amount 송금할 금액
     */
    @Transactional
    public void sendMoney(final Long senderId, final Long receiverId, final Long amount) {
        validateSenderAndReceiver(senderId, receiverId);

        // 계좌 ID를 오름차순으로 정렬하여 일관된 순서로 Lock 확보
        List<Long> sortedIds = getSortedIds(senderId, receiverId);
        final Account firstAccount = accountService.findByIdForUpdate(sortedIds.get(0));
        final Account secondAccount = accountService.findByIdForUpdate(sortedIds.get(1));

        final Account senderAccount = senderId.equals(firstAccount.getMemberId()) ? firstAccount : secondAccount;
        final Account receiverAccount = receiverId.equals(firstAccount.getMemberId()) ? firstAccount : secondAccount;

        final var sender = memberService.findById(senderAccount.getMemberId());
        final var receiver = memberService.findById(receiverAccount.getMemberId());

        // 송금 처리
        processRemittance(sender, receiver, senderAccount, amount);
    }

    private void processRemittance(final Member sender, final Member receiver, final Account senderAccount, final Long amount) {
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

    private Long recordRemittanceRequest(final Member sender, final Member receiver, final Long amount) {
        return remittanceRequestService.recordRequestAndGetId(
            new RemittanceRequest(
                sender.getId(),
                receiver.getId(),
                RemittanceRequestStatus.PENDING,
                amount
            )
        );
    }

    private Long recordRemittanceHistory(final Member sender, final Account senderAccount, final Long amount) {
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

    private List<Long> getSortedIds(final Long senderId, final Long receiverId) {
        return Stream.of(senderId, receiverId)
            .sorted()
            .toList();
    }
}
