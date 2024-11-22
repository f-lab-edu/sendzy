package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {

    private static final Length EMAIL_LENGTH = Length.builder().min(5).max(100).build();
    private static final Length PASSWORD_LENGTH = Length.builder().min(8).max(100).build();

    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public Member(final String email, final String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    private void validate(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(final String email) {
        final var fieldName = "email";
        Validator.notBlank(email, fieldName);
        Validator.maxLength(email, EMAIL_LENGTH.getMax(), fieldName);
        Validator.minLength(email, EMAIL_LENGTH.getMin(), fieldName);
        Validator.matchesRegex(email, RegexPattern.EMAIL.getPattern(), RegexPattern.EMAIL.getDescription(), fieldName);
    }

    private void validatePassword(final String password) {
        final var fieldName = "password";
        Validator.notBlank(password, fieldName);
        Validator.maxLength(password, PASSWORD_LENGTH.getMax(), fieldName);
        Validator.minLength(password, PASSWORD_LENGTH.getMin(), fieldName);
        Validator.matchesRegex(password, RegexPattern.PASSWORD.getPattern(), RegexPattern.PASSWORD.getDescription(), fieldName);
    }
}
