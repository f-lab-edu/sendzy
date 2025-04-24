package com.donggi.sendzy.account.application;

import com.donggi.sendzy.remittance.application.RemittanceExpirationService;
import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@RequiredArgsConstructor
@Component
public class ExpirationAsyncProcessor {

    private final RemittanceExpirationService remittanceExpirationService;

    @Async
    public void expire(final RemittanceRequest request) {
        remittanceExpirationService.expireRequest(request);
    }
}
