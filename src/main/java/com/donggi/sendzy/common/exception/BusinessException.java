package com.donggi.sendzy.common.exception;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(final String message) {
        super(message);
    }
}
