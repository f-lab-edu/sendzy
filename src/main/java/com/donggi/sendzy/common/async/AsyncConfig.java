package com.donggi.sendzy.common.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Bean("remittanceExpireExecutor")
    public ThreadPoolTaskExecutor remittanceExpireExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(4);          // 항상 살아있을 스레드
        taskExecutor.setMaxPoolSize(16);          // 최대 확장
        taskExecutor.setQueueCapacity(5_000);     // 대기 큐

        taskExecutor.setThreadNamePrefix("expire-");
        taskExecutor.setAwaitTerminationSeconds(30);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
            log.error("[async] {}({}) 실패", method.getName(), Arrays.toString(params), ex);
    }

    @Override
    public Executor getAsyncExecutor() {
        // TODO : 기본 Executor 설정
        return new SimpleAsyncTaskExecutor();
    }
}
