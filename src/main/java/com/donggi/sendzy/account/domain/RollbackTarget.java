package com.donggi.sendzy.account.domain;

/**
 * 송금 요청 만료 등으로 인해 계좌의 송금 대기 금액을 롤백할 때 사용되는 도메인 객체
 *
 * @param senderId 송금자 계좌 ID
 * @param amount 롤백할 송금 대기 금액
 */
public record RollbackTarget(long senderId, long amount) {
}
