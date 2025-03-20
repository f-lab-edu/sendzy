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

    private Long id;
    private Long senderId;
    private Long receiverId;
    private RemittanceRequestStatus status;
    private Long amount;
    private LocalDateTime createdAt;
}
