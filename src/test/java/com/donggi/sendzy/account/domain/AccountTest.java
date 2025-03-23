package com.donggi.sendzy.account.domain;

import com.donggi.sendzy.account.exception.InvalidWithdrawalException;
import com.donggi.sendzy.common.exception.ValidException;
import com.donggi.sendzy.member.TestUtils;
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

    @Nested
    class 출금_시 {

        @Nested
        class 출금_금액이 {

            @Test
            void 포함되지_않으면_ValidException_예외가_발생한다() {
                // given
                final var memberId = TestUtils.DEFAULT_MEMBER_ID;
                final var account = new Account(memberId);

                // when & then
                assertThatThrownBy(() -> account.withdraw(null))
                    .isInstanceOf(ValidException.class)
                    .hasMessage("amount 은/는 null이 될 수 없습니다.");
            }

            @Test
            void 음수면_ValidException_예외가_발생한다() {
                // given
                final var memberId = TestUtils.DEFAULT_MEMBER_ID;
                final var account = new Account(memberId);
                final var amount = -1L;

                // when & then
                assertThatThrownBy(() -> account.withdraw(amount))
                    .isInstanceOf(ValidException.class)
                    .hasMessage("amount 은/는 음수가 될 수 없습니다.");
            }

            @Test
            void _0이면_InvalidWithdrawalException_예외가_발생한다() {
                // given
                final var memberId = TestUtils.DEFAULT_MEMBER_ID;
                final var account = new Account(memberId);
                final var amount = 0L;

                // when & then
                assertThatThrownBy(() -> account.withdraw(amount))
                    .isInstanceOf(InvalidWithdrawalException.class)
                    .hasMessage("송금액은 0원 이상이어야 합니다. 사용자 요청 금액: 0");
            }

            @Test
            void 잔액보다_크면_InvalidWithdrawalException_예외가_발생한다() {
                // given
                final var memberId = TestUtils.DEFAULT_MEMBER_ID;
                final var account = new Account(memberId);
                final var amount = 100L;

                // when & then
                assertThatThrownBy(() -> account.withdraw(amount))
                    .isInstanceOf(InvalidWithdrawalException.class)
                    .hasMessage("잔액이 부족합니다.");
            }

            @Test
            void 잔액보다_작으면_대기_금액이_증가한다() {
                // given
                final var memberId = TestUtils.DEFAULT_MEMBER_ID;
                final var depositAmount = 1000L;
                final var withdrawAmount = 100L;
                final var account = new Account(memberId);
                account.deposit(depositAmount);

                // when
                account.withdraw(withdrawAmount);

                // then
                assertThat(account.getPendingAmount()).isEqualTo(withdrawAmount);
            }
        }
    }
}
