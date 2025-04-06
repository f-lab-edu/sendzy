package com.donggi.sendzy.remittance.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회원 간 송금 요청 정보를 기록하는 도메인 모델입니다.
 * 송금 요청의 상태와 함께 송금자와 수신자의 정보를 보관합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceRequest {

    private static final long EXPIRATION_DAYS = 3;

    private Long id;
    private Long senderId;
    private Long receiverId;
    private RemittanceRequestStatus status;
    private Long amount;
    private LocalDateTime createdAt;

    public RemittanceRequest(
        final Long senderId,
        final Long receiverId,
        final RemittanceRequestStatus status,
        final Long amount
    ) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 테스트용 생성자입니다.
     * 접근제어자를 package-private 으로 설정하여 외부에서 직접 생성할 수 없도록 합니다.
     */
    RemittanceRequest(
        final Long senderId,
        final Long receiverId,
        final RemittanceRequestStatus status,
        final Long amount,
        final LocalDateTime createdAt
    ) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public boolean isPending() {
        return this.status == RemittanceRequestStatus.PENDING;
    }

    public boolean isExpired(final LocalDateTime now) {
        return createdAt.plusDays(EXPIRATION_DAYS).isBefore(now);
    }

    public void accept() {
        status = this.status.accept();
    }

    public void reject() {
        status = this.status.reject();
    }

    public void expire() {
        status = this.status.expire();
    }
}
