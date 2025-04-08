package com.donggi.sendzy.remittance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RemittanceRequestTest {

    @Test
    void 생성된_시점으로부터_3일이_지나면_만료로_판단된다() {
        // given
        final var createdAt = LocalDateTime.of(2024, 4, 1, 0, 0);
        final var now = LocalDateTime.of(2024, 4, 4, 0, 0, 1); // 3일 + 1초
        final var expected = new RemittanceRequest(1L, 2L, RemittanceRequestStatus.PENDING, 1000L, createdAt);

        // when
        final var actual = expected.isExpired(now);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 생성된_시점으로부터_3일이_지나지_않았으면_만료가_아니다() {
        // given
        final var createdAt = LocalDateTime.of(2024, 4, 1, 0, 0);
        final var now = LocalDateTime.of(2024, 4, 3, 23, 59, 59); // 아직 3일 안됨
        final var expected = new RemittanceRequest(1L, 2L, RemittanceRequestStatus.PENDING, 1000L, createdAt);

        // when
        boolean expired = expected.isExpired(now);

        // then
        assertThat(expired).isFalse();
    }

}
