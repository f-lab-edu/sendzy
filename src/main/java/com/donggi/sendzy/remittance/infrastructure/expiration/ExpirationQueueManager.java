package com.donggi.sendzy.remittance.infrastructure.expiration;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

@Component
public class ExpirationQueueManager {

    private final DelayQueue<ExpireRequest> expirationQueue = new DelayQueue<>();

    public void register(final RemittanceRequest request) {
        expirationQueue.offer(new ExpireRequest(request));
    }

    public ExpireRequest take() {
        try {
            return expirationQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ExpirationQueueInterruptedException();
        }
    }
}
