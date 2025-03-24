package com.donggi.sendzy.remittance.domain.service;

import com.donggi.sendzy.remittance.domain.RemittanceStatusHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemittanceStatusHistoryService {

    private final RemittanceStatusHistoryRepository remittanceStatusHistoryRepository;

    @Transactional(readOnly = true)
    public RemittanceStatusHistory findBySenderId(final Long senderId) {
        return remittanceStatusHistoryRepository.findBySenderId(senderId)
            .orElseThrow(() -> new IllegalArgumentException("RemittanceStatusHistory not found"));
    }

    @Transactional
    public void recordStatusHistory(final RemittanceStatusHistory remittanceStatusHistory) {
        remittanceStatusHistoryRepository.create(remittanceStatusHistory);
    }
}
