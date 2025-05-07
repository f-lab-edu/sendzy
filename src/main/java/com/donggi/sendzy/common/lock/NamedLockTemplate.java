package com.donggi.sendzy.common.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class NamedLockTemplate {

    private final NamedLockMapper namedLockMapper;

    public <T> T executeWithLock(final String lockName, final int timeout, final Supplier<T> action) {
        int result = namedLockMapper.getLock(lockName, timeout);
        if (result != 1) {
            throw new NamedLockAcquisitionException(lockName);
        }
        try {
            return action.get();
        } finally {
            namedLockMapper.releaseLock(lockName);
        }
    }

    public void executeWithLock(final String lockName, final int timeout, final Runnable action) {
        int result = namedLockMapper.getLock(lockName, timeout);
        if (result != 1) {
            throw new NamedLockAcquisitionException(lockName);
        }
        try {
            action.run();
        } finally {
            namedLockMapper.releaseLock(lockName);
        }
    }
}
