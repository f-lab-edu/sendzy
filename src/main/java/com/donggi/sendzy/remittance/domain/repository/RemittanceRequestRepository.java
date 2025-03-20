package com.donggi.sendzy.remittance.domain.repository;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;

public interface RemittanceRequestRepository {

    /**
     * 송금 요청 정보를 저장합니다.
     * @param remittanceRequest 저장할 송금 요청 정보
     * @return 저장된 송금 요청 ID
     */
    Long create(RemittanceRequest remittanceRequest);
}
