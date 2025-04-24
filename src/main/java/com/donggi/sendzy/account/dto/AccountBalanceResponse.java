package com.donggi.sendzy.account.dto;

import com.donggi.sendzy.account.domain.Account;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;

import java.util.List;

public record AccountBalanceResponse(
    long balance,
    long holdAmount,
    long availableAmount,
    List<HoldDetail> holdDetails
) {
    /**
     * 잔액 조회 응답 객체
     * @param account 계좌 정보
     * @param activeRequests 진행중인 송금 요청 리스트
     * @return AccountBalanceResponse
     */
    public static AccountBalanceResponse from(final Account account, final List<RemittanceRequest> activeRequests) {
        final var holdAmount = activeRequests.stream()
            .mapToLong(RemittanceRequest::getAmount)
            .sum();

        final var availableAmount = account.getBalance() - holdAmount;

        final List<HoldDetail> holdDetails = activeRequests.stream()
            .map(request -> new HoldDetail(request.getReceiverId(), request.getAmount()))
            .toList();

        return new AccountBalanceResponse(account.getBalance(), holdAmount, availableAmount, holdDetails);
    }

    private record HoldDetail(long receiverId, long amount) {
    }
}
