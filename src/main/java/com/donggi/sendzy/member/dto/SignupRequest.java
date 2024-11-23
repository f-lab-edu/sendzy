package com.donggi.sendzy.member.dto;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;

public record SignupRequest(
    String email,
    String plainPassword
) {
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final int EMAIL_MIN_LENGTH = 5;
    private static final int PLAIN_PASSWORD_MAX_LENGTH = 32;
    private static final int PLAIN_PASSWORD_MIN_LENGTH = 5;


    public SignupRequest {
        validate(email, plainPassword);
    }

    private void validate(final String email, final String plainPassword) {
        validateEmail(email);
        validatePlainPassword(plainPassword);
    }

    private void validateEmail(final String email) {
        final var fieldName = "email";
        Validator.notBlank(email, fieldName);
        Validator.maxLength(email, EMAIL_MAX_LENGTH, fieldName);
        Validator.minLength(email, EMAIL_MIN_LENGTH, fieldName);
        Validator.matchesRegex(email, RegexPattern.EMAIL.getPattern(), RegexPattern.EMAIL.getDescription(), fieldName);
    }

    private void validatePlainPassword(final String plainPassword) {
        final var fieldName = "plainPassword";
        Validator.notBlank(plainPassword, fieldName);
        Validator.maxLength(plainPassword, PLAIN_PASSWORD_MAX_LENGTH, fieldName);
        Validator.minLength(plainPassword, PLAIN_PASSWORD_MIN_LENGTH, fieldName);
        Validator.matchesRegex(plainPassword, RegexPattern.PASSWORD.getPattern(), RegexPattern.PASSWORD.getDescription(), fieldName);
    }
}
