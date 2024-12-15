package com.donggi.sendzy.common.exception;

import com.donggi.sendzy.member.exception.EmailDuplicatedException;
import com.donggi.sendzy.member.exception.InvalidPasswordException;
import com.donggi.sendzy.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @RestControllerAdvice 는 @ControllerAdvice 와 @ResponseBody 가 합쳐진 어노테이션입니다.
 * @ControllerAdvice 는 컨트롤러에서 발생하는 예외를 전역적으로 처리하는 클래스에 붙이는 어노테이션입니다.
 * @ResponseBody 는 메서드의 반환값을 HTTP 응답 바디에 직접 넣어주는 어노테이션입니다.
 */
@RestControllerAdvice
public class ClientErrorControllerAdvice {

    /**
     * @ExceptionHandler 는 특정 예외가 발생했을 때 처리할 메서드를 지정하는 어노테이션입니다.
     */
    /**
     * 클라이언트가 입력한 이메일이 이미 존재하는 경우
     */
    @ExceptionHandler(EmailDuplicatedException.class)
    public ProblemDetail handleEmailDuplicatedException(EmailDuplicatedException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    /**
     * 클라이언트가 입력한 값이 유효하지 않은 경우
     */
    @ExceptionHandler(ValidException.class)
    public ProblemDetail handleValidException(ValidException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 클라이언트가 입력한 비밀번호가 유효하지 않은 경우
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handleInvalidPasswordException(InvalidPasswordException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 클라이언트가 요청한 회원이 존재하지 않는 경우
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ProblemDetail handleMemberNotFoundException(MemberNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
