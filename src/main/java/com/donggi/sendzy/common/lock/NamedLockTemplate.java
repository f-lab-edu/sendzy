package com.donggi.sendzy.common.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class NamedLockTemplate {

    private final NamedLockMapper namedLockMapper;

    public <T> T executeWithLock(final String lockName, final int timeout, final Supplier<T> action) {
        try {
            namedLockMapper.getLock(lockName, timeout);
            return action.get();
        } finally {
            namedLockMapper.releaseLock(lockName);
        }
    }

    public void executeWithLock(final String lockName, final int timeout, final Runnable action) {
        try {
            namedLockMapper.getLock(lockName, timeout);
            action.run();
        } finally {
            namedLockMapper.releaseLock(lockName);
        }
    }
}
