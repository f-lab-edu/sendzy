package com.donggi.sendzy.remittance.infrastructure;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import com.donggi.sendzy.remittance.domain.repository.TestRemittanceRequestRepository;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface RemittanceRequestMapper extends RemittanceRequestRepository, TestRemittanceRequestRepository {

    Long create(final RemittanceRequest remittanceRequest);

    Optional<RemittanceRequest> findById(final long requestId);

    Optional<RemittanceRequest> findByIdForUpdate(final long requestId);

    void deleteAll();

    List<RemittanceRequest> findPendingRequestsBySenderId(final long senderId);

    List<RemittanceRequest> findExpiredRequest(final long lastId, final int chunkSize, final LocalDateTime now);

    void expireAllByIds(List<Long> ids);
}
