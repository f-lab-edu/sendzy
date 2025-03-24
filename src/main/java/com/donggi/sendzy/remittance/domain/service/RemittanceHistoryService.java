package com.donggi.sendzy.remittance.domain.service;

import com.donggi.sendzy.remittance.domain.RemittanceHistory;
import com.donggi.sendzy.remittance.domain.repository.RemittanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemittanceHistoryService {

    private final RemittanceHistoryRepository remittanceHistoryRepository;

    @Transactional
    public Long recordHistoryAndGetId(final RemittanceHistory remittanceHistory) {
        remittanceHistoryRepository.create(remittanceHistory);
        return remittanceHistory.getId();
    }

    @Transactional
    public void updateRequestId(final Long historyId, final Long requestId) {
        final var remittanceHistory = remittanceHistoryRepository.findById(historyId);
        remittanceHistory.updateRequestId(requestId);
    }

    @Transactional(readOnly = true)
    public RemittanceHistory findBySenderId(final Long senderId) {
        return remittanceHistoryRepository.findBySenderId(senderId);
    }
}
