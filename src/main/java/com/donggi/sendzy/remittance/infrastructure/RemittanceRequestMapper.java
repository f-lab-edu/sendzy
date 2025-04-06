package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceRequestRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RemittanceRequestMapper extends RemittanceRequestRepository, TestRemittanceRequestRepository {

    Long create(final RemittanceRequest remittanceRequest);

    Optional<RemittanceRequest> findById(final long requestId);

    Optional<RemittanceRequest> findByIdForUpdate(final long requestId);

    void deleteAll();
}
