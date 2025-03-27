package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;

import java.util.List;

public interface RemittanceHistoryRepository {

    /**
     * 송금 내역을 저장합니다.
     * @param remittanceHistory 저장할 송금 내역
     * @return 저장된 송금 ID
     */
    Long create(final RemittanceHistory remittanceHistory);

    /**
     * 송금 내역을 조회합니다.
     * @param id 조회할 송금 ID
     * @return 조회된 송금 내역
     */
    RemittanceHistory findById(final long id);

    /**
     * 송금자 ID로 송금 내역 목록을 조회합니다.
     * @param senderId 송금자 ID
     * @return 조회된 송금 내역 목록
     */
    List<RemittanceHistory> listBySenderId(final long senderId);
}
