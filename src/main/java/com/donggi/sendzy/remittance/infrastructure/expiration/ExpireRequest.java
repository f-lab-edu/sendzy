package com.donggi.sendzy.remittance.infrastructure.expiration;

import com.donggi.sendzy.remittance.domain.RemittanceRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public class ExpireRequest implements Delayed {

    private final RemittanceRequest remittanceRequest;

    @Override
    public long getDelay(final TimeUnit unit) {
        final var delay = Duration.between(LocalDateTime.now(), remittanceRequest.getExpiredAt()).toMillis();
        return unit.convert(delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(final Delayed o) {
        return Long.compare(getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }
}
