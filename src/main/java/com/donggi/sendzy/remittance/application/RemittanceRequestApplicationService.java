package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.account.domain.AccountService;
import com.donggi.sendzy.member.domain.MemberService;
import com.donggi.sendzy.remittance.domain.RemittanceHistory;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.RemittanceRequestStatus;
import com.donggi.sendzy.remittance.domain.service.RemittanceHistoryService;
import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
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
    private final MemberService memberService;

    /**
     *
     * @param senderId 송금자 ID
     * @param receiverId 수신자 ID
     * @param amount 송금할 금액
     */
    @Transactional
    public void sendMoney(final Long senderId, final Long receiverId, final Long amount) {
        // 계좌 ID를 오름차순으로 정렬하여 일관된 순서로 Lock 확보
        List<Long> sortedIds = getSortedIds(senderId, receiverId);
        final Account firstAccount = accountService.findByIdForUpdate(sortedIds.get(0));
        final Account secondAccount = accountService.findByIdForUpdate(sortedIds.get(1));

        final Account senderAccount = senderId.equals(firstAccount.getId()) ? firstAccount : secondAccount;
        final Account receiverAccount = receiverId.equals(firstAccount.getId()) ? firstAccount : secondAccount;

        final var sender = memberService.findById(senderAccount.getMemberId());
        final var receiver = memberService.findById(receiverAccount.getMemberId());

        // 송금 처리 진행, 송금 내역 저장
        final var historyId = remittanceHistoryService.recordHistoryAndGetId(
            new RemittanceHistory(
                null,
                sender.getId(),
                sender.getEmail(),
                "",
                amount,
                senderAccount.getBalance()
            )
        );

        // 송금 요청
        final var requestId = remittanceRequestService.recordRequestAndGetId(
            new RemittanceRequest(
                sender.getId(),
                receiver.getId(),
                RemittanceRequestStatus.PENDING,
                amount
            )
        );

        // 송금 내역에 송금 ID 업데이트
        remittanceHistoryService.updateRequestId(historyId, requestId);

        // 송금자 출금 처리 (홀딩 잔액 업데이트)
        accountService.withdraw(senderAccount, amount);
    }

    private List<Long> getSortedIds(final Long senderId, final Long receiverId) {
        return Stream.of(senderId, receiverId)
            .sorted()
            .toList();
    }
}
