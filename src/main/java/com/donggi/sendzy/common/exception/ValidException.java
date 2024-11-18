package com.donggi.sendzy.common.exception;

public class ValidException extends BusinessException {

    private String errorMessage;

    public ValidException(final String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
