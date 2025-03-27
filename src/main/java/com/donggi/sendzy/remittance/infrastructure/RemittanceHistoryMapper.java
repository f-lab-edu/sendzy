package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceHistoryRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RemittanceHistoryMapper extends RemittanceHistoryRepository {

    Long create(final RemittanceHistory remittanceHistory);

    RemittanceHistory findById(final long id);

    List<RemittanceHistory> listBySenderId(final long senderId);

    Optional<RemittanceHistory> findByRequestId(final long requestId);

    void update(final RemittanceHistory remittanceHistory);
}
