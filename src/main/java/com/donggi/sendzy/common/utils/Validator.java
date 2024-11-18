package com.donggi.sendzy.common.utils;

import com.donggi.sendzy.common.exception.ValidException;

import java.math.BigDecimal;

public final class Validator {

    private Validator() {
    }

    public static void notBlank(final String input, final String fieldName) {
        if (input == null || input.isBlank()) {
            throw new ValidException("%s 은/는 null 또는 공백이 될 수 없습니다.".formatted(fieldName));
        }
    }

    public static void maxLength(final CharSequence input, final int maxLength, final String fieldName) {
        if (maxLength <= 0) {
            throw new ValidException("최대 길이는 0 이하일 수 없습니다.");
        }

        if (input.length() > maxLength) {
            throw new ValidException(fieldName + " 의 길이는 " + maxLength + " 글자 이하여야 합니다.");
        }
    }

    public static void minLength(final CharSequence input, final int minLength, final String fieldName) {
        if (minLength <= 0) {
            throw new ValidException("최소 길이는 0 이하일 수 없습니다.");
        }

        if (input.length() < minLength) {
            throw new ValidException(fieldName + " 의 길이는 " + minLength + " 글자 이상이어야 합니다.");
        }
    }

    public static void minValue(final long value, final long minValue, final String fieldName) {
        if (value < minValue) {
            throw new ValidException(fieldName + " 은/는 " + minValue + " 이상이어야 합니다.");
        }
    }

    public static void maxValue(final BigDecimal value, final int maxValue, final String fieldName) {
        final var convertedMaxValue = BigDecimal.valueOf(maxValue);

        if (value.compareTo(convertedMaxValue) > 0) {
            throw new ValidException(fieldName + " 은/는 " + maxValue + " 이하이어야 합니다.");
        }
    }

    public static void notNegative(final BigDecimal value, final String fieldName) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidException(fieldName + " 은/는 음수가 될 수 없습니다.");
        }
    }

    public static void matchesRegex(final CharSequence input, final String pattern, final String patternDescription, final String fieldName) {
        if (!input.toString().matches(pattern)) {
            throw new ValidException(fieldName + " 은/는 " + patternDescription + " 조건을 충족해야 합니다.");
        }
    }
}

