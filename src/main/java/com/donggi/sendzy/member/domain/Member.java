package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;

import java.time.LocalDateTime;

public class Member {

    private static final int EMAIL_MAX_LENGTH = 100;
    private static final int EMAIL_MIN_LENGTH = 5;
    public static final int ENCODED_PASSWORD_MAX_LENGTH = 100;
    public static final int ENCODED_PASSWORD_MIN_LENGTH = 8;

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
        Validator.maxLength(email, EMAIL_MAX_LENGTH, fieldName);
        Validator.minLength(email, EMAIL_MIN_LENGTH, fieldName);
        Validator.matchesRegex(email, RegexPattern.EMAIL.getPattern(), RegexPattern.EMAIL.getDescription(), fieldName);
    }

    private void validatePassword(final String password) {
        final var fieldName = "password";
        Validator.notBlank(password, fieldName);
        Validator.maxLength(password, ENCODED_PASSWORD_MAX_LENGTH, fieldName);
        Validator.minLength(password, ENCODED_PASSWORD_MIN_LENGTH, fieldName);
        Validator.matchesRegex(password, RegexPattern.PASSWORD.getPattern(), RegexPattern.PASSWORD.getDescription(), fieldName);
    }

    public Long getId() {
        return id;
    }
}
