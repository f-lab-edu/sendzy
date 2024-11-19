package com.donggi.sendzy.common.exception;

import com.donggi.sendzy.member.exception.EmailDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientErrorControllerAdvice {

    /**
     * 클라이언트가 입력한 이메일이 이미 존재하는 경우
     */
    @ExceptionHandler(EmailDuplicatedException.class)
    public ProblemDetail handleEmailDuplicatedException(EmailDuplicatedException e) {
        return ProblemDetail.forStatus(HttpStatus.CONFLICT);
    }
}
