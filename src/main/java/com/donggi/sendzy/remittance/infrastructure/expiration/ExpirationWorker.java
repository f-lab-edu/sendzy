package com.donggi.sendzy.remittance.infrastructure.expiration;

import com.donggi.sendzy.remittance.application.RemittanceExpirationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpirationWorker {

    private final ExpirationQueueManager expirationQueueManager;
    private final RemittanceExpirationService remittanceExpirationService;

    /**
     * 큐에서 만료된 요청을 가져와 만료 처리를 수행하는 스레드를 시작합니다.
     *
     * while (!Thread.currentThread().isInterrupted()) 조건을 사용해 graceful shutdown을 지원합니다.
     */
    @PostConstruct
    public void start() {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                final var expireRequest = expirationQueueManager.take();
                remittanceExpirationService.expireRequest(expireRequest.getRemittanceRequest());
            }
        });
        worker.setDaemon(true);
        worker.start();
    }
}
