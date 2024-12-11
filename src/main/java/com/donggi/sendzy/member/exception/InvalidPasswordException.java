package com.donggi.sendzy.member.exception;

import com.donggi.sendzy.common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {

    public InvalidPasswordException(final String errorMessage) {
        super(errorMessage);
    }
}
