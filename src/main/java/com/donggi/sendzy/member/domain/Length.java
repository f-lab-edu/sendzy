package com.donggi.sendzy.member.domain;

import lombok.Getter;

@Getter
public class Length {

    private final int min;
    private final int max;

    private Length(final Builder builder) {
        this.min = builder.min;
        this.max = builder.max;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int min;
        private int max;

        public Builder min(final int min) {
            this.min = min;
            return this;
        }

        public Builder max(final int max) {
            this.max = max;
            return this;
        }

        public Length build() {
            return new Length(this);
        }
    }
}
