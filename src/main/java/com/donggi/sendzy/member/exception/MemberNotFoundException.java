package com.donggi.sendzy.member.exception;

import com.donggi.sendzy.common.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(String message) {
        super(message);
    }
}
