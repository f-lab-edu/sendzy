package com.donggi.sendzy.common.exception;

import com.donggi.sendzy.account.exception.InvalidWithdrawalException;
import com.donggi.sendzy.member.exception.EmailDuplicatedException;
import com.donggi.sendzy.member.exception.InvalidPasswordException;
import com.donggi.sendzy.remittance.exception.InvalidRemittanceRequestStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
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
     * 클라이언트가 요청한 데이터가 존재하지 않는 경우
     */
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleMemberNotFoundException(NotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * 클라이언트가 인증되지 않은 경우
     */
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    /**
     * 클라이언트가 접근 권한이 없는 경우
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    /**
     * 클라이언트가 출금 요청을 잘못한 경우
     */
    @ExceptionHandler(InvalidWithdrawalException.class)
    public ProblemDetail handleInvalidWithdrawalException(final InvalidWithdrawalException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 클라이언트 요청이 잘못된 경우
     */
    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(final BadRequestException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 송금 요청이 수락 또는 거절 가능한 상태가 아닌 경우
     */
    @ExceptionHandler(InvalidRemittanceRequestStatusException.class)
    public ProblemDetail handleInvalidRemittanceRequestStatusException(final InvalidRemittanceRequestStatusException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }
}
