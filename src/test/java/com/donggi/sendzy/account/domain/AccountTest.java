package com.donggi.sendzy.account.domain;

import com.donggi.sendzy.common.exception.ValidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AccountTest {

    @Nested
    class 계좌_등록_시 {

        @Nested
        class 요청이_정상적이면 {

            @Test
            void 계좌가_생성된다() {
                // given
                final var memberId = 1L;

                // when
                final var actual = new Account(memberId);

                // then
                assertThat(actual).isNotNull();
            }

            @Test
            void 초기_잔액은_0이다() {
                // given
                final var memberId = 1L;

                // when
                final var actual = new Account(memberId);

                // then
                assertThat(actual.getBalance()).isEqualTo(0L);
            }

            @Test
            void 초기_대기_금액은_0이다() {
                // given
                final var memberId = 1L;

                // when
                final var actual = new Account(memberId);

                // then
                assertThat(actual.getPendingAmount()).isEqualTo(0L);
            }
        }

        @Nested
        class 회원_ID가 {

            @Test
            void 포함되지_않으면_예외가_발생한다() {
                // when & then
                assertThatThrownBy(() -> new Account(null))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("memberId 은/는 null이 될 수 없습니다.");
            }
        }
    }
}
