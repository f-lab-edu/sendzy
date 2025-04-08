package com.donggi.sendzy.remittance.infrastructure.expiration;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import com.donggi.sendzy.remittance.domain.repository.RemittanceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpirationQueueRestorer implements ApplicationRunner {

    private final RemittanceRequestRepository remittanceRequestRepository;
    private final ExpirationQueueManager expirationQueueManager;

    @Override
    public void run(ApplicationArguments args) {
        final List<RemittanceRequest> requests = remittanceRequestRepository.findPendingRequests();
        for (final var request : requests) {
            expirationQueueManager.register(request);
        }
    }
}
