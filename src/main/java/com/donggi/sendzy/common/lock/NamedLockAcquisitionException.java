package com.donggi.sendzy.common.lock;

import com.donggi.sendzy.common.exception.BusinessException;

public class NamedLockAcquisitionException extends RuntimeException {

    public NamedLockAcquisitionException(final String lockName) {
        super("Lock 획득 실패: " + lockName);
    }
}
