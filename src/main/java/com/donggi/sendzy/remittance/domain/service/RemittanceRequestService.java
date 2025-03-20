package com.donggi.sendzy.remittance.domain.service;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemittanceRequestService {

    RemittanceRequestRepository remittanceRequestRepository;

    @Transactional
    public Long recordRequestAndGetId(final RemittanceRequest remittanceRequest) {
        return remittanceRequestRepository.create(remittanceRequest);
    }
}
