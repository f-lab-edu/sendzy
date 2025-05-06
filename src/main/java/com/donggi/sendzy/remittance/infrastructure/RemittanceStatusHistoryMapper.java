package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceStatusHistoryRepository;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceStatusHistoryRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RemittanceStatusHistoryMapper extends RemittanceStatusHistoryRepository, TestRemittanceStatusHistoryRepository {

    Long create(final RemittanceStatusHistory remittanceStatusHistory);

    Optional<RemittanceStatusHistory> findByRequestId(final long requestId);

    void bulkInsert(List<RemittanceStatusHistory> histories);
}
