package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceStatusHistoryRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RemittanceStatusHistoryMapper extends RemittanceStatusHistoryRepository {

    Long create(final RemittanceStatusHistory remittanceStatusHistory);

    Optional<RemittanceStatusHistory> findByRequestId(final long requestId);
}
