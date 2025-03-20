package com.donggi.sendzy.member.exception;

import com.donggi.sendzy.common.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(final Long memberId) {
        super("존재하지 않는 회원입니다. memberId: " + memberId);
    }
}
