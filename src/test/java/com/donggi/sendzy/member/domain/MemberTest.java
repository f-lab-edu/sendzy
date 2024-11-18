package com.donggi.sendzy.member.domain;

import com.donggi.sendzy.common.exception.ValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
public class MemberTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버_등록_시 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 요청이_정상적이면 {

            @Test
            @DisplayName("멤버가 생성된다")
            void 멤버가_생성된다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "SuperPa2ssword!@#";

                // when
                final var actual = new Member(email, password);

                // then
                assertThat(actual).isNotNull();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이메일이 {

            @Test
            @DisplayName("포함되지 않으면 예외가 발생한다")
            void 포함되지_않으면_예외가_발생한다() {
                // given
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(null, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 은/는 null 또는 공백이 될 수 없습니다.");
            }

            @Test
            @DisplayName("이메일이 100자를 넘어가면 예외가 발생한다")
            void 이메일이_100자를_넘어가면_예외가_발생한다() {
                // given
                final var email = "a".repeat(101);
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 의 길이는 100 글자 이하여야 합니다.");
            }

            @Test
            @DisplayName("이메일이 5자 미만이면 예외가 발생한다")
            void 이메일이_5자_미만이면_예외가_발생한다() {
                // given
                final var email = "a".repeat(4);
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 의 길이는 5 글자 이상이어야 합니다.");
            }

            @Test
            @DisplayName("이메일이 이메일 형식이 아니면 예외가 발생한다")
            void 이메일이_이메일_형식이_아니면_예외가_발생한다() {
                // given
                final var email = "donggi";
                final var password = "superpassword";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("email 은/는 유효한 이메일 형식 (예: user@example.com) 조건을 충족해야 합니다.");
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 비밀번호가 {

            @Test
            @DisplayName("포함되지 않으면 예외가 발생한다")
            void 포함되지_않으면_예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";

                // when & then
                assertThatThrownBy(() -> new Member(email, null))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("password 은/는 null 또는 공백이 될 수 없습니다.");
            }

            @Test
            @DisplayName("32자를 넘어가면 예외가 발생한다")
            void _32자를_넘어가면_예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "a".repeat(33);

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("password 의 길이는 32 글자 이하여야 합니다.");
            }

            @Test
            @DisplayName("8자 미만이면 예외가 발생한다")
            void _8자_미만이면_예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "1234567";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("password 의 길이는 8 글자 이상이어야 합니다.");
            }

            @Test
            @DisplayName("영문 대문자 영문 소문자 숫자 특수문자 중 3가지 이상을 포함하지 않으면 예외가 발생한다")
            void 영문_대문자_영문_소문자_숫자_특수문자_중_3가지_이상을_포함하지_않으면_예외가_발생한다() {
                // given
                final var email = "donggi@sendzy.com";
                final var password = "password";

                // when & then
                assertThatThrownBy(() -> new Member(email, password))
                        .isInstanceOf(ValidException.class)
                        .hasMessage("password 은/는 영문 대문자, 영문 소문자, 숫자, 특수문자 중 3가지 이상 포함 조건을 충족해야 합니다.");
            }
        }
    }

}
