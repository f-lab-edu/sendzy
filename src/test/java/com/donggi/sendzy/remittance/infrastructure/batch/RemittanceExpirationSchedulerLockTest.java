package com.donggi.sendzy.remittance.infrastructure.batch;

import com.donggi.sendzy.common.lock.NamedLockAcquisitionException;
import com.donggi.sendzy.support.IntegrationTest;
import com.donggi.sendzy.remittance.application.RemittanceExpirationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DisplayName("RemittanceExpirationScheduler 네임드락 경합 테스트")
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@IntegrationTest
class RemittanceExpirationSchedulerLockTest {
    @Autowired
    private RemittanceExpirationScheduler scheduler;

    @SpyBean
    private RemittanceExpirationService expirationService;

    @Test
    void 두_스레드가_동시에_expiredRequestBatch를_호출하면_한_스레드는_락_획득에_실패한다() throws InterruptedException {
        doAnswer(invocation -> {
            Thread.sleep(2_000);
            return invocation.callRealMethod();
        }).when(expirationService).expireRequestBatch(anyInt(), any());

        ExecutorService exec = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failure = new AtomicInteger();

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

        exec.submit(task);
        Thread.sleep(100);
        exec.submit(task);

        latch.await();

        assertThat(success.get()).isEqualTo(1);
        assertThat(failure.get()).isEqualTo(1);
        verify(expirationService, times(1)).expireRequestBatch(anyInt(), any());
    }
}
