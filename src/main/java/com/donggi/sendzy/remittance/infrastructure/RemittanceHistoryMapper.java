package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceHistoryRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemittanceHistoryMapper extends RemittanceHistoryRepository {

    Long create(final RemittanceHistory remittanceHistory);

    RemittanceHistory findById(final Long id);

    RemittanceHistory findBySenderId(final Long senderId);
}
