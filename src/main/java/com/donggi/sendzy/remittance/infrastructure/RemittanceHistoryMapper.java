package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.repository.RemittanceHistoryRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemittanceHistoryMapper extends RemittanceHistoryRepository {
}
