package com.donggi.sendzy.remittance.infrastructure.batch;

import com.donggi.sendzy.common.lock.NamedLockTemplate;
import com.donggi.sendzy.remittance.application.RemittanceExpirationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.donggi.sendzy.common.lock.NamedLockPolicy.REMITTANCE_EXPIRE_BATCH;

@Slf4j
@RequiredArgsConstructor
@Component
public class RemittanceExpirationScheduler {

    private static final int CHUNK_SIZE = 1_000;

    private final RemittanceExpirationService remittanceExpirationService;
    private final NamedLockTemplate namedLockTemplate;

    @Scheduled(cron = "0 */5 * * * *")
    public void expirePendingRequests() {
        final var now = LocalDateTime.now();

        log.info("[만료 배치 시작] 실행 시각: {}", now);

        namedLockTemplate.executeWithLock(
            REMITTANCE_EXPIRE_BATCH.getKey(),
            REMITTANCE_EXPIRE_BATCH.getTimeout(),
            () -> {
                try {
                    log.info("[배치 잠금 획득 성공] 만료 배치 실행 시작");
                    remittanceExpirationService.expireRequestBatch(CHUNK_SIZE, now);
                    log.info("[만료 배치 완료] 완료 시각: {}", LocalDateTime.now());
                } catch (Exception e) {
                    log.error("[만료 배치 실패] 오류 발생: {}", e.getMessage(), e);
                    throw e;
                }
            }
        );
    }
}
