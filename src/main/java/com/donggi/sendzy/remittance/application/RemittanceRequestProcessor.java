package com.donggi.sendzy.remittance.application;

import com.donggi.sendzy.remittance.domain.service.RemittanceRequestService;
import com.donggi.sendzy.remittance.exception.InvalidRemittanceRequestStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RemittanceRequestProcessor {

    private final RemittanceRequestService remittanceRequestService;

    @Transactional
    public void handleAcceptance(final long requestId) {
        final var remittanceRequest = remittanceRequestService.getByIdForUpdate(requestId);

        if (!remittanceRequest.isPending()) {
            throw new InvalidRemittanceRequestStatusException(remittanceRequest.getStatus());
        }

        remittanceRequestService.accept(remittanceRequest);
    }


    public void handleRejection() {
        // 요청 거절
    }


    public void handleExpiration() {
        // 요청 만료
    }
}
