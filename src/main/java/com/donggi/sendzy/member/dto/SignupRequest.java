package com.donggi.sendzy.member.dto;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;

public record SignupRequest(
    String email,
    String password
) {
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final int EMAIL_MIN_LENGTH = 5;
    private static final int RAW_PASSWORD_MAX_LENGTH = 32;
    private static final int RAW_PASSWORD_MIN_LENGTH = 5;


    public SignupRequest {
        validate(email, password);
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
        Validator.maxLength(password, RAW_PASSWORD_MAX_LENGTH, fieldName);
        Validator.minLength(password, RAW_PASSWORD_MIN_LENGTH, fieldName);
        Validator.matchesRegex(password, RegexPattern.PASSWORD.getPattern(), RegexPattern.PASSWORD.getDescription(), fieldName);
    }
}
