package com.donggi.sendzy.remittance.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 송금 요청 상태의 변경 이력을 기록하는 도메인 모델입니다.
 * 요청 상태의 변경과 관련된 시점(생성, 수락, 만료)을 관리합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceStatusHistory {

    private Long id;
    private Long requestId;
    private Long senderId;
    private Long receiverId;
    private Long amount;
    private RemittanceRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime acceptedAt;

    // 공통 초기화 메서드
    private static RemittanceStatusHistory base(
        final Long requestId,
        final Long senderId,
        final Long receiverId,
        final Long amount,
        final RemittanceRequestStatus status
    ) {
        final RemittanceStatusHistory history = new RemittanceStatusHistory();
        history.requestId = requestId;
        history.senderId = senderId;
        history.receiverId = receiverId;
        history.amount = amount;
        history.status = status;
        history.createdAt = LocalDateTime.now();
        return history;
    }

    public static RemittanceStatusHistory forExpiration(
        final Long requestId,
        final Long senderId,
        final Long receiverId,
        final Long amount
    ) {
        final RemittanceStatusHistory history = base(requestId, senderId, receiverId, amount, RemittanceRequestStatus.EXPIRED);
        history.expiredAt = LocalDateTime.now();
        return history;
    }

    public static RemittanceStatusHistory forAcceptance(
        final Long requestId,
        final Long senderId,
        final Long receiverId,
        final Long amount
    ) {
        final RemittanceStatusHistory history = base(requestId, senderId, receiverId, amount, RemittanceRequestStatus.ACCEPTED);
        history.acceptedAt = LocalDateTime.now();
        return history;
    }

    public static RemittanceStatusHistory forRejection(
        final Long requestId,
        final Long senderId,
        final Long receiverId,
        final Long amount
    ) {
        return base(requestId, senderId, receiverId, amount, RemittanceRequestStatus.REJECTED);
    }

    public static RemittanceStatusHistory forPending(
        final Long requestId,
        final Long senderId,
        final Long receiverId,
        final Long amount
    ) {
        return base(requestId, senderId, receiverId, amount, RemittanceRequestStatus.PENDING);
    }
}
