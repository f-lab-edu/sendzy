package com.donggi.sendzy.common.utils;

import lombok.Getter;

@Getter
public enum RegexPattern {
    EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", "유효한 이메일 형식 (예: user@example.com)"),
    RAW_PASSWORD("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$", "영문 대문자, 영문 소문자, 숫자, 특수문자 중 3가지 이상 포함");

    private final String pattern;
    private final String description;

    RegexPattern(String pattern, String description) {
        this.pattern = pattern;
        this.description = description;
    }
}
