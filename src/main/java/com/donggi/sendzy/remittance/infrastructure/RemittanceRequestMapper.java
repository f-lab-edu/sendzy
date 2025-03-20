package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemittanceRequestMapper extends RemittanceRequestRepository {
}
