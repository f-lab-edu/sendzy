package com.donggi.sendzy.remittance.domain.service;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;
import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceStatusHistoryRepository;
import com.donggi.sendzy.remittance.exception.RemittanceStatusHistoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemittanceStatusHistoryService {

    private final RemittanceStatusHistoryRepository remittanceStatusHistoryRepository;

    @Transactional(readOnly = true)
    public RemittanceStatusHistory findBySenderId(final long senderId) {
        return remittanceStatusHistoryRepository.findBySenderId(senderId)
            .orElseThrow(RemittanceStatusHistoryNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public RemittanceStatusHistory getByRequestId(final long requestId) {
        return remittanceStatusHistoryRepository.findByRequestId(requestId)
            .orElseThrow(RemittanceStatusHistoryNotFoundException::new);
    }

    @Transactional
    public void recordStatusHistory(final RemittanceStatusHistory remittanceStatusHistory) {
        remittanceStatusHistoryRepository.create(remittanceStatusHistory);
    }
}
