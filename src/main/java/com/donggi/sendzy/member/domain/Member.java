package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {

    private static final Length EMAIL_LENGTH = new Length(5, 100);
    private static final Length ENCODED_PASSWORD_LENGTH = new Length(8, 100);

    private Long id;
    private String email;
    private String encodedPassword;
    private LocalDateTime createdAt;

    public Member(final String email, final String encodedPassword) {
        validate(email, encodedPassword);
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.createdAt = LocalDateTime.now();
    }

    private void validate(final String email, final String encodedPassword) {
        validateEmail(email);
        validateEncodedPassword(encodedPassword);
    }

    private void validateEmail(final String email) {
        final var fieldName = "email";
        Validator.notBlank(email, fieldName);
        Validator.maxLength(email, EMAIL_LENGTH.getMax(), fieldName);
        Validator.minLength(email, EMAIL_LENGTH.getMin(), fieldName);
        Validator.matchesRegex(email, RegexPattern.EMAIL.getPattern(), RegexPattern.EMAIL.getDescription(), fieldName);
    }

    private void validateEncodedPassword(final String encodedPassword) {
        final var fieldName = "encodedPassword";
        Validator.notBlank(encodedPassword, fieldName);
        Validator.maxLength(encodedPassword, ENCODED_PASSWORD_LENGTH.getMax(), fieldName);
        Validator.minLength(encodedPassword, ENCODED_PASSWORD_LENGTH.getMin(), fieldName);
    }
}
