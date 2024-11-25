package com.donggi.sendzy.member.dto;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;
import com.donggi.sendzy.member.domain.Length;

public record SignupRequest(
    String email,
    String rawPassword
) {

    private static final Length EMAIL_LENGTH = new Length(5, 100);
    private static final Length RAW_PASSWORD_LENGTH = new Length(5, 32);

    public SignupRequest {
        validate(email, rawPassword);
    }

    private void validate(final String email, final String rawPassword) {
        validateEmail(email);
        validateRawPassword(rawPassword);
    }

    private void validateEmail(final String email) {
        final var fieldName = "email";
        Validator.notBlank(email, fieldName);
        Validator.maxLength(email, EMAIL_LENGTH.getMax(), fieldName);
        Validator.minLength(email, EMAIL_LENGTH.getMin(), fieldName);
        Validator.matchesRegex(email, RegexPattern.EMAIL.getPattern(), RegexPattern.EMAIL.getDescription(), fieldName);
    }

    private void validateRawPassword(final String rawPassword) {
        final var fieldName = "rawPassword";
        Validator.notBlank(rawPassword, fieldName);
        Validator.maxLength(rawPassword, RAW_PASSWORD_LENGTH.getMax(), fieldName);
        Validator.minLength(rawPassword, RAW_PASSWORD_LENGTH.getMin(), fieldName);
        Validator.matchesRegex(rawPassword, RegexPattern.RAW_PASSWORD.getPattern(), RegexPattern.RAW_PASSWORD.getDescription(), fieldName);
    }
}
