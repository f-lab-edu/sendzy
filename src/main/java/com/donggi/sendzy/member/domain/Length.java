package com.donggi.sendzy.member.domain;

import lombok.Getter;

@Getter
public class Length {
    private int min;
    private int max;

    public Length(final int min, final int max) {
        this.min = min;
        this.max = max;
    }
}
