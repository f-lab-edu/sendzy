package com.donggi.sendzy.remittance.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 송금 내역을 기록하기 위한 도메인 모델입니다.
 * 송금 요청과 연관된 상세 이력을 보관하며, 송금 후의 잔액 상태도 기록합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceHistory {

    private Long id;
    private Long remittanceRequestId;
    private Long memberId;
    private String email;
    private String description;
    private Long amount;
    private Long balance;
    private LocalDateTime createdAt;

    public RemittanceHistory(
        final Long remittanceRequestId,
        final Long memberId,
        final String email,
        final String description,
        final Long amount,
        final Long balance
    ) {
        this.remittanceRequestId = remittanceRequestId;
        this.memberId = memberId;
        this.email = email;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    public void updateRequestId(final Long requestId) {
        this.remittanceRequestId = requestId;
    }
}
