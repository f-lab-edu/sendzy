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
}
