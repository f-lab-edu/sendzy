package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.common.utils.RegexPattern;
import com.donggi.sendzy.common.utils.Validator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {

    private static final Length EMAIL_LENGTH = Length.builder().min(5).max(100).build();
    private static final Length ENCRYPTED_PASSWORD_LENGTH = Length.builder().min(8).max(100).build();

    private Long id;
    private String email;
    private String encryptedPassword;
    private LocalDateTime createdAt;

    public Member(final String email, final String encryptedPassword) {
        validate(email, encryptedPassword);
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.createdAt = LocalDateTime.now();
    }

    private void validate(final String email, final String password) {
        validateEmail(email);
        validateEncryptedPassword(password);
    }

    private void validateEmail(final String email) {
        final var fieldName = "email";
        Validator.notBlank(email, fieldName);
        Validator.maxLength(email, EMAIL_LENGTH.getMax(), fieldName);
        Validator.minLength(email, EMAIL_LENGTH.getMin(), fieldName);
        Validator.matchesRegex(email, RegexPattern.EMAIL.getPattern(), RegexPattern.EMAIL.getDescription(), fieldName);
    }

    private void validateEncryptedPassword(final String encryptedPassword) {
        final var fieldName = "encryptedPassword";
        Validator.notBlank(encryptedPassword, fieldName);
        Validator.maxLength(encryptedPassword, ENCRYPTED_PASSWORD_LENGTH.getMax(), fieldName);
        Validator.minLength(encryptedPassword, ENCRYPTED_PASSWORD_LENGTH.getMin(), fieldName);
    }
}
