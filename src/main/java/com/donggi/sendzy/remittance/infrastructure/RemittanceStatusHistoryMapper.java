package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.repository.RemittanceStatusHistoryRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemittanceStatusHistoryMapper extends RemittanceStatusHistoryRepository {
}
