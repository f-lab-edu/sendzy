package com.donggi.sendzy.remittance.infrastructure.batch;

import com.donggi.sendzy.common.lock.NamedLockAcquisitionException;
import com.donggi.sendzy.support.IntegrationTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@DisplayName("RemittanceExpirationScheduler 네임드락 경합 테스트")
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@IntegrationTest
class RemittanceExpirationSchedulerLockTest {
    @Autowired
    private RemittanceExpirationScheduler scheduler;

    @Test
    void 여러_스레드가_동시에_expiredRequestBatch를_호출하면_한_개의_스레드만_락_획득에_성공한다() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failure = new AtomicInteger();

        // 실행할 task 정의
        Runnable task = () -> {
            try {
                scheduler.expirePendingRequests();
                success.incrementAndGet();
            } catch (NamedLockAcquisitionException e) {
                failure.incrementAndGet();
            } finally {
                latch.countDown();
            }
        };

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(task);
        }

        latch.await();

        // 1번만 성공, 나머지 9번은 실패
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(success.get()).isEqualTo(1);
                softly.assertThat(failure.get()).isEqualTo(threadCount - 1);
            }
        );
    }
}
