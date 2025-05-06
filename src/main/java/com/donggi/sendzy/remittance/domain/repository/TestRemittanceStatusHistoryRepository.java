package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;

import java.util.List;

public interface TestRemittanceStatusHistoryRepository {

    /**
     * 저장된 모든 송금 상태 변경 내역을 삭제합니다.
     */
    void deleteAll();

    /**
     * 저장된 모든 송금 상태 변경 내역을 조회합니다.
     *
     * @return 저장된 모든 송금 상태 변경 내역
     */
    List<RemittanceStatusHistory> findAll();
}
