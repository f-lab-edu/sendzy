package com.donggi.sendzy.remittance.domain.service;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import com.donggi.sendzy.remittance.exception.RemittanceRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemittanceRequestService {

    private final RemittanceRequestRepository remittanceRequestRepository;

    @Transactional
    public Long recordRequestAndGetId(final RemittanceRequest remittanceRequest) {
        remittanceRequestRepository.create(remittanceRequest);
        return remittanceRequest.getId();
    }

    @Transactional(readOnly = true)
    public RemittanceRequest getById(final long requestId) {
        return remittanceRequestRepository.findById(requestId)
            .orElseThrow(RemittanceRequestNotFoundException::new);
    }
}
