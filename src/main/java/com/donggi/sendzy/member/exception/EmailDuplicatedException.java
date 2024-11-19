package com.donggi.sendzy.member.exception;

import com.donggi.sendzy.common.exception.BusinessException;

public class EmailDuplicatedException extends BusinessException {

    public EmailDuplicatedException(final String errorMessage) {
        super(errorMessage);
    }
}
